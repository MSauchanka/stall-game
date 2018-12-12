package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.product.Product;
import stallgame.stall.CashierPlace;
import stallgame.stall.StallDoor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GroceryStall {

    private StallDoor backDoor = new StallDoor(this);
    // private ProductsLoadPlace productsLoadPlace;
    private CashierPlace cashierPlace = new CashierPlace(this);
    // private RadioPlace radioPlace;
    // private RestPlace restPlace;
    // private StallWindow stallWindow;
    // private StallQueue stallQueue;

    public static final int MAX_STALL_VISITORS = 1;

    public final HashSet<NonPlayableCharacter> visitors = new HashSet<>(MAX_STALL_VISITORS, 1.0f);
    private List<Product> storage = new ArrayList<>();
    private StallDoor mainDoor = new StallDoor(this);


    public StallDoor getMainDoor() {
        return mainDoor;
    }

    public CashierPlace getCashierPlace() {
        return cashierPlace;
    }

    public void addVisitor(NonPlayableCharacter visitor) {
        if (!visitors.contains(visitor)) {
            visitors.add(visitor);
        }
    }

    public void removeVisitor(NonPlayableCharacter visitor) {
        visitors.remove(visitor);
    }

    public void loadProducts(List<Product> products) {
        storage.addAll(products);
    }

    public List<Product> getStorage() {
        return storage;
    }
}
