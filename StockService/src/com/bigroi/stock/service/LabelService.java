package com.bigroi.stock.service;

import com.bigroi.stock.bean.db.Label;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface LabelService {

    String getLabel(String category, String name, Locale language);

    Map<String, String> getAllLabel(Locale language);

    List<Label> getAllLabel();

    Label getLabelById(long id);

    void merge(Label label);

    void delete(long id);
}
