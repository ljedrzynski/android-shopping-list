package pl.devone.shoppinglist.fragments.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ShoppingItem> ITEMS = new ArrayList<ShoppingItem>();


    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ShoppingItem> ITEM_MAP = new HashMap<String, ShoppingItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ShoppingItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ShoppingItem createDummyItem(int position) {
        return new ShoppingItem(String.valueOf(position), "Item " + position, 1, 1, true);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ShoppingItem {
        private final String id;
        private final String name;
        private final int quantity;
        private final double price;
        private final boolean isDone;

        public ShoppingItem(String id, String name, int quantity, double price, boolean isDone) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.isDone = isDone;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }

        public boolean isDone() {
            return isDone;
        }

        @Override
        public String toString() {
            return "ShoppingItem{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", price='" + price + '\'' +
                    ", isDone=" + isDone +
                    '}';
        }
    }
}
