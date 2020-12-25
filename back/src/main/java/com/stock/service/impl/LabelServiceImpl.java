package com.stock.service.impl;

import com.stock.dao.LabelDao;
import com.stock.entity.Language;
import com.stock.entity.business.LabelRecord;
import com.stock.entity.ui.Label;
import com.stock.service.LabelService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LabelServiceImpl implements LabelService {

    private final LabelDao labelDao;


    public LabelServiceImpl(LabelDao labelDao) {
        this.labelDao = labelDao;
    }

    @Override
    public Map<String, String> getLabels(Language language) {
        return labelDao.getAll()
                .stream()
                .filter(r -> r.getLanguage().equals(language))
                .collect(Collectors.toMap(LabelRecord::getName, LabelRecord::getValue));
    }

    @Override
    public List<Label> getAllLabelAsAdmin() {
        return labelDao.getAll()
                .stream()
                .collect(Collectors.groupingBy(LabelRecord::getName))
                .entrySet()
                .stream()
                .map(e -> new Label(
                        e.getKey(),
                        e.getValue()
                                .stream()
                                .collect(Collectors.toMap(LabelRecord::getLanguage, LabelRecord::getValue)))
                )
                .collect(Collectors.toList());
    }

    @Override
    public boolean addAsAdmin(Label label) {
        if (!labelDao.getByName(label.getName()).isEmpty()) {
            return false;
        }
        var records = Language.getStream()
                .map(language -> new LabelRecord(
                        label.getName(),
                        language,
                        label.getLabels().getOrDefault(language, "")
                ))
                .collect(Collectors.toList());

        labelDao.create(records);
        return true;
    }

    @Override
    public boolean updateAsAdmin(Label label) {
        var records = Language.getStream()
                .map(language -> new LabelRecord(
                        label.getName(),
                        language,
                        label.getLabels().getOrDefault(language, "")
                ))
                .collect(Collectors.toList());

        for (var bool : labelDao.update(records)) {
            if (!bool) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteAsAdmin(String name) {
        return labelDao.deleteByName(name);
    }

    @Override
    public List<Language> getLanguages() {
        return Arrays.asList(Language.values());
    }
}
