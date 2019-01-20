package stallgame.portal;

public class NoPlaceToEnterException extends RuntimeException {

    protected NoPlaceToEnterException() {
        super("No place to enter!");
    }
}
