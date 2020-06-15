package com.bigroi.stock.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonUtil {

    public static final String FORMATTER_PATTERN = "dd.MM.yyyy";
    private static final Gson gson = new GsonBuilder().setDateFormat(FORMATTER_PATTERN).create();

    public static Gson getGson() {
        return gson;
    }

    private GsonUtil() {
    }

}
