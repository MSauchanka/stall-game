package stallgame.visual;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 320);
        setLocation(400, 400);
        setVisible(true);
    }

    public static void showWorldReport() {
        MainWindow window = new MainWindow();
        window.setTitle("World Reporter");
        window.add(new WorldStatusField());
    }
}
