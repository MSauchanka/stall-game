package stallgame;

import stallgame.product.Product;
import stallgame.stall.CashierPlace;
import stallgame.stall.StallDoor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroceryStall {

    public static final int MAX_STALL_VISITORS = 1;

    public final HashSet<StallVisitor> visitors = new HashSet<>(MAX_STALL_VISITORS, 1.0f);
    private List<Product> storage = new ArrayList<>();
    private StallDoor mainDoor = new StallDoor(this);
    private CashierPlace cashierPlace = new CashierPlace(this);

    public StallDoor getMainDoor() {
        return mainDoor;
    }

    public CashierPlace getCashierPlace() {
        return cashierPlace;
    }

    public void addVisitor(StallVisitor visitor) {
        if (!visitors.contains(visitor)) {
            visitors.add(visitor);
        }
    }

    public void removeVisitor(StallVisitor visitor) {
        visitors.remove(visitor);
    }

    public void loadProducts(List<Product> products) {
        storage.addAll(products);
    }

    public void addVisitorToCashierPlace(MainChar seller) {
        if (visitors.contains(seller)) {
            cashierPlace.enter(seller);
        } else {
            throw new RuntimeException("You are outside the stall!");
        }
    }

    public List<Product> getStorage() {
        return storage;
    }
}
