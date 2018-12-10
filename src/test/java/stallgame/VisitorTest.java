package stallgame;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.product.Product;

import static java.util.Collections.singletonList;

public class VisitorTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void countMoney() {
        Visitor visitor = new Visitor();
        Assert.assertEquals(Visitor.VISITOR_ON_SPAWN_MONEY_AMOUNT, visitor.countMoney());
    }

    @Test
    public void pay() {
        Visitor visitor = new Visitor();
        Assert.assertEquals(3, visitor.pay(3));
        Assert.assertEquals(7, visitor.countMoney());
    }

    @Test
    public void payNegative() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Not enough money to pay!");

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