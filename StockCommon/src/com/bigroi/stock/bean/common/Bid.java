package com.bigroi.stock.bean.common;

import java.util.Date;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.Product;

public interface Bid {

	int getMaxVolume();

	void setMaxVolume(int volume);
	
	int getMinVolume();
	
	Date getCreationDate();
	
	double getPrice();
	
	Address getAddress();
	
	Product getProduct();

}
