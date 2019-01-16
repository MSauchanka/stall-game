package stallgame.character;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.Goal;
import stallgame.Role;
import stallgame.item.Item;
import stallgame.item.product.Product;
import stallgame.item.product.ProductTypes;
import stallgame.stall.CashierPlace;
import stallgame.door.Door;
import stallgame.stall.cashbox.Cashbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static stallgame.Constants.MEAT_FOOD;
import static stallgame.Constants.MEAT_FOOD_DESCRIPTION;

public class NonPlayableCharacter {

    private static final Logger LOGGER = LogManager.getLogger(NonPlayableCharacter.class.getName());
    public static final int VISITOR_ON_SPAWN_MONEY_AMOUNT = 10;
    public static final int MIN_AT_ROLE_TIME = 10;

    private int money = VISITOR_ON_SPAWN_MONEY_AMOUNT;
    private List<Item> inventory = new ArrayList<>();

    private Role role = Role.NO_ROLE;
    private Goal goal = Goal.TO_STALL_BUY;

    public int atRole = 0;

    private String fullName;

    public NonPlayableCharacter() {
        generateName();
    }

    public void buy(List<Product> products, CashierPlace cashierPlace) {
        // validate visitor place
        if (null != cashierPlace.observe()) {
            if (cashierPlace.isProductsAvailable(products)) {
                NonPlayableCharacter seller = cashierPlace.observe();
                int productsPrice = products.stream().mapToInt(Product::getPrice).sum();
                if (productsPrice <= countMoney()) {
                    try {
                        Cashbox cashbox = cashierPlace.getCashbox();
                        cashbox.registerTransaction(seller, products, productsPrice, this, "From npc.buy");
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

    public void reviewInventory() {
        LOGGER.info(System.lineSeparator());
        LOGGER.info("Inventory:");
        if (inventory.isEmpty()) {
            LOGGER.info("- No items in the inventory!");
        }
        IntStream.range(0, inventory.size()).forEach(idx -> LOGGER.info(idx + ". " + inventory.get(idx).getDescription()));
        LOGGER.info(System.lineSeparator());
    }

    private void generateName() {
        String line;
        ArrayList<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/NpcNames")))) {
            while ((line = br.readLine()) != null) {
                names.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fullName = names.get(new Random().nextInt(names.size()));
    }

    public List<Product> wantedProducts() {
        return singletonList(new Product(ProductTypes.FOOD, MEAT_FOOD, 7, MEAT_FOOD_DESCRIPTION));
    }

    public void enter(Door door) {
        door.enter(this);
    }

    public void leave(Door door) {
        door.leave(this);
    }

    public void leaveAndLock(Door door) {
        door.leaveAndLock(this);
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

    public List<Item> getInventory() {
        return inventory;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        atRole = 0;
    }

    public String getFullName() {
        return fullName;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
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
