package stallgame.door;

public class DoorClosedException extends RuntimeException {

    protected DoorClosedException() {
        super("Sorry! Door is closed!");
    }
}
