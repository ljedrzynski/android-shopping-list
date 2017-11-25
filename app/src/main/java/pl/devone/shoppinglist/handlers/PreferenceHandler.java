package pl.devone.shoppinglist.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ljedrzynski on 24.11.2017.
 */

public class PreferenceHandler {


    public static boolean isAutoDeleteMode(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean("remove_done_shopping_list", false);
    }

    public static int getCommonFontSize(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        //walkaround
        return Integer.valueOf(sharedPreferences.getString("font_size", "17"));
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
