package stallgame;

import org.junit.Test;
import stallgame.jetty.ClientOperator;
import stallgame.jetty.ServerOperator;

public class ClientServerTest {

    @Test
    public void test() {
        ServerOperator.runServer(9009);
        new Thread(() -> ClientOperator.connect("ws://echo.websocket.org")).start();

    }

    @Test
    public void test1() {
        ClientOperator.connect("ws://localhost:9009");
    }
}
