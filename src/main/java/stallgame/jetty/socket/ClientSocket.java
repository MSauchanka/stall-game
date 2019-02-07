package stallgame.jetty.socket;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import stallgame.GameClient;
import stallgame.GameServer;
import stallgame.World;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class ClientSocket {

    private static final Logger LOGGER = LogManager.getLogger(ClientSocket.class.getName());

    @SuppressWarnings("unused")
    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOGGER.debug("Connection closed: %d - %s%n", statusCode, reason);
        this.session.close();
        this.session = null;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOGGER.debug("Got connect: %s%n", session);
        this.session = session;
        GameClient.clientSocket = this;
        try {
            session.getRemote().sendString("Hello");
            session.getRemote().sendString("Thanks for the conversation.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(byte[] msg, int offset, int length) {
        LOGGER.debug("Got worldLocal msg!");
        Object obj = SerializationUtils.deserialize(msg);
        if (obj instanceof World) {
            GameClient.worldServer = (World) obj;
        }
        if (obj instanceof String) {
            LOGGER.debug("Server message" + obj.toString());
        }
        LOGGER.debug("World was updated!");
    }

}
