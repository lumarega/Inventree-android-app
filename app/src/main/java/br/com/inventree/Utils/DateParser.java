package br.com.inventree.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    public static String parseDateFromTimestamp(String date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
    }

    public static Date getTimestampDate(){
        return new Date();
    }

}
