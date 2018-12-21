package stallgame.item.key;

import stallgame.item.Item;

public class Key extends Item {

    private String keyword;

    public Key(String keyword, String description) {
        super(description);
        this.keyword = keyword;
    }

    protected String getKeyword() {
        return keyword;
    }
}
