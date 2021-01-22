package com.stock.entity.ui;

import com.stock.entity.Language;

import java.util.Map;

public class Label {

    private String name;
    private Map<Language, String> labels;

    public Label() {

    }

    public Label(String name, Map<Language, String> labels) {
        this.name = name;
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Language, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<Language, String> labels) {
        this.labels = labels;
    }
}
