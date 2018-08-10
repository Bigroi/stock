package com.bigroi.stock.service;

import java.util.Locale;
import java.util.Map;

public interface LabelService {

	String getLabel(String category, String name, Locale language);
	
	Map<String, String> getAllLabel(Locale language);
}
