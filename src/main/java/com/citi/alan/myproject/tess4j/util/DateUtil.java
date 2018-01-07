package com.citi.alan.myproject.tess4j.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(ImageUtil.class);
	
    private DateUtil(){}
    
    public static String getFormatDateStr(String pattern){
        LocalDateTime dateTime = LocalDateTime.now();
        String result = ""; 
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            result = dateTime.format(formatter);
        }catch(Exception se){
            logger.error(se);
        }
        return result;
    }

}
