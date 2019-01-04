package stallgame.action;

import stallgame.Environment;
import stallgame.character.NonPlayableCharacter;

public enum Actions {

    ENTER_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.enterStall(environment.groceryStall.getMainDoor());
        }
    },
    LEAVE_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.leaveStall(environment.groceryStall.getMainDoor());
        }
    },
    LEAVE_AND_LOCK_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.leaveAndLockStall(environment.groceryStall.getMainDoor());
        }
    },
    BECOME_SELLER {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.enterCashierPlace(environment.groceryStall.getCashierPlace());
        }
    },
    LEAVE_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.leaveCashierPlace(environment.groceryStall.getCashierPlace());
        }
    },
    LEAVE_AND_LOCK_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.leaveAndLockCashierPlace(environment.groceryStall.getCashierPlace());
        }
    },
    BUY {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.buy(npc.wantedProducts(), environment.groceryStall.getCashierPlace());
        }
    },
    REVIEW_INVENTORY {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.reviewInventory();
        }
    };

    public abstract void execute(NonPlayableCharacter npc, Environment environment);
}
