package stallgame;

import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.area.Area;

import java.util.Set;
import java.util.stream.Stream;

public class World extends Area {

    private static final int MAX_WORLD_VISITORS = 100;

    // START SNIPPET: world-1
    public GroceryStall groceryStall;
    public PlayableCharacter mainChar;
    public Set<PlayableCharacter> wrappedNpcs;
    // END SNIPPET: world-1

    public int serverFramesFrequency = 24;
    public long tics = 0;

    public static World create() {
        return new World(MAX_WORLD_VISITORS, Role.NO_ROLE).addGroceryStall();
    }

    private World(int maxVisitorsCount, Role role) {
        super(maxVisitorsCount, role);
    }

    public PlayableCharacter operateNpc(NonPlayableCharacter npc) {
        this.mainChar = new PlayableCharacter(npc);

        return this.mainChar;
    }

    public Stream<NonPlayableCharacter> getAllNpcsStream() {
        return Stream.concat(getVisitors().stream(), groceryStall.getVisitors().stream());
    }

    private World addGroceryStall() {
        this.groceryStall = GroceryStall.createWith(this);

        return this;
    }


}
