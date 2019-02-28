package stallgame.jetty.socket;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import stallgame.GameClient;
import stallgame.GameServer;
import stallgame.World;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

@WebSocket
public class ServerSocket {

    private static final Logger LOGGER = LogManager.getLogger(ClientSocket.class.getName());

    private Session session;
    private JsonParser parser = new JsonParser();

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOGGER.trace("Close: statusCode=" + statusCode + ", reason=" + reason);
        GameServer.serverSockets.remove(this);
        UUID uuid = GameServer.serverSocketByUuid.entrySet().stream()
                .filter(entry -> entry.getValue().equals(this))
                .findFirst().orElseThrow(RuntimeException::new)
                .getKey();
        GameServer.serverSocketByUuid.remove(uuid);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        LOGGER.trace("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        LOGGER.trace("Connect: " + session.getRemoteAddress().getAddress());
        this.session = session;
        GameServer.serverSockets.add(this);
        sendStringMessage(GameServer.worldsUUIDsAsJson());

    }

    @OnWebSocketMessage
    public void onMessage(byte[] msg, int offset, int length) throws IOException {
        Object obj = SerializationUtils.deserialize(msg);
        if (obj instanceof String) {
            String message = (String) obj;
            JsonObject json = null;

            try {
                json = parser.parse(message).getAsJsonObject();
            } catch (JsonParseException e) {
                LOGGER.trace("Message is not in json format: " + obj);
            }

            if (null != json) {
                if (json.has("createWorld")) {
                    boolean createWorld = json.get("createWorld").getAsBoolean();
                    if (createWorld) {
                        LOGGER.trace("Creating new world...");
                        World world = GameServer.createWorld();
                        GameServer.worlds.add(world);
                        GameServer.serverSocketByUuid.put(world.uuid, this);
                        GameServer.worldByUUID.put(world.uuid, world);
                        sendWorldInstance(world);
                        LOGGER.trace("World " + world.uuid.toString() + " has been created.");
                    }
                }

                if (json.has("getWorld")) {
                    UUID uuid = UUID.fromString(json.get("get").getAsString());
                    LOGGER.trace("Get World " + uuid.toString() + " has been received.");
                    if (GameServer.worldByUUID.containsKey(uuid)) {
                        sendWorldInstance(GameServer.worldByUUID.get(uuid));
                    } else {
                        LOGGER.trace("World " + uuid.toString() + " was not found.");
                        sendNoWorldError(uuid);
                    }
                }
            }
        }
    }

    public void sendWorldInstance(World world) {
        LOGGER.trace("Sending worldLocal instance...");
        byte[] data = SerializationUtils.serialize(world);
        try {
            if (session.isOpen()) {
                session.getRemote().sendBytes(ByteBuffer.wrap(data));
            } else {
                LOGGER.trace("World cannot be sent. Session is closed!");
            }
        } catch (IOException e) {
            LOGGER.trace("Exception in send worldLocal methods: " + Arrays.toString(e.getStackTrace()));

        }
    }

    public void sendStringMessage(String msg) {
        LOGGER.trace("Sending text message: " + msg);
        byte[] data = SerializationUtils.serialize(msg);
        try {
            session.getRemote().sendBytes(ByteBuffer.wrap(data));
        } catch (IOException e) {
            LOGGER.trace("Exception in send sendStringMessage method: " + Arrays.toString(e.getStackTrace()));

        }
    }

    public void sendNoWorldError(UUID uuid) throws IOException {
        JsonObject obj = new JsonObject();
        obj.addProperty("Error", "No world with uuid: " + uuid.toString());
        session.getRemote().sendString(obj.toString());
    }

    public Session getSession() {
        return session;
    }

}
