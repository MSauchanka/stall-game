package stallgame;

import org.junit.Assert;
import org.junit.Test;
import stallgame.jetty.ClientOperator;
import stallgame.jetty.ServerOperator;
import stallgame.visual.MainWindow;

public class ClientServerTest {

    @Test
    public void testClientServerConnection() throws InterruptedException {
        GameServer.worlds.add(GameServer.createWorld());
        new Thread(() -> ServerOperator.runServer(9009)).start();
        while (!ServerOperator.isServerStarted) {
            Thread.sleep(10);
        }
        ClientOperator.connect("ws://localhost:9009");
        Assert.assertNotEquals(null, GameClient.world);
    }

}
