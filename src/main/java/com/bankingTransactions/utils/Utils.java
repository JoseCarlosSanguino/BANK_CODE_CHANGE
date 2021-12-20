package com.bankingTransactions.utils;

import java.security.SecureRandom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String generateRandomString(int length) {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();
    
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            // 0-62 (exclusivo), retorno aleatorio 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
	
	public static boolean isValid(String text) {
	    if (text == null || !text.matches("\\d{4}-[01]\\d-[0-3]\\d"))
	        return false;
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    df.setLenient(false);
	    try {
	        df.parse(text);
	        return true;
	    } catch (ParseException ex) {
	        return false;
	    }
	}
	
	public  boolean isGreaterDate(String date3) {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		 Date newDate = new Date(System.currentTimeMillis());
		 String date=formatter.format(newDate);
		 Boolean response = false;
		 try {
			Date date1 = formatter.parse(date);
			Date date2 = formatter.parse(date3);
			
			if(date2.after(date1)) {
				response =  true;
			}else {
				response = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return response;
		
	}
	
public  boolean isEqualsDate(String date3) {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		 Date newDate = new Date(System.currentTimeMillis());
		 String date=formatter.format(newDate);
		 Boolean response = false;
		 try {
			Date date1 = formatter.parse(date);
			Date date2 = formatter.parse(date3);
			
			if(date2.equals(date1)) {
				response =  true;
			}else {
				response = false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return response;
		
	}

public  boolean isAftersDate(String date3) {
	
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
	 Date newDate = new Date(System.currentTimeMillis());
	 String date=formatter.format(newDate);
	 Boolean response = false;
	 try {
		Date date1 = formatter.parse(date);
		Date date2 = formatter.parse(date3);
		
		if(date2.before(date1)) {
			response =  true;
		}else {
			response = false;
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 return response;
	
}

}
