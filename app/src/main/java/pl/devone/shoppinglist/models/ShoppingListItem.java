package pl.devone.shoppinglist.models;

public class ShoppingListItem {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private boolean isDone;
    private boolean isNew;

    public ShoppingListItem() {
    }

    public ShoppingListItem(int id, String name, int quantity, double price, boolean isDone, boolean isNew) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.isDone = isDone;
        this.isNew = isNew;
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
