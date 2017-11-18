package pl.devone.shoppinglist.models;

public class ShoppingListItem {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private boolean isDone;
    private boolean isNew;
    private ShoppingList shoppingList;

    public ShoppingListItem() {
    }

    public ShoppingListItem(int id, String name, int quantity, double price, boolean isDone, boolean isNew, ShoppingList shoppingList) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isDone = isDone;
        this.isNew = isNew;
        this.shoppingList = shoppingList;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingListItem that = (ShoppingListItem) o;

        if (id != that.id) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (isDone != that.isDone) return false;
        if (isNew != that.isNew) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return shoppingList != null ? shoppingList.equals(that.shoppingList) : that.shoppingList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (isNew ? 1 : 0);
        result = 31 * result + (shoppingList != null ? shoppingList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", isDone=" + isDone +
                ", isNew=" + isNew +
                '}';
    }
}
