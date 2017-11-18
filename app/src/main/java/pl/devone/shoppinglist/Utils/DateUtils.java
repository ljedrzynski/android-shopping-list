package pl.devone.shoppinglist.Utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.devone.shoppinglist.R;

/**
 * Created by ljedrzynski on 18.11.2017.
 */

public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static Date formatStringToDate(String date, Context context) {
        Date resultDate = null;
        try {
            resultDate = new SimpleDateFormat(context.getString(R.string.date_format)).parse(date);
            System.out.println(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultDate;
    }

    public static String formatDateToString(Date date, Context context) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(context.getString(R.string.date_format));

        return df.format(date);
    }
}
