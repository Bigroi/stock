package com.bigroi.stock.bean;

import java.text.SimpleDateFormat;

public interface Bid {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	public int getMaxVolume();

	public void setMaxVolume(int volume);
	
	public int getMinVolume();

}
