package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static java.sql.Timestamp d2t(Date d){
        if(d==null) return null;
        return new java.sql.Timestamp(d.getTime());
    }
    public static Date t2d(java.sql.Timestamp t){
        if(t==null) return null;
        return new Date(t.getTime());
    }
    public static Date convert(String date) {
        Date retValue = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            retValue = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retValue;
    }
}
