package stallgame;

import stallgame.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Visitor extends StallVisitor {

    private int money = VISITOR_ON_SPAWN_MONEY_AMOUNT;
    private List<Product> inventory = new ArrayList<>();

    public static final int VISITOR_ON_SPAWN_MONEY_AMOUNT = 10;

    public int countMoney() {
        return money;
    }

    public int pay(int amountToPay) {
        if (amountToPay <= money) {
            money -= amountToPay;
            return money;
        }

        throw new RuntimeException("Not enough money to pay!");
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public int countProducts() {
        return inventory.size();
    }

    public void addProducts(List<Product> products) {
        inventory.addAll(products);
    }
}
