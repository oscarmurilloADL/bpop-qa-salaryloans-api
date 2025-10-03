package co.com.bancopopular.automation.utils.dates;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimeUtils {
    private DateAndTimeUtils() {
    }

    public static Date getDateFormatyyyyMMddHHmm(String strDate) {
        var format = "yyyy/MM/dd HH:mm";
        var df = new SimpleDateFormat(format);
        var cDateNow = Calendar.getInstance();
        Date dNow=null;
        try {
            if(strDate.isEmpty()){
                dNow= df.parse(df.format(cDateNow.getTime()));
            }else{
                dNow = df.parse(strDate);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return  dNow;
    }

    public static Date addMinutes(Date date, int minutes){
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }
}
