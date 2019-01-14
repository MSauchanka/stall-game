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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;
import static stallgame.character.NonPlayableCharacter.MIN_AT_ROLE_TIME;

public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());

    public static void main(String[] args) {
        World world = createWorld();
        // Make npc playable
        Set<PlayableCharacter> wrappedNpc = world.population.stream()
                .map(npc -> {
                    int selection = new Random().nextInt(world.population.size());
                    if (selection < world.population.size() / 3) {
                        npc.getInventory().addAll(singletonList(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION)));
                        npc.getInventory().addAll(singletonList(new Key(CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION)));
                    }
                    return new PlayableCharacter(npc);
                })
                .collect(Collectors.toSet());
        for (; ; ) {
            long epochSecondStart = Instant.now().toEpochMilli();
            gameLoop(world, wrappedNpc);
            long epochSecondEnd = Instant.now().toEpochMilli();
            // Calculate amount of millisec required for one frame.
            // If game loop execution took less, then wait.
            long delay = 1000 / world.serverFramesFrequency - (epochSecondEnd - epochSecondStart);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            world.tics += 1;
        }
    }

    private static void gameLoop(World world, Set<PlayableCharacter> wrappedNpc) {
        wrappedNpc.forEach(wnpc -> {
            int selection = new Random().nextInt(wnpc.getActions().size());
            LOGGER.info(wnpc.npc.getFullName() + " selection: " + selection);
            LOGGER.info(wnpc.npc.getFullName() + ":");
            LOGGER.info("Выполняет: " + wnpc.getActions().get(selection).toString());
            try {
                if (wnpc.npc.atRole > MIN_AT_ROLE_TIME) {
                    List<Pair> pairs = createWeightPairs(wnpc);
                    Actions action = (Actions) new EnumeratedDistribution(pairs).sample();
                    action.execute(wnpc.npc, world);
                } else {
                    wnpc.npc.atRole += 1;
                }
            } catch (RuntimeException e) {
                LOGGER.error(e.getMessage());
            }
        });
        clearConsole();
        printAllWorldStatus(world);
    }

    private static List<Pair> createWeightPairs(PlayableCharacter wnpc) {
        double originalWaitWeight = 100 - wnpc.npc.atRole;
        List<Pair> pairs = wnpc.getActions().stream()
                .map(action -> {
                    double avgActionWeight = (100 - originalWaitWeight) / wnpc.getActions().size();
                    LOGGER.debug("{} action {} weight: {}, wait weight: {}.", wnpc.npc.getFullName(),
                            action.toString(), avgActionWeight, originalWaitWeight);
                    return getPairWeightedByRole(wnpc, action, avgActionWeight, originalWaitWeight);
                })
                .collect(Collectors.toList());
        double actionsWeightSum = pairs.stream()
                .mapToDouble(pair -> (double) pair.getValue())
                .sum();
        double residualWaitWeight = 100 - actionsWeightSum;
        pairs.add(new Pair(Actions.WAIT, residualWaitWeight));
        LOGGER.debug("{} residual wait weight: {}.", wnpc.npc.getFullName(), residualWaitWeight);

        return pairs;
    }

    private static Pair getPairWeightedByRole(PlayableCharacter wnpc, Actions action, double actionWeight, double waitWeight) {
        if (Goal.TO_STALL_BUY.equals(wnpc.npc.getGoal())){
            if (Role.NO_ROLE.equals(wnpc.npc.getRole())) {
                List<Actions> actions = new LinkedList<>();
                actions.add(Actions.ENTER_STALL);

                return createPair(wnpc, action, actionWeight, waitWeight, actions);
            } else if (Role.VISITOR.equals(wnpc.npc.getRole())) {
                List<Actions> actions = new LinkedList<>();
                actions.add(Actions.BUY);

                return createPair(wnpc, action, actionWeight, waitWeight, actions);
            }
        } else if(Goal.TO_STALL_VISIT.equals(wnpc.npc.getGoal())) {
            if (Role.NO_ROLE.equals(wnpc.npc.getRole())) {
                List<Actions> actions = new LinkedList<>();
                actions.add(Actions.ENTER_STALL);

                return createPair(wnpc, action, actionWeight, waitWeight, actions);
            }
        } else if (Goal.TO_WORK.equals(wnpc.npc.getGoal())) {

        } else if (Goal.TO_HOME.equals(wnpc.npc.getGoal())) {

        }

        return null;
    }

    private static Pair createPair(PlayableCharacter wnpc, Actions action, double actionWeight, double waitWeight,
                                   List<Actions> actions) {
        double halfWaitWeight = waitWeight / 2;
        for (int idx = 0; idx < actions.size(); idx++) {
            if (actions.get(idx).equals(action)) {
                if (0 == idx) {
                    actionWeight += halfWaitWeight / 2;
                } else {
                    actionWeight += (halfWaitWeight / 2) / wnpc.getActions().size();
                }
            }
        }
        LOGGER.debug("{} UPDATED action {} weight: {}.", wnpc.npc.getFullName(), action.toString(), actionWeight);

        return new Pair(action, actionWeight);
    }


    private static World createWorld() {
        World world = new World();
        Product product = new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION);
        IntStream.range(0, 50).forEach(idx -> world.groceryStall.loadProducts(singletonList(product)));
        IntStream.range(0, 50).forEach(idx -> world.population.add(new NonPlayableCharacter()));

        return world;
    }

    private static void printAllWorldStatus(World world) {
        LOGGER.trace("NPC count: " + world.population.size());
        LOGGER.trace(Role.VISITOR + " count : " + world.population.stream()
                .filter(npc -> Role.VISITOR.equals(npc.getRole()))
                .count());
        LOGGER.trace("Grocery VISITORS MAP count : " + world.groceryStall.visitors.size());
        LOGGER.trace(Role.SELLER + " name : " + world.population.stream()
                .filter(npc -> Role.SELLER.equals(npc.getRole()))
                .map(NonPlayableCharacter::getFullName)
                .findFirst().orElse("No seller at the moment!"));
        LOGGER.trace(Role.NO_ROLE + " count : " + world.population.stream()
                .filter(npc -> Role.NO_ROLE.equals(npc.getRole()))
                .count());
        LOGGER.trace("Grocery stall products count: " + world.groceryStall.getStorage().size());
        LOGGER.trace("Cashbox money count: " + world.groceryStall.getCashierPlace().getCashbox().countMoney());
        LOGGER.trace("Tics passed: " + world.tics);
    }

    private static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
