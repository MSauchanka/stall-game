package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.key.Lock;
import stallgame.item.product.Product;
import stallgame.stall.cashbox.Cashbox;

import java.util.List;

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

    public boolean isProductsAvailable() {
        return !groceryStall.getStorage().isEmpty();
    }

    public List<Product> getStorage() {
        return groceryStall.getStorage();
    }

    public Cashbox getCashbox() {
        return cashbox;
    }


}
