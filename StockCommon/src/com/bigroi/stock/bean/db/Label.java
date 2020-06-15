package com.bigroi.stock.bean.db;

import com.bigroi.stock.util.LabelUtil;

public class Label {

    private long id;
    private String category;
    private String name;
    private String enUs;
    private String pl;
    private String ruRu;
    private String currentLanguage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnUs() {
        return enUs;
    }

    public void setEnUs(String enUs) {
        this.enUs = enUs;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getRuRu() {
        return ruRu;
    }

    public void setRuRu(String ruRu) {
        this.ruRu = ruRu;
    }

    public String getCurrentLanguage() {
        if (currentLanguage == null) {
            return LabelUtil.getLabelUndefinedValue(category, name);
        } else {
            return currentLanguage;
        }
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public String generateKey() {
        return new StringBuilder("label.")
                .append(category).append(".")
                .append(name).toString();
    }

}
