package stallgame.stall.cashbox;

import stallgame.character.NonPlayableCharacter;
import stallgame.product.Product;

import java.util.List;

public class TransactionLogger {

    private NonPlayableCharacter salesPerson;
    private NonPlayableCharacter buyer;
    private List<Product> products;
    private int price;
    private String comment;

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
        System.out.println(sb.toString());
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
        System.out.println(sb.toString());
    }
}
