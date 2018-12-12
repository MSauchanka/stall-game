package stallgame.character;

import stallgame.Role;
import stallgame.stall.CashierPlace;
import stallgame.stall.StallDoor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class NonPlayableCharacter {

    private String fullName;
    private Role role = Role.NO_ROLE;

    private boolean inStall = false;
    private boolean isCashier = false;

    public void enterStall(StallDoor door) {
        door.enter(this);
        inStall = true;
    }

    public void leaveStall(StallDoor door) {
        door.leave(this);
        inStall = false;
    }

    public void enterCashierPlace(CashierPlace place) {
        if (isInStall()) {
            place.enter(this);
            setCashier(true);
        } else {
            throw new RuntimeException("Enter the stall to take cashier place!");
        }
    }

    public boolean isInStall() {
        return inStall;
    }

    public boolean isCashier() {
        return isCashier;
    }

    public void setInStall(boolean inStall) {
        this.inStall = inStall;
    }

    public void setCashier(boolean cashier) {
        isCashier = cashier;
    }

    public NonPlayableCharacter() {
        generateName();
    }

    public String getFullName() {
        return fullName;
    }

    private void generateName() {
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("NpcNames")))) {
            while (br.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        int randomInt = new Random().ints(0, lines).limit(1).findFirst().getAsInt();
        try {
            String s = Files.readAllLines(Paths.get(getClass().getClassLoader()
                    .getResource("NpcNames").toURI())).get(randomInt);
            System.out.println(s);
            fullName = s;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
