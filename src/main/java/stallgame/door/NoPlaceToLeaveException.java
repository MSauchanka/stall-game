package stallgame.door;

public class NoPlaceToLeaveException extends RuntimeException {

    public NoPlaceToLeaveException() {
        super("No place to leave!");
    }
}
