package com.bigroi.stock.bean;

import java.text.SimpleDateFormat;

public interface Bid {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	public int getVolume();

	public void setVolume(int volume);
	
	public int getMinVolume();
}
