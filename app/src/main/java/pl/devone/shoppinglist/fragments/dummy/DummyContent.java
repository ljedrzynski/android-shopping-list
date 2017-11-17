package pl.devone.shoppinglist.fragments.dummy;

import android.annotation.SuppressLint;

import java.util.ArrayList;
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
    public static final List<ShoppingListItem> ITEMS = new ArrayList<ShoppingListItem>();


    /**
     * A map of sample (dummy) items, by ID.
     */
    @SuppressLint("UseSparseArrays")
    public static final Map<Integer, ShoppingListItem> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ShoppingListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ShoppingListItem createDummyItem(int position) {
        return new ShoppingListItem(position, "Item " + position, 1, 1, true);
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
    public static class ShoppingListItem {
        private int id;
        private String name;
        private int quantity;
        private double price;
        private boolean isDone;
        private boolean isTransient = true;

        public ShoppingListItem() {
        }

        public ShoppingListItem(int id, String name, int quantity, double price, boolean isDone) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.isDone = isDone;
            this.isTransient = false;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isDone() {
            return isDone;
        }

        public void setDone(boolean done) {
            isDone = done;
        }

        public boolean isTransient() {
            return isTransient;
        }

        public void setTransient(boolean aTransient) {
            isTransient = aTransient;
        }

        @Override
        public String toString() {
            return "ShoppingListItem{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", quantity='" + quantity + '\'' +
                    ", price='" + price + '\'' +
                    ", isDone=" + isDone +
                    '}';
        }
    }
}
