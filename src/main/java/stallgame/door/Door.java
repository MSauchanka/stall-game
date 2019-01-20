package stallgame.door;

import stallgame.area.Area;
import stallgame.door.key.Lock;
import stallgame.portal.Portal;

public class Door extends Portal {

    public static Door createWith(Area inside, Area outside, Lock lock) {
        return new Door(inside, outside, lock);
    }

    private Door(Area inside, Area outside, Lock lock) {
        super(lock, inside, outside);
    }
}
