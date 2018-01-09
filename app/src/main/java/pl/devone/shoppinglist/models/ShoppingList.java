package pl.devone.shoppinglist.models;


import java.io.Serializable;
import java.util.List;

public class ShoppingList implements Serializable {
    private String uid;
    private int no;
    private String createdAt;
    private List<ShoppingListItem> items;
    private boolean isDone;

    public ShoppingList() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public int getItemsCount() {
        return items != null ? items.size() : 0;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean allItemsDone() {
        if (items == null) {
            return isDone;
        }
        for (ShoppingListItem item : items) {
            if (!item.isDone()) {
                return false;
            }
        }
        return true;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingList that = (ShoppingList) o;

        if (no != that.no) return false;
        if (isDone != that.isDone) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + no;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        return result;
    }


}
