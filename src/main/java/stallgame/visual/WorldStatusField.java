package stallgame.visual;

import stallgame.GameClient;
import stallgame.Role;
import stallgame.World;
import stallgame.character.NonPlayableCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class WorldStatusField extends JPanel implements ActionListener {

    public static World world;
    public static java.util.List<String> worldsUUIDs;

    private java.util.List<MainWindowButton> buttonList = new LinkedList<>();
    private WorldsComboBox wcb = new WorldsComboBox();

    public WorldStatusField() {
        Timer timer = new Timer(250, this);
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(320, 320);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (null != world) {
            if (!buttonList.isEmpty()) {
                buttonList.forEach(super::remove);
                buttonList.clear();
            }
//            if (wcb.isDisplayable()) {
//                super.remove(wcb);
//            }
            g.drawString("NPC count: " + world.wrappedNpcs.size(), 5, 15);
            g.drawString(Role.VISITOR + " count : " + world.getAllNpcsStream()
                    .filter(npc -> Role.VISITOR.equals(npc.getRole()))
                    .count(), 5, 30);
            g.drawString(Role.SELLER + " name : " + world.groceryStall.getCashierPlace().getVisitors().stream()
                    .map(NonPlayableCharacter::getFullName)
                    .findFirst().orElse("No seller at the moment!"), 5, 45);
            g.drawString(Role.NO_ROLE + " count : " + world.getVisitors().size(), 5, 60);
            g.drawString("Grocery stall products count: " + world.groceryStall.getStorage().size(), 5, 75);
            g.drawString("Cashbox money count: " + world.groceryStall.getCashierPlace().getCashbox().countMoney(), 5, 90);
            g.drawString("Tics passed: " + world.tics, 5, 105);
        } else {
            if (worldsUUIDs != null) {
                g.drawString("Create new world or choose existing one!", 5, 15);
                if (buttonList.isEmpty()) {
                    int startButtonY = 30;
                    MainWindowButton newWorldButton = new MainWindowButton("Create new World!", 5, startButtonY, 180, 30);
                    newWorldButton.addActionListener((actionEvent) -> GameClient.clientSocket.sendCreateNewWorldMsg());
                    buttonList.add(newWorldButton);
                    super.add(newWorldButton);
                    worldsUUIDs.forEach(id -> {
                        MainWindowButton lastButton = buttonList.get(buttonList.size() - 1);
                        MainWindowButton button = new MainWindowButton(id, 5, lastButton.getY() + 25, 180, 30);
                        button.addActionListener((actionEvent) -> GameClient.clientSocket.sendGetWorldMsg(id));
                        buttonList.add(button);
                        super.add(button);
                    });
                }
            } else {
                g.drawString("Waiting for client connection to start!", 5, 15);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
