package stallgame.action;

import stallgame.World;
import stallgame.character.NonPlayableCharacter;

public enum Actions {

    ENTER_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.enterStall(world.groceryStall.getMainDoor());
        }
    },
    LEAVE_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveStall(world.groceryStall.getMainDoor());
        }
    },
    LEAVE_AND_LOCK_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveAndLockStall(world.groceryStall.getMainDoor());
        }
    },
    BECOME_SELLER {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.enterCashierPlace(world.groceryStall.getCashierPlace());
        }
    },
    LEAVE_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveCashierPlace(world.groceryStall.getCashierPlace());
        }
    },
    LEAVE_AND_LOCK_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveAndLockCashierPlace(world.groceryStall.getCashierPlace());
        }
    },
    BUY {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.buy(npc.wantedProducts(), world.groceryStall.getCashierPlace());
        }
    },
    REVIEW_INVENTORY {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.reviewInventory();
        }
    },
    WAIT {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.atRole += 1;
            System.out.println();
        }
    };

    public abstract void execute(NonPlayableCharacter npc, World world);
}
