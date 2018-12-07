package stallgame.stall.cashbox;

import stallgame.MainChar;
import stallgame.Visitor;
import stallgame.product.Product;

import java.util.List;

public class Cashbox {

    private int moneyCount = 0;

    public void registerTransaction(MainChar mainChar, List<Product> products, int price, Visitor buyer, String comment) {
        TransactionLogger.logTransaction(mainChar, buyer, products, price, comment);
    }
}
