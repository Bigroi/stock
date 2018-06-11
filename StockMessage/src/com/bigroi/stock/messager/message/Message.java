package com.bigroi.stock.messager.message;

public interface Message<T> {

	public void sendImediatly(T object) throws MessageException;
	
	public void send(T object) throws MessageException;

}
