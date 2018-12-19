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
    BECOME_SELLER {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.enterCashierPlace(environment.groceryStall.getCashierPlace());
        }
    },
    BUY {
        @Override
        public void execute(NonPlayableCharacter npc, Environment environment) {
            npc.buy(npc.wantedProducts(), environment.groceryStall.getCashierPlace());
        }
    };

    public abstract void execute(NonPlayableCharacter npc, Environment environment);
}
