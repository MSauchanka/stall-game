package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.product.Product;

import java.util.stream.IntStream;

import static java.util.Collections.singletonList;

public class Starter {

    public static void main(String[] args) {
        Environment env = new Environment();
        env.groceryStall.loadProducts(singletonList(new Product()));
        IntStream.range(0, 5).forEach(idx -> env.npcs.add(new NonPlayableCharacter()));
        PlayableCharacter mainChar = env.operateNpc(env.npcs.stream().findFirst().get());
        System.out.println(mainChar.npc.getFullName());

        // TODO: will add - get list of available commands for current role and inventory.
        // TODO: method that call command from the list
    }
}
