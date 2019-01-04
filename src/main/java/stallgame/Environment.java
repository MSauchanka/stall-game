package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;

import java.util.HashSet;
import java.util.Set;

public class Environment {

    public GroceryStall groceryStall = new GroceryStall();
    public Set<NonPlayableCharacter> npcs = new HashSet<>();
    public long tics = 0;
    public int frequency = 24;

    public PlayableCharacter operateNpc(NonPlayableCharacter npc) {
        return new PlayableCharacter(npc);
    }
}
