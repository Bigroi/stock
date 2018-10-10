package com.bigroi.stock.messager.message;

public interface Message<T> {

	public void sendImediatly(T object, String locale);
	
	public boolean send(T object, String locale);

}
