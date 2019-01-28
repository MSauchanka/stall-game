package stallgame.portal;

import stallgame.area.Area;
import stallgame.character.NonPlayableCharacter;
import stallgame.door.key.Key;
import stallgame.door.key.Lock;
import stallgame.door.key.WrongKeyException;

import java.io.Serializable;

public class Portal implements Serializable {

    private Lock lock;
    private Area inside;
    private Area outside;

    public Portal(Lock lock, Area inside, Area outside) {
        this.lock = lock;
        this.inside = inside;
        this.outside = outside;
    }

    public void enter(NonPlayableCharacter npc) {
        if (!lock.isLocked()) {
            if (null != inside) {
                inside.addVisitor(npc);
                outside.removeVisitor(npc);
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
                throw new PortalClosedException();
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
