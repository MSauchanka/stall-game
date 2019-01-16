package stallgame.container;

import stallgame.Role;
import stallgame.character.NonPlayableCharacter;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private int maxVisitorsCount;
    private Role role;

    private List<NonPlayableCharacter> visitors = new ArrayList<>();

    public Container(int maxVisitorsCount, Role role) {
        this.maxVisitorsCount = maxVisitorsCount;
        this.role = role;
    }

    public void addVisitor(NonPlayableCharacter npc) {
        if (maxVisitorsCount > visitors.size()) {
            visitors.add(npc);
            npc.setRole(role);
        } else {
            throw new MaxVisitorsCountException();
        }
    }

    public void removeVisitor(NonPlayableCharacter npc) {
        if (!visitors.remove(npc)) {
            throw new OutOfVisitorsException();
        }
        npc.setRole(Role.NO_ROLE);
    }

    public List<NonPlayableCharacter> getVisitors() {
        return visitors;
    }

    public Role getRole() {
        return role;
    }
}
