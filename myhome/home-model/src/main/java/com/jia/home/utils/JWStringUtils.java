package com.jia.home.utils;

public class JWStringUtils {
    public static boolean isEmpty(Object object){
        if (object==null){
            return true;
        }
        if (object.equals("")){
            return true;
        }
        return object.equals("null");
    }
    public static boolean isNotEmpty(Object object){
        return object!=null && !object.equals("") && !object.equals("null");
    }
}
