package stallgame.stall;

import org.junit.Assert;
import org.junit.Test;
import stallgame.Constants;
import stallgame.GroceryStall;
import stallgame.Role;
import stallgame.World;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.door.key.Key;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import java.util.List;

import static java.util.Collections.singletonList;
import static stallgame.Constants.*;
import static stallgame.character.NonPlayableCharacter.VISITOR_ON_SPAWN_MONEY_AMOUNT;

public class Selling {

    @Test
    public void sell() {
        World world = World.create();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        world.addVisitor(npc);
        world.operateNpc(npc);

        world.mainChar.npc.getInventory().add(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION));
        world.mainChar.npc.getInventory().add(new Key(CashierPlace.CASHIER_PLACE_LOCK, CASHIER_PLACE_KEY_DESCRIPTION));
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        world.addVisitor(visitor);
        List<Product> products = singletonList(new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION));

        world.groceryStall.loadProducts(products);
        world.mainChar.npc.enter(world.groceryStall.getMainDoor());
        world.mainChar.npc.enter(world.groceryStall.getCashierPlace().getDoor());
        visitor.enter(world.groceryStall.getMainDoor());

        visitor.buy(products, world.groceryStall.getCashierPlace());
        Assert.assertEquals(VISITOR_ON_SPAWN_MONEY_AMOUNT - 7, visitor.countMoney());
        Assert.assertEquals(1, visitor.countProducts());
        Assert.assertEquals(0, world.groceryStall.getStorage().size());
        Assert.assertEquals(7, world.groceryStall.getCashierPlace().getCashbox().countMoney());
    }
}
