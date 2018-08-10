package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Label;

public interface LabelDao {

	String getLabel(String category, String name, String language);
	
	List<Label> getAllLabel(String language);
	
}
