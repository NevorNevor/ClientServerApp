package com.mycompany.clientapp.config;

import java.util.Date;

public class Logger {
    
    /**
     * Log message to console + Date()
     * @param info log string
     * @return result text
     */
    public static String log(String info){
        Date date = new Date(System.currentTimeMillis());
        String logText = date + " " + info;
        System.out.println(logText);
        return logText;
    }
}
