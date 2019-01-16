package stallgame.container;

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
        Container container = new Container(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        container.addVisitor(npc);
        Assert.assertEquals(Role.VISITOR, npc.getRole());
    }

    @Test
    public void addVisitorException() {
        expectedEx.expect(MaxVisitorsCountException.class);
        expectedEx.expectMessage("Maximum count of visitors is reached!");

        Container container = new Container(1, Role.VISITOR);
        NonPlayableCharacter npc1 = new NonPlayableCharacter();
        NonPlayableCharacter npc2 = new NonPlayableCharacter();
        container.addVisitor(npc1);
        container.addVisitor(npc2);
    }

    @Test
    public void removeVisitor() {
        Container container = new Container(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        container.addVisitor(npc);
        container.removeVisitor(npc);
        Assert.assertEquals(Role.NO_ROLE, npc.getRole());
    }

    @Test
    public void removeVisitorException() {
        expectedEx.expect(OutOfVisitorsException.class);
        expectedEx.expectMessage("Visitor was not found to for removal!");

        Container container = new Container(1, Role.VISITOR);
        NonPlayableCharacter npc = new NonPlayableCharacter();
        container.addVisitor(npc);
        container.removeVisitor(npc);
        container.removeVisitor(npc);
    }
}