package stallgame.visual;

import stallgame.Role;
import stallgame.World;
import stallgame.character.NonPlayableCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldStatusField extends JPanel implements ActionListener {

    private World world;

    public WorldStatusField(World world) {
        this.world = world;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
