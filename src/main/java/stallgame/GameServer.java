package stallgame;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.action.Actions;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.door.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.jetty.operator.ServerOperator;
import stallgame.jetty.socket.ServerSocket;
import stallgame.stall.CashierPlace;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;
import static stallgame.character.NonPlayableCharacter.MIN_AT_ROLE_TIME;

public class GameServer {

    public static final List<ServerSocket> serverSockets = new ArrayList<>();
    public static final List<World> worlds = new LinkedList<>();
    // TODO: resolve strong dependency between world and socket
    public static final Map<UUID, ServerSocket> serverSocketByUuid = new ConcurrentHashMap<>();
    public static final Map<UUID, World> worldByUUID = new ConcurrentHashMap<>();

    private static final Logger LOGGER = LogManager.getLogger(GameServer.class.getName());
    private static final String port = null != System.getProperty("serverPort") ? System.getProperty("serverPort") : "9009";

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> ServerOperator.runServer(Integer.parseInt(port))).start();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        LOGGER.trace("Server started on port: " + port + ". Waiting for client!");
        while (serverSockets.isEmpty()) {
            Thread.sleep(1000);
        }
        LOGGER.trace("Client connected!");
        while (worlds.isEmpty()) {
            Thread.sleep(1000);
        }
        LOGGER.trace("First world created!");
        executorService.execute(new WorldLoop());
    }

    public static World createWorld() {
        World world = World.create();
        Product product = new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION);
        IntStream.range(0, 50).forEach(idx -> world.groceryStall.loadProducts(singletonList(product)));
        IntStream.range(0, 50).forEach(idx -> world.addVisitor(new NonPlayableCharacter()));
        world.wrappedNpcs = world.getVisitors().stream().map(PlayableCharacter::new).collect(Collectors.toSet());

        return world;
    }

    public static Set<PlayableCharacter> wrapNpcs(World world) {
        return world.getVisitors().stream()
                .map(npc -> {
                    int count = world.getVisitors().size();
                    int selection = new Random().nextInt(count);
                    if (selection < count / 3) {
                        npc.getInventory().addAll(singletonList(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION)));
                        npc.getInventory().addAll(singletonList(new Key(CashierPlace.CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION)));
                    }
                    return new PlayableCharacter(npc);
                })
                .collect(Collectors.toSet());
    }

    public static void gameLoop(World world, Set<PlayableCharacter> wrappedNpc) {
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
    }

    public static String worldsUUIDsAsJson() {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        worldByUUID.keySet().stream()
                .map(UUID::toString)
                .forEach(arr::add);
        obj.add("worlds", arr);

        return obj.toString();
    }

    private static List<Pair> createWeightPairs(PlayableCharacter wnpc) {
        double originalWaitWeight = 100 - wnpc.npc.atRole;
        List<Pair> pairs = wnpc.getActions().stream()
                .map(action -> {
                    double avgActionWeight = (100 - originalWaitWeight) / wnpc.getActions().size();
                    LOGGER.debug("{} action {} weight: {}, wait weight: {}.", wnpc.npc.getFullName(),
                            action.toString(), avgActionWeight, originalWaitWeight);
                    List<Actions> actions = wnpc.npc.getGoal().getActions(wnpc.npc.getRole());

                    return createPair(wnpc, action, avgActionWeight, originalWaitWeight, actions);
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
}
