package com.bigroi.stock.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.dao.LabelDao;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.LabelUtil;

@Repository
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Override
	public String getLabel(String category, String name, Locale language) {
		String label = labelDao.getLabel(category, name, language);
		if (label == null){
			return LabelUtil.getLabelUndefinedValue(category, name);
		} else {
			return label;
		}
	}

	@Override
	public Map<String, String> getAllLabel(Locale language){
		return labelDao.getAllLabel(language).stream()
				.collect(Collectors.toMap(Label::generateKey, Label::getCurrentLanguage));
	}

	@Override
	public List<Label> getAllLabel() {
		return labelDao.getAllLabel();
	}

	@Override
	public Label getLabelById(long id) {
		Label label;
		if(id == -1){
			label = new Label();
			label.setId(id);
		}else{
			label = labelDao.getLabelById(id);
		}
		return label;
	}

	@Override
	public void merge(Label label) {
		if(label.getId() == -1){
			labelDao.add(label);
		}else{
			labelDao.update(label);
		}
		
	}

	@Override
	public void delete(long id) {
		labelDao.delete(id);
	}

}
