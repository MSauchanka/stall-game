package stallgame;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.action.Actions;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.item.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;

public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());

    public static void main(String[] args) {
        Environment env = createEnvironment();
        // Make npc playable
        Set<PlayableCharacter> wrappedNpc = env.npcs.stream()
                .map(npc -> {
                    int selection = new Random().nextInt(env.npcs.size());
                    if (selection < env.npcs.size() / 3) {
                        npc.getInventory().addAll(singletonList(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION)));
                        npc.getInventory().addAll(singletonList(new Key(CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION)));
                    }
                    return new PlayableCharacter(npc);
                })
                .collect(Collectors.toSet());
        for (; ; ) {
            long epochSecondStart = Instant.now().toEpochMilli();
            gameLoop(env, wrappedNpc);
            long epochSecondEnd = Instant.now().toEpochMilli();
            // Calculate amount of millisec required for one frame.
            // If game loop execution took less, then wait.
            long delay = 1000 / env.frequency - (epochSecondEnd - epochSecondStart);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void gameLoop(Environment env, Set<PlayableCharacter> wrappedNpc) {
        wrappedNpc.forEach(wnpc -> {
            int selection = new Random().nextInt(wnpc.getActions().size() + 1);
            LOGGER.info(wnpc.npc.getFullName() + " selection: " + selection);
            if (selection == wnpc.getActions().size()) {
                printWorldStatus(wnpc, env);
            } else {
                LOGGER.info(wnpc.npc.getFullName() + ":");
                LOGGER.info("Выполняет: " + wnpc.getActions().get(selection).toString());
                try {
                    List<Pair> pairs = wnpc.getActions().stream()
                            .map(actions -> new Pair(actions, 10d))
                            .collect(Collectors.toList());
                    Actions action = (Actions) new EnumeratedDistribution(pairs).sample();
                    action.execute(wnpc.npc, env);
                } catch (RuntimeException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        });
        clearConsole();
        printAllWorldStatus(env);
    }

    private static Environment createEnvironment() {
        Environment env = new Environment();
        Product product = new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION);
        IntStream.range(0, 50).forEach(idx -> env.groceryStall.loadProducts(singletonList(product)));
        IntStream.range(0, 50).forEach(idx -> env.npcs.add(new NonPlayableCharacter()));

        return env;
    }

    private static void printWorldStatus(PlayableCharacter mainChar, Environment environment) {
        LOGGER.debug("Main character name: " + mainChar.npc.getFullName());
        LOGGER.debug("Main character role: " + mainChar.npc.getRole());
        LOGGER.debug("World npc count: " + environment.npcs.size());
        LOGGER.debug("Grocery stall visitors count: " + environment.npcs.stream()
                .filter(npc -> Role.VISITOR.equals(npc.getRole()))
                .count());
        LOGGER.debug("Grocery stall seller count: " + environment.npcs.stream()
                .filter(npc -> Role.SELLER.equals(npc.getRole()))
                .count());
        LOGGER.debug("Grocery stall products count: " + environment.groceryStall.getStorage().size());
        LOGGER.debug("Cashbox money count: " + environment.groceryStall.getCashierPlace().getCashbox().countMoney());
    }

    private static void printAllWorldStatus(Environment environment) {
        LOGGER.trace("World npc count: " + environment.npcs.size());
        LOGGER.trace("Grocery stall visitors count: " + environment.npcs.stream()
                .filter(npc -> Role.VISITOR.equals(npc.getRole()))
                .count());
        LOGGER.trace("Grocery stall seller count: " + environment.npcs.stream()
                .filter(npc -> Role.SELLER.equals(npc.getRole()))
                .count());
        LOGGER.trace("Grocery stall products count: " + environment.groceryStall.getStorage().size());
        LOGGER.trace("Cashbox money count: " + environment.groceryStall.getCashierPlace().getCashbox().countMoney());
    }



    private static void clearConsole() {
//        final String operatingSystem = System.getProperty("os.name");
//
//        if (operatingSystem .contains("Windows")) {
//            try {
//                Runtime.getRuntime().exec("cls");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            try {
//                Runtime.getRuntime().exec("clear");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
