package com.mycompany.clientapp.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IP {
    
    private static Pattern pattern;
    private static Matcher matcher;
    
    /**
     * Ip adress regex mask
     */
    private static final String IPADDRESS_PATTERN = 
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    static{
        pattern = Pattern.compile(IPADDRESS_PATTERN);
    }
    
    /**
    * Validate ip address with regular expression
    * @param ip ip address for validation
    * @return true valid ip address, false invalid ip address
    */
    public static boolean validate(final String ip){
        if (ip == null)
            return false;
        matcher = pattern.matcher(ip);
	return matcher.matches();
    }
}
