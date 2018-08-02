package com.bigroi.stock.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;

public abstract class BaseTest {

	protected final static Random random = new Random();
	
	//protected final static String randomString = randomString();

	protected <T> T createObject(Class<T> clazz) {
		try {
			T object = clazz.newInstance();
			for (Field field : clazz.getDeclaredFields()) {
				if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				if (field.getType().equals(int.class)) {
					setField(random::nextInt, field, object);
				} else if (field.getType().equals(long.class)) {
					setField(random::nextLong, field, object);
				} else if (field.getType().equals(double.class)) {
					setField(random::nextDouble, field, object);
				} else if (field.getType().equals(byte.class)) {
					setField(random::nextInt, field, object);
				} else if (field.getType().equals(short.class)) {
					setField(random::nextInt, field, object);
				} else if (field.getType().equals(float.class)) {
					setField(random::nextFloat, field, object);
				} else if (field.getType().equals(char.class)) {
					setField(random::nextInt, field, object);
				}
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setField(Callable<?> funnction, Field field, Object object) throws Exception {
		field.setAccessible(true);
		field.set(object, funnction.call());
		field.setAccessible(false);
	}

	protected String randomString() {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
	
	protected Date getExpDate() throws ParseException{
		String dateStr = "17.06.2017";// anniversary stockPlatform ;-)
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
		Date date = format.parse(dateStr);
		return date;
	}
}
