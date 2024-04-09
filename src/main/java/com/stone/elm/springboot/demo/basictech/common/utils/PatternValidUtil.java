package com.stone.elm.springboot.demo.basictech.common.utils;

import java.util.regex.Pattern;

public class PatternValidUtil {
    private PatternValidUtil() {
    }

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(
            "^1[3-9]\\d{9}$");

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 11) {
            return false;
        }
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }
}
