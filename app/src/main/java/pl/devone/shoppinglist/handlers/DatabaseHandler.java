package pl.devone.shoppinglist.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import pl.devone.shoppinglist.Utils.DateUtils;
import pl.devone.shoppinglist.models.ShoppingList;
import pl.devone.shoppinglist.models.ShoppingListItem;

/**
 * Created by ljedrzynski on 18.11.2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static Logger log = Logger.getLogger(DatabaseHandler.class.getCanonicalName());
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shoppingList";

    private static final String TABLE_SHOPPING_LIST = "shopping_list";
    private static final String TABLE_SHOPPING_LIST_ITEM = "shopping_list_item";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
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
        log.info("Creating table " + TABLE_SHOPPING_LIST);
        String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " + TABLE_SHOPPING_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COL_CREATED_AT + " TEXT)";
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);

        log.info("Creating table " + TABLE_SHOPPING_LIST_ITEM);
        String CREATE_SHOPPING_LIST_TABLE_ITEM = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COL_NAME + " TEXT,"
                + COL_QUANTITY + " INTEGER," + COL_PRICE + " REAL," + COL_DONE + " INTEGER," + FKEY_SHOPPING_LIST_ID + " INTEGER," +
                "FOREIGN KEY (" + FKEY_SHOPPING_LIST_ID + ") REFERENCES " + TABLE_SHOPPING_LIST + "(" + KEY_ID + ")" + ")";
        db.execSQL(CREATE_SHOPPING_LIST_TABLE_ITEM);

        log.info("Tables created with success.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
    }

    public List<ShoppingList> getShoppingLists() {
        List<ShoppingList> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        log.info("Fetching shoppingLists from database");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOPPING_LIST, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList();
                shoppingList.setId(cursor.getInt(0));
                shoppingList.setCreatedAt(DateUtils.formatStringToDate(cursor.getString(1), mContext));

                result.add(shoppingList);
            } while (cursor.moveToNext());
        }

        log.info("Fetched: " + result.size());

        return result;
    }

    public List<ShoppingListItem> getShoppingListItems(ShoppingList shoppingList) {
        if (shoppingList == null || shoppingList.getId() == 0) {
            throw new RuntimeException("Shopping list cannot be null/empty");
        }
        List<ShoppingListItem> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        log.info("Fetching items for shoppingList with id : " + shoppingList.getId() + " from database");

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingListItem shoppingListItem = new ShoppingListItem();
                shoppingListItem.setId(cursor.getInt(0));
                shoppingListItem.setName(cursor.getString(1));
                shoppingListItem.setQuantity(cursor.getInt(2));
                shoppingListItem.setPrice(cursor.getInt(3));
                shoppingListItem.setDone(cursor.getInt(4) == 1);
                result.add(shoppingListItem);
            } while (cursor.moveToNext());
        }

        log.info("Fetched: " + result.size());

        return result;
    }

    public void saveShoppingList(ShoppingList shoppingList) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            log.info("Saving shoppingList: " + shoppingList.toString());

            ContentValues values = new ContentValues();
            values.put(KEY_ID, shoppingList.getId());
            values.put(COL_CREATED_AT, DateUtils.formatDateToString(shoppingList.getCreatedAt(), mContext));

            db.insertWithOnConflict(TABLE_SHOPPING_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);

            if (shoppingList.getItems() != null) {
                for (ShoppingListItem shoppingListItem : shoppingList.getItems()) {
                    saveShoppingListItem(shoppingListItem, db);
                }
            }

            db.setTransactionSuccessful();
            log.info("Saved with success");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void saveShoppingListItem(ShoppingListItem shoppingListItem, SQLiteDatabase db) {
        if (db == null) {
            throw new RuntimeException("SQLiteDatabase object is null");
        }
        log.info("Saving shoppingListItem: " + shoppingListItem.toString());

        ContentValues values = new ContentValues();
        values.put(KEY_ID, shoppingListItem.getId());
        values.put(COL_NAME, shoppingListItem.getName());
        values.put(COL_QUANTITY, shoppingListItem.getQuantity());
        values.put(COL_PRICE, shoppingListItem.getPrice());
        values.put(COL_DONE, shoppingListItem.isDone());
        values.put(FKEY_SHOPPING_LIST_ID, shoppingListItem.getShoppingList().getId());

        db.insertWithOnConflict(TABLE_SHOPPING_LIST_ITEM, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        log.info("Saved with success");
    }

    public void saveShoppingListItem(ShoppingListItem shoppingListItem) {
        SQLiteDatabase db = this.getReadableDatabase();
        saveShoppingListItem(shoppingListItem, db);
        db.close();
    }

    public static DatabaseHandler getHandler(Context context) {
        return new DatabaseHandler(context);
    }
}
