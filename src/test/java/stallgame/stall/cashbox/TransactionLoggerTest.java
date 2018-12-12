package stallgame.stall.cashbox;

import org.junit.Test;
import stallgame.Visitor;
import stallgame.character.NonPlayableCharacter;
import stallgame.product.Product;

import java.util.List;

import static java.util.Collections.singletonList;

public class TransactionLoggerTest {

    @Test
    public void register() {
        NonPlayableCharacter mainChar = new NonPlayableCharacter();
        Visitor visitor = new Visitor();
        List<Product> products = singletonList(new Product());
        int price = 10;
        String comment = "Good Luck";
        TransactionLogger.logTransaction(mainChar, visitor, products, price);
        TransactionLogger.logTransaction(visitor, mainChar, products, price, comment);
    }
}