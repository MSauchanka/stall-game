package stallgame.visual;

import javax.swing.*;

public class MainWindowButton extends JButton {

    public MainWindowButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x,y,width,height);
    }
}
