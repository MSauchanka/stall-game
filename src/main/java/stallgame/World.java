package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;

import java.util.HashSet;
import java.util.Set;

public class World {

    // START SNIPPET: world-1
    public GroceryStall groceryStall = new GroceryStall();
    public Set<NonPlayableCharacter> population = new HashSet<>();
    // END SNIPPET: world-1


    public int serverFramesFrequency = 24;
    public long tics = 0;

    public PlayableCharacter operateNpc(NonPlayableCharacter npc) {
        return new PlayableCharacter(npc);
    }
}
