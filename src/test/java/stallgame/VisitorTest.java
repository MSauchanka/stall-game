package stallgame;

import org.junit.Assert;
import org.junit.Test;
import stallgame.product.Product;

import static java.util.Collections.singletonList;

public class VisitorTest {

    @Test
    public void countMoney() {
        Visitor visitor = new Visitor();
        Assert.assertEquals(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT, visitor.countMoney());
    }

    @Test
    public void pay() {
        Visitor visitor = new Visitor();
        visitor.pay(5);
        Assert.assertEquals(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT - 5, visitor.countMoney());
    }

    @Test(expected = RuntimeException.class)
    public void payNegative() {
        Visitor visitor = new Visitor();
        visitor.pay(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT + 1);
    }

    @Test
    public void addMoney() {
        Visitor visitor = new Visitor();
        visitor.addMoney(5);
        Assert.assertEquals(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT + 5, visitor.countMoney());
    }

    @Test
    public void addProducts() {
        Visitor visitor = new Visitor();
        visitor.addProducts(singletonList(new Product()));
        Assert.assertEquals(1, visitor.countProducts());
    }
}