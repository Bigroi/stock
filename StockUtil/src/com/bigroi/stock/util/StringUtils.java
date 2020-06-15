package com.bigroi.stock.util;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
