package stallgame.visual;

import stallgame.GameClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldsComboBox extends JComboBox<String> implements ActionListener {

    public WorldsComboBox() {
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String worldUuid = (String) cb.getSelectedItem();
        GameClient.clientSocket.sendGetWorldMsg(worldUuid);
    }
}
