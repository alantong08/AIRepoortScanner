package com.citi.alan.myproject.tess4j.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    private DateUtil(){}
    
    public static String getFormatDateStr(String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        String result = ""; 
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            result = dateTime.format(formatter);
        }catch(Exception se){
            se.printStackTrace();
        }
        return result;
    }

}
