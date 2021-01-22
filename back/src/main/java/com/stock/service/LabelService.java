package com.stock.service;

import com.stock.entity.Language;
import com.stock.entity.ui.Label;

import java.util.List;
import java.util.Map;

public interface LabelService {

    Map<String, String> getLabels(Language language);

    List<Label> getAllLabelAsAdmin();

    boolean addAsAdmin(Label label);

    boolean updateAsAdmin(Label label);

    boolean deleteAsAdmin(String name);

    List<Language> getLanguages();
}
