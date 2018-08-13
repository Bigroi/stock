package com.bigroi.stock.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public abstract class BaseTest {

	protected final static Random random = new Random();
	
	protected <T> T createObject(Class<T> clazz) {
		return createObject(clazz, false); 
	}
	
	protected <T> T createObject(Class<T> clazz, boolean createObjects) {
		try {
			T object = clazz.newInstance();
			for (Field field : clazz.getDeclaredFields()) {
				if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
					continue;
				} else if (!isValueNull(field, object)){
					continue;
				}
				Object value = getRundomVlaue(field, createObjects);
				setField(value, field, object);
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Object getRundomVlaue(Field field, boolean createObjects) throws Exception {
		if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
			return random.nextInt();
		} else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
			return random.nextLong();
		} else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
			return random.nextDouble();
		} else if (field.getType().equals(byte.class) || field.getType().equals(Byte.class)) {
			return random.nextInt();
		} else if (field.getType().equals(short.class) || field.getType().equals(Short.class)) {
			return random.nextInt();
		} else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
			return random.nextFloat();
		} else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
			return random.nextInt();
		} else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
			return random.nextBoolean();
		} else if (field.getType().isEnum()) {
			return ((Object[])field.getType().getMethod("values").invoke(null))[0];
		} else if (field.getType().isArray()) {
			return Array.newInstance(field.getType().getComponentType(), 0);
		} else if (createObjects) {
			return createObject(field.getType());
		} else {
			return null;
		}
	}

	private boolean isValueNull(Field field, Object instance) throws Exception{
		boolean accessable = field.isAccessible();
		field.setAccessible(true);
		Object value = field.get(instance);
		field.setAccessible(accessable);
		return value == null;
	}

	private void setField(Object value, Field field, Object object) throws Exception {
		boolean accessable = field.isAccessible();
		field.setAccessible(true);
		field.set(object, value);
		field.setAccessible(accessable);
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
