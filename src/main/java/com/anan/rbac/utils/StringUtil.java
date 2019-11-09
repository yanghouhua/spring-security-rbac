package com.anan.rbac.utils;

/**
 * 字符串工具类
 */
public class StringUtil {


    public static String clearSpace(String str) {
        return str.replaceAll(" ", "");
    }

    public static String[] clearSpace(String... str) {
        String[] temps = new String[str.length];

        for(int i = 0; i < str.length; ++i) {
            temps[i] = str[i].replaceAll(" ", "");
        }

        return temps;
    }
}
