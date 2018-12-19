package stallgame;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.product.Product;

import java.util.ArrayList;
import java.util.List;

public class GroceryStallTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void leaveByDoor() {
        GroceryStall groceryStall = new GroceryStall();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        npc.setRole(Role.SELLER);
        PlayableCharacter mainChar = new PlayableCharacter(npc);
        mainChar.npc.enterStall(groceryStall.getMainDoor());
        Assert.assertEquals(1, groceryStall.visitors.size());
        mainChar.npc.leaveStall(groceryStall.getMainDoor());
        Assert.assertEquals(0, groceryStall.visitors.size());
    }

    @Test
    public void leaveByDoorVisitor() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Sorry door is closed!");

        GroceryStall groceryStall = new GroceryStall();
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        visitor.enterStall(groceryStall.getMainDoor());
    }

    @Test
    public void cashboxTransaction() {
        GroceryStall groceryStall = new GroceryStall();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        npc.setRole(Role.SELLER);
        PlayableCharacter mainChar = new PlayableCharacter(npc);
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        mainChar.npc.enterStall(groceryStall.getMainDoor());
        groceryStall.loadProducts(products);
        visitor.enterStall(groceryStall.getMainDoor());
        mainChar.npc.enterCashierPlace(groceryStall.getCashierPlace());
        // TODO: NPC handle cashbox
//        mainChar.npc.pickUpCashbox(groceryStall.getCashierPlace().getCashbox());
//        mainChar.npc.operateCashbox().registerTransaction(mainChar, products, 100, visitor, "no comments");
    }


    @Test
    public void toCashierPlaceOutside() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Enter the stall to take cashier place!");

        GroceryStall groceryStall = new GroceryStall();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        PlayableCharacter mainChar = new PlayableCharacter(npc);
        mainChar.npc.enterCashierPlace(groceryStall.getCashierPlace());
    }


}