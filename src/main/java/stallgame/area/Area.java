package stallgame.area;

import stallgame.Role;
import stallgame.character.NonPlayableCharacter;

import java.util.ArrayList;
import java.util.List;

public class Area {

    private int maxVisitorsCount;
    private Role role;

    private List<NonPlayableCharacter> visitors = new ArrayList<>();

    public Area(int maxVisitorsCount, Role role) {
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
    }

    public List<NonPlayableCharacter> getVisitors() {
        return visitors;
    }

    public Role getRole() {
        return role;
    }
}
