package com.bigroi.stock.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Builder;

public class LabelUtil {

    private LabelUtil() {

    }

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            new Locale("en", "US"),
            new Locale("pl"),
            new Locale("ru", "RU")
    );

    public static String getLabelUndefinedValue(String category, String name) {
        return "LABEL:" + category.toUpperCase() + ":" + name.toLowerCase();
    }

    public static Locale checkLocale(Locale locale) {
        return SUPPORTED_LOCALES.contains(locale)
                ? locale
                : SUPPORTED_LOCALES.get(0);
    }

    public static List<Locale> getPassibleLanguages(Locale locale) {
        var locales = new ArrayList<Locale>(SUPPORTED_LOCALES);
        if (locale != null) {
            locales.remove(locale);
            locales.add(0, locale);
        }
        return locales;
    }

    public static Locale parseString(String lang) {
        var tmp = lang.split("_");

        var builder = new Builder().setLanguage(tmp[0]);
        if (tmp.length > 1) {
            builder.setRegion(tmp[1]);
        }

        return checkLocale(builder.build());
    }
}
