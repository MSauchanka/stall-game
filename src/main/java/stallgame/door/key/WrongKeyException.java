package stallgame.door.key;

public class WrongKeyException extends RuntimeException {

    protected WrongKeyException() {
        super("You are trying to use wrong key!");
    }

    public WrongKeyException(String message) {
        super(message);
    }
}
