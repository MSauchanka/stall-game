package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.key.Key;
import stallgame.item.key.Lock;

public class StallDoor {

    private GroceryStall groceryStall;
    private Lock lock;

    public StallDoor(GroceryStall groceryStall, Lock lock) {
        this.groceryStall = groceryStall;
        this.lock = lock;
    }

    public void enter(NonPlayableCharacter visitor) {
        if (!lock.isLocked()) {
            groceryStall.addVisitor(visitor);
        } else {
            visitor.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
                Key key = (Key) item;
                if (lock.fits(key)) {
                    lock.unlock(key);
                }
            });
            if (!lock.isLocked()) {
                enter(visitor);
            } else {
                throw new RuntimeException("Sorry door is closed!");
            }
        }
    }

    public void leaveAndLock(NonPlayableCharacter visitor) {
        if (!lock.isLocked()) {
            groceryStall.removeVisitor(visitor);
        } else {
            visitor.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
                Key key = (Key) item;
                if (lock.fits(key)) {
                    lock.lock(key);
                }
            });
            if (lock.isLocked()) {
                leaveAndLock(visitor);
            } else {
                throw new RuntimeException("You are locked inside!");
            }
        }
    }

    public void leave(NonPlayableCharacter visitor) {
        // TODO: implement
    }
}
