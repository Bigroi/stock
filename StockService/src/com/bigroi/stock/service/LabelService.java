package com.bigroi.stock.service;

import java.util.Map;

public interface LabelService {

	String getLabel(String category, String name, String language);
	
	Map<String, String> getAllLabel(String language) throws ServiceException;
}
