package com.example.tiamo.sumproject;

import java.util.regex.Pattern;

public class Validator {
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";


    public static Boolean isPhoneValidator(String str){
        return Pattern.matches(REGEX_MOBILE,str);
    }
    public static Boolean isPwdValidator(String str){
        return Pattern.matches(REGEX_PASSWORD,str);
    }
}
