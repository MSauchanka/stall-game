package stallgame.container;

public class MaxVisitorsCountException extends RuntimeException {

    protected MaxVisitorsCountException() {
        super("Maximum count of visitors is reached!");
    }
}
