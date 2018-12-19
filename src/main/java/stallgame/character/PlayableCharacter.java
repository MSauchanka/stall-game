package stallgame.character;

import stallgame.Role;
import stallgame.action.Actions;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class PlayableCharacter {

    public NonPlayableCharacter npc;

    public PlayableCharacter(NonPlayableCharacter npc) {
        this.npc = npc;
    }

    public List<Actions> getActions() {
        if (Role.NO_ROLE.equals(npc.getRole())) {
            return singletonList(Actions.ENTER_STALL);
        }
        if (Role.VISITOR.equals(npc.getRole())) {
            return asList(Actions.BECOME_SELLER, Actions.LEAVE_STALL);
        }
        if (Role.SELLER.equals(npc.getRole())) {
            return asList(Actions.LEAVE_STALL);
        }

        return new ArrayList<>();
    }
}
