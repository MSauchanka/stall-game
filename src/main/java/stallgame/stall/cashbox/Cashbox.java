package stallgame.stall.cashbox;

import stallgame.character.NonPlayableCharacter;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.stall.CashierPlace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cashbox {

    private int money = 0;
    private CashierPlace cashierPlace;

    public static Cashbox createWith(CashierPlace cashierPlace) {
        return new Cashbox(cashierPlace);
    }

    private Cashbox(CashierPlace cashierPlace) {
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

    public void registerTransaction(NonPlayableCharacter mainChar, List<Product> products, int price, NonPlayableCharacter buyer, String comment) {
        buyer.pay(price);
        money += price;
        buyer.getInventory().addAll(products);
        Map<ProductTypes, Long> productTypesCount = products.stream()
                .collect(Collectors.groupingBy(Product::getType, Collectors.counting()));
        IntStream.range(0, cashierPlace.getStorage().size()).forEach(idx -> {
            ProductTypes productType = cashierPlace.getStorage().get(idx).getType();
            if (null != productTypesCount.get(productType)) {
                Long count = productTypesCount.get(productType);
                if (count > 0) {
                    cashierPlace.getStorage().remove(idx);
                    count -= 1;
                    productTypesCount.put(productType, count);
                }
            }
        });
        TransactionLogger.logTransaction(mainChar, buyer, products, price, comment);
    }
}
