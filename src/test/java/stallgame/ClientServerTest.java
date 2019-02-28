package stallgame;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import stallgame.jetty.operator.ClientOperator;
import stallgame.jetty.operator.ServerOperator;

import java.util.Arrays;
import java.util.List;

public class ClientServerTest {

    @Test(timeout = 1000 * 10)
    public void testClientServerConnection() throws InterruptedException {
        GameServer.worlds.add(GameServer.createWorld());
        new Thread(() -> ServerOperator.runServer(9009)).start();
        while (!ServerOperator.isServerStarted) {
            Thread.sleep(10);
        }
        ClientOperator.connect("ws://localhost:9009");
        while (ClientOperator.client.getOpenSessions().isEmpty()) {
            Thread.sleep(10);
        }
        while (GameServer.serverSockets.isEmpty()) {
            Thread.sleep(10);
        }
        GameServer.serverSockets.get(0).sendWorldInstance(GameServer.worlds.get(0));
        while (null == GameClient.worldServer) {
            Thread.sleep(10);
        }
        Assert.assertNotEquals(null, GameClient.worldServer);
    }

    @Test
    public void tst() {
        JsonObject obj = new JsonObject();
        obj.addProperty("tst", true);
        System.out.println(obj.get("tst"));
    }

}
