package stallgame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.jetty.operator.ClientOperator;
import stallgame.jetty.socket.ClientSocket;
import stallgame.visual.MainWindow;
import stallgame.visual.WorldStatusField;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class GameClient {

    public static World worldServer;
    public static ClientSocket clientSocket;
    public static List<String> worldsUUIDs;

    private static final Logger LOGGER = LogManager.getLogger(GameClient.class.getName());
    private static final String host = null != System.getProperty("serverHost") ? System.getProperty("serverHost") : "localhost:9009";

    public static void main(String[] args) throws InterruptedException {
        ClientOperator.connect("ws://" + host);
        while (null == worldServer && null == worldsUUIDs) {
            Thread.sleep(1000);
        }
        SwingUtilities.invokeLater(() -> MainWindow.showWorldReport());
        for (;;) {
            WorldStatusField.world = worldServer;
            WorldStatusField.worldsUUIDs = worldsUUIDs;
        }
    }

}
