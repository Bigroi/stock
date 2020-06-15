package com.bigroi.stock.dao;

import com.bigroi.stock.bean.db.Label;

import java.util.List;
import java.util.Locale;

public interface LabelDao {

    String getLabel(String category, String name, Locale language);

    List<Label> getAllLabel(Locale language);

    List<Label> getAllLabel();

    Label getLabelById(long id);

    void add(Label label);

    boolean update(Label label);

    boolean delete(long id);

}
