package com.bigroi.stock.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;
import java.util.concurrent.Callable;

public abstract class BaseTest {
	
	protected final static Random random = new Random();
	
	protected  <T> T createObject(Class<T> clazz){
		try {
			T object = clazz.newInstance();
			for(Field field : clazz.getDeclaredFields()){
				if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) ){
					continue;
				}
				if(field.getType().equals(int.class)){
					setField(random::nextInt, field, object);
				}else if(field.getType().equals(long.class)){
					setField(random::nextLong, field, object);
				}else if(field.getType().equals(double.class)){
					setField(random::nextDouble, field, object);
				}else if(field.getType().equals(byte.class)){
					setField(random::nextInt, field, object);
				}else if(field.getType().equals(short.class)){
					setField(random::nextInt, field, object);
				}else if(field.getType().equals(float.class)){
					setField(random::nextFloat, field, object);
				}else if(field.getType().equals(char.class)){
					setField(random::nextInt, field, object);
				}
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void setField(Callable<?> funnction, Field field, Object object) throws Exception{
		field.setAccessible(true);
		field.set(object, funnction.call());
		field.setAccessible(false);
	}
	
	/*private String randomString(){
		return null;
	}*/
}
