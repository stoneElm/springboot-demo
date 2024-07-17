package com.stone.elm.springboot.demo.basictech.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

public class StoneStringUtil {
    private StoneStringUtil(){}

    public static String strUTFToISO(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        byte[] encodedBytes = str.getBytes(StandardCharsets.UTF_8);
        String encodedString = new String(encodedBytes, StandardCharsets.ISO_8859_1);

        return encodedString;
    }
}
