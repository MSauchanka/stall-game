package stallgame.item.product;

import stallgame.item.Item;

public class Product extends Item {

    private int price;
    private ProductTypes type;
    private String name;

    public Product(ProductTypes types, String name, int price, String description) {
        super(description);
        this.price = price;
        this.type = types;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public ProductTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
