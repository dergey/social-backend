package com.sergey.zhuravlev.social.util;

public class RandomStringUtils {

    private static final String ALPHA_NUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    private static final String NUMERIC_CHARS = "0123456789";
    private static final String ALPHABETIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

    public static String getAlphaNumericString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (ALPHA_NUMERIC_CHARS.length() * Math.random());
            sb.append(ALPHA_NUMERIC_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public static String getNumericString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (NUMERIC_CHARS.length() * Math.random());
            sb.append(NUMERIC_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public static String getAlphabeticString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (ALPHABETIC_CHARS.length() * Math.random());
            sb.append(ALPHABETIC_CHARS.charAt(index));
        }
        return sb.toString();
    }

}