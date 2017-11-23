package pl.devone.shoppinglist.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.devone.shoppinglist.utils.DateUtils;
import pl.devone.shoppinglist.models.ShoppingList;
import pl.devone.shoppinglist.models.ShoppingListItem;

/**
 * Created by ljedrzynski on 18.11.2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHandler.class.getCanonicalName();
    private static final int DATABASE_VERSION = 12;

    private static final String DATABASE_NAME = "shoppingList";
    private static final String TABLE_SHOPPING_LIST = "shopping_list";

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        truncateTables();
//    }

    private static final String TABLE_SHOPPING_LIST_ITEM = "shopping_list_item";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String COL_NO = "no";
    private static final String COL_NAME = "name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_PRICE = "price";
    private static final String COL_DONE = "done";
    private static final String FKEY_SHOPPING_LIST_ID = "shopping_list_id";
    private static final String COL_CREATED_AT = "created_at";

    private final Context mContext;

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating table " + TABLE_SHOPPING_LIST);
        String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " + TABLE_SHOPPING_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_CREATED_AT + " TEXT," + COL_DONE + " INTEGER)";
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);

        Log.i(TAG, "Creating table " + TABLE_SHOPPING_LIST_ITEM);
        String CREATE_SHOPPING_LIST_TABLE_ITEM = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NO + " INT," + COL_NAME + " TEXT,"
                + COL_QUANTITY + " INTEGER," + COL_PRICE + " REAL," + COL_DONE + " INTEGER," + FKEY_SHOPPING_LIST_ID + " INTEGER," +
                "FOREIGN KEY (" + FKEY_SHOPPING_LIST_ID + ") REFERENCES " + TABLE_SHOPPING_LIST + "(" + KEY_ID + ")" + ")";
        db.execSQL(CREATE_SHOPPING_LIST_TABLE_ITEM);
        Log.i(TAG, "Tables created with success.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
        onCreate(db);
    }

    public List<ShoppingList> getShoppingLists() {
        List<ShoppingList> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i(TAG, "Fetching shoppingLists from database");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOPPING_LIST, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getInt(0));
                shoppingList.setCreatedAt(DateUtils.formatStringToDate(cursor.getString(1), mContext));
                shoppingList.setDone(cursor.getInt(2) == 1);
                result.add(shoppingList);
            } while (cursor.moveToNext());
        }

        Log.i(TAG, "Fetched: " + result.size());

        return result;
    }

    public List<ShoppingListItem> getShoppingListItems(ShoppingList shoppingList) {
        if (shoppingList == null || shoppingList.getId() == 0) {
            throw new RuntimeException("Shopping list cannot be null/empty");
        }
        List<ShoppingListItem> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.i(TAG, "Fetching items for shoppingList with id:" + shoppingList.getId() + " from database");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM + " WHERE " + FKEY_SHOPPING_LIST_ID + "=" + shoppingList.getId(), null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingListItem shoppingListItem = new ShoppingListItem();
                shoppingListItem.setId(cursor.getInt(0));
                shoppingListItem.setNo(cursor.getInt(1));
                shoppingListItem.setName(cursor.getString(2));
                shoppingListItem.setQuantity(cursor.getInt(3));
                shoppingListItem.setPrice(cursor.getInt(4));
                shoppingListItem.setDone(cursor.getInt(5) == 1);
                shoppingListItem.setShoppingList(shoppingList);
                result.add(shoppingListItem);
            } while (cursor.moveToNext());
        }

        Log.i(TAG, "Fetched: " + result.size());

        return result;
    }

    public void saveShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            Log.i(TAG, "Saving shoppingList: " + shoppingList.toString());

            ContentValues values = new ContentValues();
            if (shoppingList.getId() != 0) {
                values.put(KEY_ID, shoppingList.getId());
            }
            values.put(COL_CREATED_AT, DateUtils.formatDateToString(shoppingList.getCreatedAt(), mContext));
            values.put(COL_DONE, shoppingList.allItemsDone());

            long resId = db.insertWithOnConflict(TABLE_SHOPPING_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (resId > 0) {
                shoppingList.setId(resId);
                if (shoppingList.getItems() != null) {
                    for (ShoppingListItem shoppingListItem : shoppingList.getItems()) {
                        saveShoppingListItem(shoppingListItem, shoppingList, db);
                    }
                }
            }

            db.setTransactionSuccessful();
            Log.i(TAG, "Saved with success");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void saveShoppingListItem(ShoppingListItem shoppingListItem, ShoppingList shoppingList, SQLiteDatabase db) {
        if (db == null) {
            throw new RuntimeException("SQLiteDatabase object is null");
        }
        Log.i(TAG, "Saving shoppingListItem: " + shoppingListItem.toString());

        ContentValues values = new ContentValues();
        if (shoppingListItem.getId() != 0) {
            values.put(KEY_ID, shoppingListItem.getId());
        }
        values.put(COL_NO, shoppingListItem.getNo());
        values.put(COL_NAME, shoppingListItem.getName());
        values.put(COL_QUANTITY, shoppingListItem.getQuantity());
        values.put(COL_PRICE, shoppingListItem.getPrice());
        values.put(COL_DONE, shoppingListItem.isDone());
        values.put(FKEY_SHOPPING_LIST_ID, shoppingList.getId());

        Long resId = db.insertWithOnConflict(TABLE_SHOPPING_LIST_ITEM, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        if (resId > 0) {
            shoppingListItem.setId(resId);
        }

        Log.i(TAG, "Saved with success");
    }

    public static DatabaseHandler getHandler(Context context) {
        return new DatabaseHandler(context);
    }
}
