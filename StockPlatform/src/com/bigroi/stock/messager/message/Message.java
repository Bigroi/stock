package com.bigroi.stock.messager.message;

public interface Message<T> {

	public void setDataObject(T dataObject);
	
	public void sendImediatly() throws MessageException;
	
	public void send() throws MessageException;
}
