package stallgame.stall.cashbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stallgame.Server;
import stallgame.character.NonPlayableCharacter;
import stallgame.item.product.Product;

import java.util.List;

public class TransactionLogger {

    private NonPlayableCharacter salesPerson;
    private NonPlayableCharacter buyer;
    private List<Product> products;
    private int price;
    private String comment;

    private static final Logger LOGGER = LogManager.getLogger(TransactionLogger.class.getName());

    private TransactionLogger() {
        throw new RuntimeException("Logger class!");
    }

    public static void logTransaction(NonPlayableCharacter salesPerson, NonPlayableCharacter buyer, List<Product> products, int price) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Print receipt for sales person: ")
                .append(salesPerson.toString())
                .append(" that sell ")
                .append(products.toString())
                .append(" with price ")
                .append(price)
                .append(" to buyer ")
                .append(buyer)
                .append(".");
        LOGGER.debug(sb.toString());
    }

    public static void logTransaction(NonPlayableCharacter salesPerson, NonPlayableCharacter buyer, List<Product> products, int price, String comment) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Print receipt for sales person: ")
                .append(salesPerson.toString())
                .append(" that sell ")
                .append(products.toString())
                .append(" with price ")
                .append(price)
                .append(" to buyer ")
                .append(buyer)
                .append(". Comments: ")
                .append(comment)
                .append(".");
        LOGGER.debug(sb.toString());
    }
}
