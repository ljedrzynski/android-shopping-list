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
public class ShoppingListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ShoppingList> ITEMS = new ArrayList<ShoppingList>();


    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ShoppingList> ITEM_MAP = new HashMap<String, ShoppingList>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ShoppingList item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ShoppingList createDummyItem(int position) {
        return new ShoppingList(String.valueOf(position), new Date(), new ArrayList<ShoppingList>());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


    public static class ShoppingList {
        private final String id;
        private final Date startDate;
        private List<ShoppingList> ShoppingLists;

        public ShoppingList(String id, Date startDate, List<ShoppingList> ShoppingLists) {
            this.id = id;
            this.startDate = startDate;
            this.ShoppingLists = ShoppingLists;
        }

        public String getId() {
            return id;
        }

        public Date getStartDate() {
            return startDate;
        }

        public List<ShoppingList> getShoppingLists() {
            return ShoppingLists;
        }

        public void setShoppingLists(List<ShoppingList> ShoppingLists) {
            this.ShoppingLists = ShoppingLists;
        }


        @Override
        public String toString() {
            return "ShoppingList{" +
                    "id='" + id + '\'' +
                    ", startDate=" + startDate +
                    ", ShoppingLists=" + ShoppingLists +
                    '}';
        }
    }
}
