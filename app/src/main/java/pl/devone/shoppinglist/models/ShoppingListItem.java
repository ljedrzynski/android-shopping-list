package pl.devone.shoppinglist.models;

import java.io.Serializable;

public class ShoppingListItem implements Serializable {
    private int no;
    private String name;
    private int quantity;
    private double price;
    private boolean isDone;
    private ShoppingList shoppingList;

    public ShoppingListItem() {
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

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingListItem that = (ShoppingListItem) o;

        if (no != that.no) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (isDone != that.isDone) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return shoppingList != null ? shoppingList.equals(that.shoppingList) : that.shoppingList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = no;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (shoppingList != null ? shoppingList.hashCode() : 0);
        return result;
    }
}
