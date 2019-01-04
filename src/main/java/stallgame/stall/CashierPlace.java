package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.key.Lock;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.stall.cashbox.Cashbox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static stallgame.Constants.CASHIER_PLACE_LOCK;

public class CashierPlace {

    private GroceryStall groceryStall;
    private NonPlayableCharacter seller;
    private Cashbox cashbox = new Cashbox(this);
    private Door door;

    public CashierPlace(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;
        this.door = new Door(groceryStall, new Lock(CASHIER_PLACE_LOCK));
    }

    public void enter(NonPlayableCharacter seller) {
        if (null == this.seller) {
            door.enter(seller);
            this.seller = seller;
        } else {
            throw new RuntimeException(this.seller.getFullName() + " is cashier already!");
        }
    }

    public void leave(NonPlayableCharacter seller) {
        if (this.seller.equals(seller)) {
            door.leave(seller);
            this.seller = null;
        } else {
            throw new RuntimeException("You not the cashier to leave");
        }
    }

    public void leaveAndLock(NonPlayableCharacter seller) {
        if (this.seller.equals(seller)) {
            door.leaveAndLock(seller);
            this.seller = null;
        } else {
            throw new RuntimeException("You not the cashier to leave");
        }
    }

    public NonPlayableCharacter observe() {
        return seller;
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


}
