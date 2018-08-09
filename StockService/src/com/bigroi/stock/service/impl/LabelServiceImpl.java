package com.bigroi.stock.service.impl;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LabelDao;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Override
	public String getLabel(String category, String name, String language) {
		String label = labelDao.getLabel(category, name, language);
		if (label == null){
			return Label.getAnonimLabel(category, name);
		} else {
			return label;
		}
	}

	@Override
	public Map<String, String> getAllLabel(String language) throws ServiceException {
		try {
			return labelDao.getAllLabel(language).stream()
					.collect(Collectors.toMap(Label::generateKey, Label::getCurrentLanguage));
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
