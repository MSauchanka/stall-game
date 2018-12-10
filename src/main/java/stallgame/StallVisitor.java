package stallgame;

import stallgame.stall.StallDoor;

public abstract class StallVisitor {

    private boolean inStall = false;
    private boolean isCashier = false;

    // TODO: how to control count inside the stall object if we enter by outside logic
    public void enterStall(StallDoor door) {
        door.enter(this);
        inStall = true;
    }

    public void leaveStall(StallDoor door) {
        door.leave(this);
        inStall = false;
    }

    public boolean isInStall() {
        return inStall;
    }

    public boolean isCashier() {
        return isCashier;
    }

    public void setInStall(boolean inStall) {
        this.inStall = inStall;
    }

    public void setCashier(boolean cashier) {
        isCashier = cashier;
    }

    public String toString() {
        return "StallVisitor";
    }
}
