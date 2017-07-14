package com.bigroi.stock.util;

import java.util.Random;

public class Key {
	public static String generate(int length) {
		String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
		Random rnd = new Random();
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rnd.nextInt(characters.length()));
		}
		return new String(text);
	}
}
