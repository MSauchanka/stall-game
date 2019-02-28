package stallgame.jetty.operator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import stallgame.jetty.socket.ClientSocket;

import java.net.URI;

public class ClientOperator {

    public static WebSocketClient client;
    private static final Logger LOGGER = LogManager.getLogger(ClientSocket.class.getName());

    public static void connect(String uri) {
        LOGGER.debug("Start connect method");
        client = new WebSocketClient();
        ClientSocket socket = new ClientSocket();

        try {
            client.start();
            URI echoUri = new URI(uri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);

            LOGGER.debug("Connecting to : " + echoUri);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
