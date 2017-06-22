package com.bigroi.stock.dao;

import java.lang.reflect.Field;
import java.util.Random;

import com.bigroi.stock.bean.User;

public class StubDao {

	private static final Random RANDOM = new Random();
	private final Class<?> clazz;
	
	public StubDao(Class<?> clazz){
		this.clazz = clazz;
	}
	
	public Object getById(int id) throws DaoException{
		try {
			Object object = clazz.newInstance();
			for (Field field : clazz.getDeclaredFields()){
				if (field.getType() == int.class  || field.getType() == Integer.class){
					field.setAccessible(true);
					field.set(object, RANDOM.nextInt());
					field.setAccessible(false);
				}
				if (field.getType() == long.class  || field.getType() == Long.class){
					field.setAccessible(true);
					field.set(object, RANDOM.nextLong());
					field.setAccessible(false);
				}
				
				if (field.getType() == String.class){
					field.setAccessible(true);
					field.set(object, "Test " + RANDOM.nextInt());
					field.setAccessible(false);
				}
				
				
			}
			return object;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DaoException(e);
		}
		
	}
}
