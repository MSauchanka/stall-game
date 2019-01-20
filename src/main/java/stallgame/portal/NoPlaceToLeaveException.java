package stallgame.portal;

public class NoPlaceToLeaveException extends RuntimeException {

    public NoPlaceToLeaveException() {
        super("No place to leave!");
    }
}
