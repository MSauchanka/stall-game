package stallgame.action;

import stallgame.World;
import stallgame.character.NonPlayableCharacter;

public enum Actions {

    ENTER_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.enter(world.groceryStall.getMainDoor());
        }
    },
    LEAVE_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leave(world.groceryStall.getMainDoor());
        }
    },
    LEAVE_AND_LOCK_STALL {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveAndLock(world.groceryStall.getMainDoor());
        }
    },
    BECOME_SELLER {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.enter(world.groceryStall.getCashierPlace().getDoor());
        }
    },
    LEAVE_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leave(world.groceryStall.getCashierPlace().getDoor());
        }
    },
    LEAVE_AND_LOCK_SELLER_PLACE {
        @Override
        public void execute(NonPlayableCharacter npc, World world) {
            npc.leaveAndLock(world.groceryStall.getCashierPlace().getDoor());
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
        }
    };

    public abstract void execute(NonPlayableCharacter npc, World world);
}
