package com.stock.entity.business;

import com.stock.entity.Language;

public class LabelRecord {

    private String name;
    private Language language;
    private String value;

    public LabelRecord() {

    }

    public LabelRecord(String name, Language language, String value) {
        this.name = name;
        this.language = language;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
