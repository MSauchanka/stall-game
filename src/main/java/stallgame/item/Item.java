package stallgame.item;

import java.io.Serializable;

public class Item implements Serializable {

    private String description;

    public Item(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
