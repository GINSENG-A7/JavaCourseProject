package sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHandler
{
    private static DateFormat formatMySQL = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static boolean isDate(String value) {
        return value.matches("(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\d\\d");
    }

    public static Date convertStringToDate(String value) {
//        DateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        try {
            return formatMySQL.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToString(Date value) {
         return formatMySQL.format(value);
/*
         String day = String.valueOf(value.getDay());
         String month = String.valueOf(value.getMonth());
         String year = String.valueOf(value.getYear());
         return year + "-" + month + "-" + day;
*/
    }
}
