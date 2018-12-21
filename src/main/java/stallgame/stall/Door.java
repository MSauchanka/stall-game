package stallgame.stall;

import stallgame.GroceryStall;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.key.Key;
import stallgame.item.key.Lock;
import stallgame.item.key.WrongKeyException;

public class Door {

    private GroceryStall groceryStall;
    private Lock lock;

    public Door(GroceryStall groceryStall, Lock lock) {
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
        leave(visitor);
        visitor.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
            Key key = (Key) item;
            if (lock.fits(key)) {
                lock.lock(key);
            }
        });
        if (!lock.isLocked()) {
            throw new WrongKeyException("You have no key to close the stall!");
        }
    }

    public void leave(NonPlayableCharacter visitor) {
        if (!lock.isLocked()) {
            groceryStall.removeVisitor(visitor);
        } else {
            visitor.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
                Key key = (Key) item;
                if (lock.fits(key)) {
                    lock.unlock(key);
                }
            });
            if (!lock.isLocked()) {
                leave(visitor);
            } else {
                throw new WrongKeyException("You have no key to leave the stall!");
            }
        }
    }
}
