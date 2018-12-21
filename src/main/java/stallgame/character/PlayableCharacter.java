package stallgame.character;

import stallgame.Constants;
import stallgame.Role;
import stallgame.action.Actions;
import stallgame.item.Item;
import stallgame.item.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            Optional<Item> keyMainDoor = npc.getInventory().stream()
                    .filter(item -> Constants.MAIN_DOOR_KEY_DESCRIPTION.equals(item.getDescription()))
                    .findAny();

            Optional<Item> keyCashierPlace = npc.getInventory().stream()
                    .filter(item -> Constants.MAIN_DOOR_KEY_DESCRIPTION.equals(item.getDescription()))
                    .findAny();

            if (keyMainDoor.isPresent()) {
                if (keyCashierPlace.isPresent()) {
                    return asList(Actions.LEAVE_STALL, Actions.LEAVE_AND_LOCK_STALL, Actions.BECOME_SELLER);
                }
                return asList(Actions.LEAVE_STALL, Actions.LEAVE_AND_LOCK_STALL);
            }

            return singletonList(Actions.LEAVE_STALL);
        }
        if (Role.SELLER.equals(npc.getRole())) {
            Optional<Item> keyCashierPlace = npc.getInventory().stream()
                    .filter(item -> Constants.MAIN_DOOR_KEY_DESCRIPTION.equals(item.getDescription()))
                    .findAny();

            if (keyCashierPlace.isPresent()) {
                return asList(Actions.LEAVE_SELLER_PLACE, Actions.LEAVE_AND_LOCK_SELLER_PLACE);
            }

            return singletonList(Actions.LEAVE_SELLER_PLACE);
        }

        return new ArrayList<>();
    }
}
