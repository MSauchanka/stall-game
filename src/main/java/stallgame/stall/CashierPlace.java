package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.Role;
import stallgame.character.NonPlayableCharacter;
import stallgame.area.Area;
import stallgame.door.Door;
import stallgame.door.key.Lock;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.stall.cashbox.Cashbox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CashierPlace extends Area {

    public static final String CASHIER_PLACE_LOCK = "cashierPlaceLock";
    private static final int CASHIER_PLACE_MAX_VISITORS = 1;

    private GroceryStall groceryStall;
    private NonPlayableCharacter seller;
    private Cashbox cashbox;
    private Door door;

    public static CashierPlace createWith(GroceryStall groceryStall) {
        return new CashierPlace(CASHIER_PLACE_MAX_VISITORS, Role.SELLER)
                .addGroceryStall(groceryStall)
                .addCashbox()
                .addDoor();
    }

    private CashierPlace(int maxVisitorsCount, Role role) {
        super(maxVisitorsCount, role);
    }

    public NonPlayableCharacter observe() {
        if (getVisitors().size() > 0) {
            return getVisitors().get(0);
        }

        return null;
    }

    public boolean isProductsAvailable(List<Product> products) {
        if (!groceryStall.getStorage().isEmpty()) {
            Map<ProductTypes, List<Product>> productTypesRequired = products.stream()
                    .collect(Collectors.groupingBy(Product::getType));
            Map<ProductTypes, List<Product>> productTypesStorage = groceryStall.getStorage().stream()
                    .collect(Collectors.groupingBy(Product::getType));
            if (productTypesStorage.keySet().containsAll(productTypesRequired.keySet())) {
                return productTypesRequired.entrySet().stream()
                        .allMatch(entry -> productTypesStorage.get(entry.getKey()).size() >= entry.getValue().size());
            }
            return false;
        }
        return false;
    }

    public List<Product> getStorage() {
        return groceryStall.getStorage();
    }

    public Cashbox getCashbox() {
        return cashbox;
    }

    public Door getDoor() {
        return door;
    }

    private CashierPlace addGroceryStall(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;

        return this;
    }

    private CashierPlace addCashbox() {
        this.cashbox = Cashbox.createWith(this);

        return this;
    }

    private CashierPlace addDoor() {
        this.door = Door.createWith(this, groceryStall, Lock.createWith(CASHIER_PLACE_LOCK));

        return this;
    }

}