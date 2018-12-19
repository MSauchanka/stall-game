package stallgame.character;

import stallgame.Role;
import stallgame.product.Product;
import stallgame.stall.CashierPlace;
import stallgame.stall.StallDoor;
import stallgame.stall.cashbox.Cashbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NonPlayableCharacter {

    private String fullName;
    private int money = VISITOR_ON_SPAWN_MONEY_AMOUNT;
    private List<Product> inventory = new ArrayList<>();
    private Role role = Role.NO_ROLE;

    public static final int VISITOR_ON_SPAWN_MONEY_AMOUNT = 10;

    public NonPlayableCharacter() {
        generateName();
    }

    public void enterStall(StallDoor door) {
        door.enter(this);
        role = Role.VISITOR;
    }

    public void leaveStall(StallDoor door) {
        door.leave(this);
        role = Role.NO_ROLE;
    }

    public void enterCashierPlace(CashierPlace place) {
        if (!Role.NO_ROLE.equals(role)) {
            place.enter(this);
            role = Role.SELLER;
        } else {
            throw new RuntimeException("Enter the stall to take cashier place!");
        }
    }

    public void buy(List<Product> products, CashierPlace cashierPlace) {
        // validate visitor place
        if (null != cashierPlace.observe()) {
            if (cashierPlace.isProductsAvailable()) {
                NonPlayableCharacter seller = cashierPlace.observe();
                int productsPrice = products.stream().mapToInt(value -> value.price).sum();
                if (productsPrice <= countMoney()) {
                    try {
                        Cashbox cashbox = cashierPlace.getCashbox();
                        cashbox.registerTransaction(seller, products, productsPrice, this, "From visitor.buy");
                    } catch (Exception e) {
                        throw new RuntimeException("Transaction exception!");
                        // if fails rollback
                    }
                } else {
                    throw new RuntimeException("You have no enough money!");
                }
            } else {
                throw new RuntimeException("No products!");
            }
        } else {
            throw new RuntimeException("No cashier in the place!");
        }
    }

    public int pay(int amountToPay) {
        if (amountToPay <= money) {
            money -= amountToPay;
            return amountToPay;
        }

        throw new RuntimeException("Not enough money to pay!");
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
            fullName = s;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<Product> wantedProducts() {
        // TODO: question
        return new ArrayList<>();
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public int countMoney() {
        return money;
    }

    public int countProducts() {
        return inventory.size();
    }

    public void addProducts(List<Product> products) {
        inventory.addAll(products);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "NonPlayableCharacter{" +
                "fullName='" + fullName + '\'' +
                ", money=" + money +
                ", inventory=" + inventory +
                ", role=" + role +
                '}';
    }
}
