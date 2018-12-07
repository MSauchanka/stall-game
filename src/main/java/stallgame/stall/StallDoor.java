package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.MainChar;
import stallgame.StallVisitor;

public class StallDoor {

    private boolean isLocked = true;
    private GroceryStall groceryStall;

    public StallDoor(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;
    }

    public void enter(StallVisitor visitor) {
        if (!isLocked) {
            groceryStall.addVisitor(visitor);
        } else {
            if (visitor instanceof MainChar) {
                isLocked = false;
                enter(visitor);
            } else {
                throw new RuntimeException("Sorry door is closed!");
            }
        }
    }

    public void leave(StallVisitor visitor) {
        if (!isLocked) {
            groceryStall.removeVisitor(visitor);
        } else {
            if (visitor instanceof MainChar) {
                isLocked = false;
                leave(visitor);
            } else {
                throw new RuntimeException("You are locked inside!");
            }
        }
    }
}
