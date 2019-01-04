package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.item.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import java.util.Scanner;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;

public class Client {

    public static void main(String[] args) {
        Environment env = new Environment();
        Product product = new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION);
        IntStream.range(0, 5).forEach(idx -> env.groceryStall.loadProducts(singletonList(product)));
        IntStream.range(0, 5).forEach(idx -> env.npcs.add(new NonPlayableCharacter()));
        PlayableCharacter mainChar = env.operateNpc(new NonPlayableCharacter());
        mainChar.npc.getInventory().addAll(singletonList(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION)));
        mainChar.npc.getInventory().addAll(singletonList(new Key(CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION)));

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
                printWorldStatus(mainChar, env);
            } else {
                mainChar.getActions().get(selection).execute(mainChar.npc, env);
            }
        }
    }

    private static void printWorldStatus(PlayableCharacter mainChar, Environment environment) {
        System.out.println("Main character name: " + mainChar.npc.getFullName());
        System.out.println("Main character role: " + mainChar.npc.getRole());
        System.out.println("World npc count: " + environment.npcs.size());
        System.out.println("Grocery stall visitors count: " + environment.npcs.stream()
                .filter(npc -> Role.VISITOR.equals(npc.getRole()))
                .count());
        System.out.println("Grocery stall seller count: " + environment.npcs.stream()
                .filter(npc -> Role.SELLER.equals(npc.getRole()))
                .count());
        System.out.println("Grocery stall products count: " + environment.groceryStall.getStorage().size());
        System.out.println("Cashbox money count: " + environment.groceryStall.getCashierPlace().getCashbox().countMoney());
    }

}
