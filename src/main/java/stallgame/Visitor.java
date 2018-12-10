package stallgame;

import stallgame.product.Product;
import stallgame.stall.CashierPlace;
import stallgame.stall.cashbox.Cashbox;

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
            return amountToPay;
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

    public void buy(List<Product> products, CashierPlace cashierPlace) {
        // validate visitor place
        if (null != cashierPlace.observe()) {
            if (cashierPlace.isProductsAvailable()) {
                MainChar seller = cashierPlace.observe();
                int productsPrice = products.stream().mapToInt(value -> value.price).sum();
                if (productsPrice <= countMoney()) {
                    try {
                        Cashbox cashbox = cashierPlace.getCashbox();
                        cashbox.registerTransaction(seller, products, productsPrice, this, "From visitor.buy");
                    } catch (Exception e) {
                        throw new RuntimeException("Transaction exception!");
                        // if fails rollback
                    }
                } else {
                    throw new RuntimeException("You have no enough money!");
                }
            } else {
                throw new RuntimeException("No products!");
            }
        } else {
            throw new RuntimeException("No cashier in the place!");
        }
    }
}
