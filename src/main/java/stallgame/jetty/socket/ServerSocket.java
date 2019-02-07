package stallgame.jetty.socket;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import stallgame.GameServer;
import stallgame.World;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

@WebSocket
public class ServerSocket {

    private static final Logger LOGGER = LogManager.getLogger(ClientSocket.class.getName());

    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOGGER.debug("Close: statusCode=" + statusCode + ", reason=" + reason);
        GameServer.serverSockets.remove(this);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        LOGGER.debug("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOGGER.debug("Connect: " + session.getRemoteAddress().getAddress());
        try {
            this.session = session;
            GameServer.serverSockets.add(this);
            session.getRemote().sendString("Hello Webbrowser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) throws IOException {
        LOGGER.debug("Message from client: " + message);
    }

    public void sendWorldInstance(World world) {
        LOGGER.debug("Sending worldLocal instance...");
        byte[] data = SerializationUtils.serialize(world);
        try {
            session.getRemote().sendBytes(ByteBuffer.wrap(data));
        } catch (IOException e) {
            LOGGER.debug("Exception in send worldLocal methods: " + Arrays.toString(e.getStackTrace()));

        }
    }
}
