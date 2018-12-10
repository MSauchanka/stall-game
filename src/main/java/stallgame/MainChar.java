package stallgame;

import stallgame.stall.CashierPlace;

public class MainChar extends StallVisitor {

    public void enterCashierPlace(CashierPlace place) {
        if (isInStall()) {
            place.enter(this);
            setCashier(true);
        } else {
            throw new RuntimeException("Enter the stall to take cashier place!");
        }
    }

}
