package stallgame.area;

public class MaxVisitorsCountException extends RuntimeException {

    protected MaxVisitorsCountException() {
        super("Maximum count of visitors is reached!");
    }
}
