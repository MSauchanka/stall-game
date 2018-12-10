package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.MainChar;
import stallgame.product.Product;
import stallgame.stall.cashbox.Cashbox;

import java.util.List;

public class CashierPlace {

    private MainChar seller;
    private Cashbox cashbox = new Cashbox(this);
    private GroceryStall groceryStall;

    public CashierPlace(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;
    }

    public void enter(MainChar seller) {
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

    public MainChar observe() {
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
