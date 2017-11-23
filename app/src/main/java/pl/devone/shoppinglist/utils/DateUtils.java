package pl.devone.shoppinglist.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.devone.shoppinglist.R;

/**
 * Created by ljedrzynski on 18.11.2017.
 */

public class DateUtils {

    public static Date formatStringToDate(String date, Context context) {
        Date resultDate = null;
        try {
            resultDate = getDateFormat(context).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultDate;
    }

    public static String formatDateToString(Date date, Context context) {
        return getDateFormat(context).format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static DateFormat getDateFormat(Context context) {
        return new SimpleDateFormat(context.getString(R.string.date_format));
    }
}
