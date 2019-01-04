package stallgame.stall.cashbox;

import org.junit.Test;
import stallgame.Constants;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;

import java.util.List;

import static java.util.Collections.singletonList;
import static stallgame.Constants.MEAT_FOOD_DESCRIPTION;

public class TransactionLoggerTest {

    @Test
    public void register() {
        NonPlayableCharacter mainChar = new NonPlayableCharacter();
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        List<Product> products = singletonList(new Product(ProductTypes.FOOD, Constants.MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION));
        int price = 10;
        String comment = "Good Luck";
        TransactionLogger.logTransaction(mainChar, visitor, products, price);
        TransactionLogger.logTransaction(visitor, mainChar, products, price, comment);
    }
}