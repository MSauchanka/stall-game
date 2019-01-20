package stallgame.portal;

public class PortalClosedException extends RuntimeException {

    protected PortalClosedException() {
        super("Closed! Sorry!");
    }
}
