package com.bigroi.stock.util;

import java.util.Random;

public class Generator {
	
	private static final String charsCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String chars = "abcdefghijklmnopqrstuvwxyz";
	private static final String nums = "0123456789";
	private static final String symbols = "!@#$%^&*_=+-/ˆ.?<>)";
	
	private static final String linkKeyChars = nums + chars + charsCaps;
	private static final String passwordChars = charsCaps + chars + nums + symbols;
	
	private static final Random rnd = new Random();
	
	public static String generateLinkKey(int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = linkKeyChars.charAt(rnd.nextInt(linkKeyChars.length()));
		}
		return new String(text);
	}
	
	public static String generatePass(int lengthOfPass) {
		char[] password = new char[lengthOfPass];
		for (int i = 0; i < lengthOfPass; i++) {
			password[i] = passwordChars.charAt(rnd.nextInt(passwordChars.length()));
		}
		return new String(password);
	}
}
