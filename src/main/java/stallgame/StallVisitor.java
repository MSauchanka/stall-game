package stallgame;

import stallgame.stall.StallDoor;

public abstract class StallVisitor {

    public void enterStall(StallDoor door) {
        door.enter(this);
    }

    public void leaveStall(StallDoor door) {
        door.leave(this);
    }
}
