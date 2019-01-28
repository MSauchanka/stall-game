package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.door.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.stall.CashierPlace;

import java.util.Scanner;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;

public class GameClient {

    public static World world;

    public static void main(String[] args) {
        World world = World.create();
        Product product = new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION);
        IntStream.range(0, 5).forEach(idx -> world.groceryStall.loadProducts(singletonList(product)));
        IntStream.range(0, 5).forEach(idx -> world.addVisitor(new NonPlayableCharacter()));
        PlayableCharacter mainChar = world.operateNpc(new NonPlayableCharacter());
        mainChar.npc.getInventory().addAll(singletonList(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION)));
        mainChar.npc.getInventory().addAll(singletonList(new Key(CashierPlace.CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION)));

        System.out.println(mainChar.npc.getFullName() + ", рассвет уже близко. Самое время открыть магазин, " +
                "чтобы не расстроить утренних покупателей.");

        boolean gameInProgress = true;
        Scanner reader = new Scanner(System.in);
        while (gameInProgress) {

            System.out.println(System.lineSeparator() + "Список действий: ");

            IntStream.range(0, mainChar.getActions().size()).forEach(idx -> System.out.println(idx + ". "
                    + mainChar.getActions().get(idx).toString()));
            System.out.println(mainChar.getActions().size() + ". World status.");
            System.out.println(mainChar.getActions().size() + 1 + ". Exit.");
            System.out.println("Ввод номера действия:");
            int selection = reader.nextInt();
            if (selection > mainChar.getActions().size()) {
                gameInProgress = false;
                System.out.println(System.lineSeparator() + "Игра закончена.");
            } else if (selection == mainChar.getActions().size()) {

            } else {
                mainChar.getActions().get(selection).execute(mainChar.npc, world);
            }
        }
    }

}
