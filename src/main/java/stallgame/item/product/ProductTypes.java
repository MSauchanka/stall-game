package stallgame.item.product;

import java.util.Arrays;
import java.util.List;

public enum ProductTypes {

    FOOD, DRINK, HOUSEHOLD_GOODS;

    public static List<ProductTypes> asList() {
        return Arrays.asList(FOOD, DRINK, HOUSEHOLD_GOODS);
    }


}
