package stallgame.stall;

import org.junit.Assert;
import org.junit.Test;
import stallgame.GroceryStall;
import stallgame.MainChar;
import stallgame.Visitor;
import stallgame.product.Product;

import java.util.Collections;
import java.util.List;

public class Selling {

    @Test
    public void sell() {
        GroceryStall groceryStall = new GroceryStall();
        MainChar mainChar = new MainChar();
        Visitor visitor = new Visitor();
        List<Product> products = Collections.singletonList(new Product());

        groceryStall.loadProducts(products);
        mainChar.enterStall(groceryStall.getMainDoor());
        mainChar.enterCashierPlace(groceryStall.getCashierPlace());
        visitor.enterStall(groceryStall.getMainDoor());

        visitor.buy(products, groceryStall.getCashierPlace());
        Assert.assertEquals(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT - 7, visitor.countMoney());
        Assert.assertEquals(1, visitor.countProducts());
        Assert.assertEquals(0, groceryStall.getStorage().size());
        Assert.assertEquals(7, groceryStall.getCashierPlace().getCashbox().countMoney());

        //TODO - нарисовать схему магазина (image)
    }
}
