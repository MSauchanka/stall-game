package stallgame.jetty.operator;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import stallgame.jetty.socket.ServerSocket;

public class ServerOperator {

    public static volatile boolean isServerStarted = false;

    public static void runServer(int port) {

        Server server = new Server(port);

        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ServerSocket.class);
            }
        };

        try {
            server.setHandler(wsHandler);
            server.start();
            isServerStarted = "STARTED".equals(server.getState());
            server.join();
        } catch (Exception e) {
            System.err.println("Exception caught when running server:" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Done.");

    }

}
