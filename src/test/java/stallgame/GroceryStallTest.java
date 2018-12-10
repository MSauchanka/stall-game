package stallgame;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.product.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroceryStallTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void leaveByDoor() {
        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        mainChar.enterStall(groceryStall.getMainDoor());
        Assert.assertEquals(1, groceryStall.visitors.size());
        mainChar.leaveStall(groceryStall.getMainDoor());
        Assert.assertEquals(0, groceryStall.visitors.size());
    }

    @Test
    public void leaveByDoorVisitor() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Sorry door is closed!");

        GroceryStall groceryStall = new GroceryStall();
        Visitor visitor = new Visitor();
        visitor.enterStall(groceryStall.getMainDoor());
    }

    @Test
    public void cashboxTransaction() {
        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        Visitor visitor = new Visitor();
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        mainChar.enterStall(groceryStall.getMainDoor());
        groceryStall.loadProducts(products);
        visitor.enterStall(groceryStall.getMainDoor());
        mainChar.enterCashierPlace(groceryStall.getCashierPlace());
        // TODO
//        mainChar.pickUpCashbox(groceryStall.getCashierPlace().getCashbox());
//        mainChar.operateCashbox().registerTransaction(mainChar, products, 100, visitor, "no comments");
    }


    @Test
    public void toCashierPlaceOutside() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Enter the stall to take cashier place!");

        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        mainChar.enterCashierPlace(groceryStall.getCashierPlace());
    }


}