package stallgame;

import org.junit.Assert;
import org.junit.Test;
import stallgame.product.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroceryStallTest {

    @Test
    public void leaveByDoor() {
        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        mainChar.enterStall(groceryStall.getMainDoor());
        Assert.assertEquals(1, groceryStall.visitors.size());
        mainChar.leaveStall(groceryStall.getMainDoor());
        Assert.assertEquals(0, groceryStall.visitors.size());
    }


    @Test(expected = RuntimeException.class)
    public void leaveByDoorVisitor() {
        GroceryStall groceryStall = new GroceryStall();
        Visitor visitor = new Visitor();
        visitor.enterStall(groceryStall.getMainDoor());
        Assert.assertEquals(0, groceryStall.visitors.size());
        visitor.leaveStall(groceryStall.getMainDoor());
        Assert.assertEquals(0, groceryStall.visitors.size());
    }

    @Test
    public void cashboxTransactionTest() {
        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        Visitor visitor = new Visitor();
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        mainChar.enterStall(groceryStall.getMainDoor());
        groceryStall.loadProducts(products);
        visitor.enterStall(groceryStall.getMainDoor());
        groceryStall.getCashbox().registerTransaction(mainChar, products, 100, visitor, "no comments");
    }
}