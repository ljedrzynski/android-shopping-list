package pl.devone.shoppinglist.models;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShoppingList implements Serializable {
    private int id;
    private Date createdAt;
    private List<ShoppingListItem> items;

    public ShoppingList() {
    }

    public ShoppingList(int id, Date createdAt, List<ShoppingListItem> items) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public int getItemsCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingList that = (ShoppingList) o;

        if (id != that.id) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", items=" + items +
                '}';
    }
}
