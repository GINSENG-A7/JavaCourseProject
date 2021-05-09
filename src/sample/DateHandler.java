package sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHandler
{
    public static boolean isDate(String value) {
        return value.matches("(0[1-9]|[12][0-9]|3[01])[-/.](0[1-9]|1[012])[-/.](19|20)\\d\\d");
    }

    public static Date convertStringToDate(String value) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            return format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //public static String convertDateToString(Date value) { }
}
