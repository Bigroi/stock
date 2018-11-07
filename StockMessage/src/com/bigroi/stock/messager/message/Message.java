package com.bigroi.stock.messager.message;

import java.util.Locale;

public interface Message<T> {

	public void sendImediatly(T object, Locale locale);
	
	public boolean send(T object, Locale locale);

}
