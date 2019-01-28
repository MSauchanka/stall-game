package stallgame.jetty;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import stallgame.GameServer;
import stallgame.World;

import java.io.IOException;
import java.nio.ByteBuffer;

@WebSocket
public class ServerSocket {

    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        try {
            this.session = session;
            session.getRemote().sendString("Hello Webbrowser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) throws IOException {
        System.out.println("Message: " + message);
        byte[] data = SerializationUtils.serialize(GameServer.worlds.get(0)) ;
        session.getRemote().sendBytes(ByteBuffer.wrap(data));
        System.out.println("Data was sent!");
    }


}
