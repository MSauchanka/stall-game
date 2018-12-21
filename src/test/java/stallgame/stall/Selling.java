package stallgame.stall;

import org.junit.Assert;
import org.junit.Test;
import stallgame.Constants;
import stallgame.GroceryStall;
import stallgame.Role;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.item.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import java.util.Collections;
import java.util.List;

import static stallgame.Constants.*;
import static stallgame.character.NonPlayableCharacter.VISITOR_ON_SPAWN_MONEY_AMOUNT;

public class Selling {

    @Test
    public void sell() {
        GroceryStall groceryStall = new GroceryStall();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        npc.setRole(Role.SELLER);
        PlayableCharacter mainChar = new PlayableCharacter(npc);
        mainChar.npc.getInventory().add(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION));
        mainChar.npc.getInventory().add(new Key(CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION));
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        List<Product> products = Collections.singletonList(new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, "Fresh meat!"));

        groceryStall.loadProducts(products);
        mainChar.npc.enterStall(groceryStall.getMainDoor());
        mainChar.npc.enterCashierPlace(groceryStall.getCashierPlace());
        visitor.enterStall(groceryStall.getMainDoor());

        visitor.buy(products, groceryStall.getCashierPlace());
        Assert.assertEquals(VISITOR_ON_SPAWN_MONEY_AMOUNT - 7, visitor.countMoney());
        Assert.assertEquals(1, visitor.countProducts());
        Assert.assertEquals(0, groceryStall.getStorage().size());
        Assert.assertEquals(7, groceryStall.getCashierPlace().getCashbox().countMoney());
    }
}
