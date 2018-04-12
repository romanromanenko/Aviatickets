package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class helper {
    public static String getCurrentDate (String format, int addDays){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        Date date = cal.getTime();

        return dateFormat.format(date);

    }
}
