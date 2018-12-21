package stallgame.item.key;

public class WrongKeyException extends RuntimeException {

    public WrongKeyException() {
        super("You are trying to use wrong key!");
    }

    public WrongKeyException(String message) {
        super(message);
    }
}
