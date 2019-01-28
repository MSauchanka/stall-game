package stallgame.door.key;

import java.io.Serializable;

public class Lock implements Serializable {

    private String keyword;
    private boolean isLocked = true;

    public static Lock createWith(String keyword) {
        return new Lock(keyword);
    }

    private Lock(String keyword) {
        this.keyword = keyword;
    }

    public void lock(Key key) {
        if (fits(key)) {
            isLocked = true;
        } else {
            throw new WrongKeyException();
        }
    }

    public void unlock(Key key) {
        if (fits(key)) {
            isLocked = false;
        } else {
            throw new WrongKeyException();
        }
    }

    public boolean fits(Key key) {
        return keyword.equals(key.getKeyword());
    }

    public boolean isLocked() {
        return isLocked;
    }
}
