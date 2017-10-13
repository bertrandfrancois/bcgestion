package com.testbuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTestBuilder {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat stringFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static Date date(String date){
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String toString(Date date) {
        return stringFormatter.format(date);
    }
}
