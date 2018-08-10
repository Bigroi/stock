package com.bigroi.stock.messager.message;

public interface Message<T> {

	public void sendImediatly(T object);
	
	public void send(T object);

}
