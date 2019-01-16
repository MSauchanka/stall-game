package stallgame.door;

import stallgame.character.NonPlayableCharacter;
import stallgame.container.Container;
import stallgame.door.key.Key;
import stallgame.door.key.Lock;
import stallgame.door.key.WrongKeyException;

public class Door {

    private Lock lock;
    private Container inside;
    private Container outside;

    public static Door createWith(Container inside, Container outside, Lock lock) {
        return new Door(inside, outside, lock);
    }

    private Door(Container inside, Container outside, Lock lock) {
        this.lock = lock;
        this.inside = inside;
        this.outside = outside;
    }

    public void enter(NonPlayableCharacter npc) {
        if (!lock.isLocked()) {
            if (null != inside) {
                inside.addVisitor(npc);
            } else {
                throw new NoPlaceToEnterException();
            }
        } else {
            npc.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
                Key key = (Key) item;
                if (lock.fits(key)) {
                    lock.unlock(key);
                }
            });
            if (!lock.isLocked()) {
                enter(npc);
            } else {
                throw new DoorClosedException();
            }
        }
    }

    public void leaveAndLock(NonPlayableCharacter npc) {
        leave(npc);
        npc.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
            Key key = (Key) item;
            if (lock.fits(key)) {
                lock.lock(key);
            }
        });
        if (!lock.isLocked()) {
            throw new WrongKeyException("You have no key to close the stall!");
        }
    }

    public void leave(NonPlayableCharacter npc) {
        if (!lock.isLocked()) {
            if (null != outside) {
                inside.removeVisitor(npc);
                outside.addVisitor(npc);
            } else {
                throw new NoPlaceToLeaveException();
            }
        } else {
            npc.getInventory().stream().filter(item -> item instanceof Key).forEach(item -> {
                Key key = (Key) item;
                if (lock.fits(key)) {
                    lock.unlock(key);
                }
            });
            if (!lock.isLocked()) {
                leave(npc);
            } else {
                throw new WrongKeyException("You have no key to leave the stall!");
            }
        }
    }
}
