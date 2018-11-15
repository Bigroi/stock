package com.bigroi.stock.util;

import java.util.Random;

public class Generator {
	
	private static final String CHARS_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMS = "0123456789";
	private static final String SYMBOLS = "";
	
	private static final String LINK_KEY_CHARS = NUMS + CHARS + CHARS_CAPS;
	private static final String PW_CHARS = CHARS_CAPS + CHARS + NUMS + SYMBOLS;
	
	private static final Random rnd = new Random();
	
	public static String generateLinkKey(int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = LINK_KEY_CHARS.charAt(rnd.nextInt(LINK_KEY_CHARS.length()));
		}
		return System.currentTimeMillis() + new String(text);
	}
	
	public static String generatePass(int lengthOfPass) {
		char[] password = new char[lengthOfPass];
		for (int i = 0; i < lengthOfPass; i++) {
			password[i] = PW_CHARS.charAt(rnd.nextInt(PW_CHARS.length()));
		}
		return new String(password);
	}
	
	private Generator() {}
}
