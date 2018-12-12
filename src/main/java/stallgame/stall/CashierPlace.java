package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.character.NonPlayableCharacter;
import stallgame.product.Product;
import stallgame.stall.cashbox.Cashbox;

import java.util.List;

public class CashierPlace {

    private NonPlayableCharacter seller;
    private Cashbox cashbox = new Cashbox(this);
    private GroceryStall groceryStall;

    public CashierPlace(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;
    }

    public void enter(NonPlayableCharacter seller) {
        if (null == this.seller) {
            this.seller = seller;
        } else {
            if (!this.seller.equals(seller)) {
                throw new RuntimeException(seller.toString() + " is cashier already!");
            }
        }
    }

    public void leave() {
        seller = null;
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
