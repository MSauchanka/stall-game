package stallgame.portal;

public class PortalClosedException extends RuntimeException {

    public PortalClosedException() {
        super("Closed! Sorry!");
    }
}
