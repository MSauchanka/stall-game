package stallgame;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import static java.util.Collections.singletonList;
import static stallgame.character.NonPlayableCharacter.VISITOR_ON_SPAWN_MONEY_AMOUNT;

public class VisitorTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void countMoney() {
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        Assert.assertEquals(VISITOR_ON_SPAWN_MONEY_AMOUNT, visitor.countMoney());
    }

    @Test
    public void pay() {
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        Assert.assertEquals(3, visitor.pay(3));
        Assert.assertEquals(7, visitor.countMoney());
    }

    @Test
    public void payNegative() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Not enough money to pay!");

        NonPlayableCharacter visitor = new NonPlayableCharacter();
        visitor.pay(VISITOR_ON_SPAWN_MONEY_AMOUNT + 1);
    }

    @Test
    public void addMoney() {
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        visitor.addMoney(5);
        Assert.assertEquals(VISITOR_ON_SPAWN_MONEY_AMOUNT + 5, visitor.countMoney());
    }

    @Test
    public void addProducts() {
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        visitor.getInventory().addAll(singletonList(new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, "Fresh meat!")));
        Assert.assertEquals(1, visitor.countProducts());
    }
}