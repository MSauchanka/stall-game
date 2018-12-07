package stallgame.stall.cashbox;

import stallgame.StallVisitor;
import stallgame.product.Product;

import java.util.List;

public class TransactionLogger {

    private StallVisitor salesPerson;
    private StallVisitor buyer;
    private List<Product> products;
    private int price;
    private String comment;

    private TransactionLogger() {
        throw new RuntimeException("Logger class!");
    }

    public static void logTransaction(StallVisitor salesPerson, StallVisitor buyer, List<Product> products, int price) {
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

    public static void logTransaction(StallVisitor salesPerson, StallVisitor buyer, List<Product> products, int price, String comment) {
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
