package com.bigroi.stock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationURL {

	public static boolean isValidURL(String url) {
		try {
			String regex = "\\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(url);
			matcher.matches();
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}
}
