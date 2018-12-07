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
    private StallVisitor cashier;

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

    public Cashbox getCashbox(StallVisitor visitor) {
        // TODO: ask about ifs
        if (visitors.contains(visitor)) {
            if (null == cashier) {
                cashier = visitor;
                return cashbox;
            } else if (cashier.equals(visitor)) {
                return cashbox;
            } else {
                // TODO: ask about exceptions
                throw new RuntimeException("You are not cashier to get the cashbox!");
            }
        } else {
            throw new RuntimeException("You are not inside the stall to get the cashbox!");
        }

    }
}
