package com.bigroi.stock.dao;

import java.util.List;
import java.util.Locale;

import com.bigroi.stock.bean.db.Label;

public interface LabelDao {

	String getLabel(String category, String name, Locale language);
	
	List<Label> getAllLabel(Locale language);
	
}
