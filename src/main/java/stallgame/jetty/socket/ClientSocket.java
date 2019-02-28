package stallgame.jetty.socket;

import com.google.gson.*;
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class ClientSocket {

    private JsonParser parser = new JsonParser();
    private static final Logger LOGGER = LogManager.getLogger(ClientSocket.class.getName());

    @SuppressWarnings("unused")
    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOGGER.trace("Connection closed: " + statusCode + " - " + reason);
        this.session.close();
        this.session = null;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOGGER.trace("Got connection!");
        this.session = session;
        GameClient.clientSocket = this;
    }

    @OnWebSocketMessage
    public void onMessage(byte[] msg, int offset, int length) {
        LOGGER.trace("Got message!");
        Object obj = SerializationUtils.deserialize(msg);
        if (obj instanceof World) {
            LOGGER.trace("World instance message!");
            GameClient.worldServer = (World) obj;
        }
        if (obj instanceof String) {
            LOGGER.trace("Text message: " + obj);
            JsonObject json = null;

            try {
                json = parser.parse(((String) obj).toLowerCase()).getAsJsonObject();
            } catch (JsonParseException e) {
                LOGGER.trace("Message is not in json format: " + obj);
            }

            if (null != json) {
                if (json.has("worlds")) {
                    JsonArray worlds = json.get("worlds").getAsJsonArray();
                    List<String> arr = new LinkedList<>();
                    for (JsonElement el: worlds) {
                        arr.add(el.getAsString());
                    }
                    GameClient.worldsUUIDs = arr;
                }
            }
        }
    }

    public void sendCreateNewWorldMsg() {
        JsonObject obj = new JsonObject();
        obj.addProperty("createWorld", true);
        sendStringMessage(obj.toString());
    }

    public void sendGetWorldMsg(String uuid) {
        JsonObject obj = new JsonObject();
        obj.addProperty("getWorld", uuid);
        sendStringMessage(obj.toString());
    }

    private void sendStringMessage(String msg) {
        LOGGER.trace("Sending text message: " + msg);
        byte[] data = SerializationUtils.serialize(msg);
        try {
            session.getRemote().sendBytes(ByteBuffer.wrap(data));
        } catch (IOException e) {
            LOGGER.trace("Exception in send sendStringMessage method: " + Arrays.toString(e.getStackTrace()));

        }
    }

}
