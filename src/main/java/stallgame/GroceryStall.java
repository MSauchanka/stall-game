package stallgame;

import stallgame.area.Area;
import stallgame.door.Door;
import stallgame.door.key.Lock;
import stallgame.item.product.Product;
import stallgame.stall.CashierPlace;

import java.util.ArrayList;
import java.util.List;

public class GroceryStall extends Area {

    private static final int MAX_STALL_VISITORS = 10;
    private static final String MAIN_DOOR_KEY = "mainDoor";

    private List<Product> storage = new ArrayList<>();

    private World world;
    private CashierPlace cashierPlace;
    private Door mainDoor;

    public static GroceryStall createWith(World world) {
        return new GroceryStall(MAX_STALL_VISITORS, Role.VISITOR)
                .addWorld(world)
                .addMainDoor()
                .addCashierPlace();
    }

    private GroceryStall(int maxVisitorsCount, Role role) {
        super(maxVisitorsCount, role);
    }

    public void loadProducts(List<Product> products) {
        storage.addAll(products);
    }

    public Door getMainDoor() {
        return mainDoor;
    }

    public CashierPlace getCashierPlace() {
        return cashierPlace;
    }

    public List<Product> getStorage() {
        return storage;
    }

    private GroceryStall addWorld(World world) {
        this.world = world;

        return this;
    }

    private GroceryStall addMainDoor() {
        this.mainDoor = Door.createWith(this, world, Lock.createWith(MAIN_DOOR_KEY));

        return this;
    }

    private GroceryStall addCashierPlace() {
        this.cashierPlace = CashierPlace.createWith(this);

        return this;
    }

}
