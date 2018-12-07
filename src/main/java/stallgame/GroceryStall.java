package stallgame;

import stallgame.product.Product;
import stallgame.stall.cashbox.Cashbox;
import stallgame.stall.StallDoor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroceryStall {

    public static final int MAX_STALL_VISITORS = 1;

    public final HashSet<StallVisitor> visitors = new HashSet<>(MAX_STALL_VISITORS, 1.0f);
    private List<Product> storage = new ArrayList<>();
    private StallDoor mainDoor = new StallDoor(this);
    private Cashbox cashbox = new Cashbox();

    public boolean isVisitorInside(StallVisitor visitor) {
        return visitors.contains(visitor);
    }

    public StallDoor getMainDoor() {
        return mainDoor;
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

    public Cashbox getCashbox() {
        return cashbox;
    }
}
