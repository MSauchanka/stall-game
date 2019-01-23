package stallgame.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class ServerOperator {

    public static void runServer(int port) {

        Server server = new Server(port);

        WebSocketHandler wsHandler = new WebSocketHandler()
        {
            @Override
            public void configure(WebSocketServletFactory factory)
            {
                factory.register(MyWebSocketHandler.class);
            }
        };

        try
        {
            server.setHandler(wsHandler);
            server.start();
            server.join();
        }
        catch (Exception e)
        {
            System.err.println("Exception caught when running server:" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Done.");

    }

}
