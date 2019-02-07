package stallgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.jetty.operator.ClientOperator;
import stallgame.jetty.socket.ClientSocket;
import stallgame.visual.MainWindow;
import stallgame.visual.WorldStatusField;

import javax.swing.*;

public class GameClient {

    public static World worldServer;
    public static ClientSocket clientSocket;

    private static final Logger LOGGER = LogManager.getLogger(GameClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        ClientOperator.connect("ws://localhost:9009");
        while (null == worldServer) {
            Thread.sleep(1000);
        }
        SwingUtilities.invokeLater(() -> MainWindow.showWorldReport());
        for (;;) {
            WorldStatusField.world = worldServer;
        }
    }

}
