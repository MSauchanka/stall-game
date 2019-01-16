package stallgame.door;

public class NoPlaceToEnterException extends RuntimeException {

    protected NoPlaceToEnterException() {
        super("No place to enter!");
    }
}
