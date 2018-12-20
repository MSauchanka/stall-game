package stallgame.item.key;

public class Lock {

    private String keyword;
    private boolean isLocked = true;

    public Lock(String keyword) {
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
