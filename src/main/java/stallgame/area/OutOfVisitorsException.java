package stallgame.area;

public class OutOfVisitorsException extends RuntimeException {

    public OutOfVisitorsException() {
        super("Visitor was not found to for removal!");
    }
}
