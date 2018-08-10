package com.bigroi.stock.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.Builder;

import com.google.common.collect.ImmutableList;

public class LabelUtil {

	private static final List<Locale> SUPPORTED_LOCALES = ImmutableList.of(
			new Locale("en", "US"),
			new Locale("pl"), 
			new Locale("ru", "RU")); 
	
	public static String getLabelUndefinedValue(String category, String name){
		return "LABEL:" + category.toUpperCase() + ":" + name.toLowerCase();
	}
	
	public static Locale checkLocale(Locale locale){
		if (SUPPORTED_LOCALES.contains(locale)){
			return locale;
		} else {
			return SUPPORTED_LOCALES.get(0);
		}
	}

	public static Object getPassibleLanguages(Locale locale) {
		List<Locale> locales = new ArrayList<>(SUPPORTED_LOCALES);
		locales.remove(locale);
		locales.add(0, locale);
		return locales;
	}
	
	public static Locale parseString(String lang){
		String[] tmp = lang.split("_");
		
		Builder builder = new Builder().setLanguage(tmp[0]);
		if (tmp.length > 1){
			builder.setRegion(tmp[1]);
		}
		
		return builder.build();
	}
}
