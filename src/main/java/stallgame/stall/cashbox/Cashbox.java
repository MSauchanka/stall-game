package stallgame.stall.cashbox;

import stallgame.Visitor;
import stallgame.character.NonPlayableCharacter;
import stallgame.product.Product;
import stallgame.stall.CashierPlace;

import java.util.List;

public class Cashbox {

    private int money = 0;
    private CashierPlace cashierPlace;

    public Cashbox(CashierPlace cashierPlace) {
        this.cashierPlace = cashierPlace;
    }

    public int countMoney() {
        return money;
    }

    public int getMoney() {
        int all = money;
        money = 0;
        return all;
    }

    public int getMoney(int count) {
        if (money < count) {
            return getMoney();
        }
        money -= count;
        return count;
    }
    public void registerTransaction(NonPlayableCharacter mainChar, List<Product> products, int price, Visitor buyer, String comment) {
        if (cashierPlace.getStorage().size() >= products.size()) {
            buyer.pay(price);
            money += price;
            buyer.addProducts(products);
            cashierPlace.getStorage().removeAll(products);
        } else {
            throw new RuntimeException("No enough products in the storage!");
        }
        TransactionLogger.logTransaction(mainChar, buyer, products, price, comment);
    }
}
