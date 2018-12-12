package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.Role;
import stallgame.StallVisitor;
import stallgame.character.NonPlayableCharacter;

public class StallDoor {

    private boolean isLocked = true;
    private GroceryStall groceryStall;

    public StallDoor(GroceryStall groceryStall) {
        this.groceryStall = groceryStall;
    }

    public void enter(NonPlayableCharacter visitor) {
        if (!isLocked) {
            groceryStall.addVisitor(visitor);
        } else {
            if (Role.SELLER.equals(visitor.getRole())) {
                isLocked = false;
                enter(visitor);
            } else {
                throw new RuntimeException("Sorry door is closed!");
            }
        }
    }

    public void leave(NonPlayableCharacter visitor) {
        if (!isLocked) {
            groceryStall.removeVisitor(visitor);
        } else {
            if (Role.SELLER.equals(visitor.getRole())) {
                isLocked = false;
                leave(visitor);
            } else {
                throw new RuntimeException("You are locked inside!");
            }
        }
    }
}
