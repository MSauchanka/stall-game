package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.item.key.Lock;
import stallgame.item.product.Product;
import stallgame.stall.CashierPlace;
import stallgame.stall.Door;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static stallgame.Constants.BACK_DOOR_LOCK;

public class GroceryStall {

    private Door backDoor = new Door(this, new Lock(BACK_DOOR_LOCK));
    // private ProductsLoadPlace productsLoadPlace;
    private CashierPlace cashierPlace = new CashierPlace(this);
    // private RadioPlace radioPlace;
    // private RestPlace restPlace;
    // private StallWindow stallWindow;
    // private StallQueue stallQueue;

    public static final int MAX_STALL_VISITORS = 1;
    // TODO validate visitor
    public final HashSet<NonPlayableCharacter> visitors = new HashSet<>(MAX_STALL_VISITORS, 1.0f);
    private List<Product> storage = new ArrayList<>();
    private Door mainDoor = new Door(this, new Lock("mainDoor"));


    public Door getMainDoor() {
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
        visitor.setRole(Role.NO_ROLE);
    }

    public void loadProducts(List<Product> products) {
        storage.addAll(products);
    }

    public List<Product> getStorage() {
        return storage;
    }
}
