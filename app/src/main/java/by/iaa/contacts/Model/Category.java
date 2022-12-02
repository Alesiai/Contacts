package by.iaa.contacts.Model;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    family(0),
    friends(1),
    study(2),
    hobby(3);

    private int value;
    private static Map map = new HashMap<>();

    private Category(int value) {
        this.value = value;
    }

    static {
        for (Category pageType : Category.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Category valueOf(int categoryType) {
        return (Category) map.get(categoryType);
    }

    public int getValue() {
        return value;
    }
}
