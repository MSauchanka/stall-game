package stallgame;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.character.NonPlayableCharacter;
import stallgame.character.PlayableCharacter;
import stallgame.portal.PortalClosedException;
import stallgame.door.key.Key;

import static stallgame.Constants.*;

public class GroceryStallTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void leaveByDoor() {
        World world = World.create();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        npc.setRole(Role.SELLER);
        npc.getInventory().add(new Key(MAIN_DOOR_LOCK, MAIN_DOOR_KEY_DESCRIPTION));
        world.addVisitor(npc);
        PlayableCharacter mainChar = world.operateNpc(npc);
        mainChar.npc.enter(world.groceryStall.getMainDoor());
        Assert.assertEquals(1, world.groceryStall.getVisitors().size());
        mainChar.npc.leave(world.groceryStall.getMainDoor());
        Assert.assertEquals(0, world.groceryStall.getVisitors().size());
    }

    @Test
    public void leaveByDoorVisitor() {
        expectedEx.expect(PortalClosedException.class);
        expectedEx.expectMessage("Closed! Sorry!");

        World world = World.create();
        GroceryStall groceryStall = GroceryStall.createWith(world);
        NonPlayableCharacter visitor = new NonPlayableCharacter();
        visitor.enter(groceryStall.getMainDoor());
    }

    @Test
    public void toCashierPlaceOutside() {
        expectedEx.expect(PortalClosedException.class);
        expectedEx.expectMessage("Closed! Sorry!");

        World world = World.create();
        NonPlayableCharacter npc = new NonPlayableCharacter();
        PlayableCharacter mainChar = new PlayableCharacter(npc);
        mainChar.npc.enter(world.groceryStall.getCashierPlace().getDoor());
    }


}