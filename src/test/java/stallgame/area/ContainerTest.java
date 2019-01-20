package stallgame.area;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stallgame.Role;
import stallgame.character.NonPlayableCharacter;

public class ContainerTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void addVisitor() {
        Area area = new Area(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        area.addVisitor(npc);
        Assert.assertEquals(Role.VISITOR, npc.getRole());
    }

    @Test
    public void addVisitorException() {
        expectedEx.expect(MaxVisitorsCountException.class);
        expectedEx.expectMessage("Maximum count of visitors is reached!");

        Area area = new Area(1, Role.VISITOR);
        NonPlayableCharacter npc1 = new NonPlayableCharacter();
        NonPlayableCharacter npc2 = new NonPlayableCharacter();
        area.addVisitor(npc1);
        area.addVisitor(npc2);
    }

    @Test
    public void removeVisitor() {
        Area area = new Area(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        area.addVisitor(npc);
        area.removeVisitor(npc);
        Assert.assertEquals(Role.VISITOR, npc.getRole());
    }

    @Test
    public void removeVisitorException() {
        expectedEx.expect(OutOfVisitorsException.class);
        expectedEx.expectMessage("Visitor was not found to for removal!");

        Area area = new Area(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        area.addVisitor(npc);
        area.removeVisitor(npc);
        area.removeVisitor(npc);
    }
}