package com.bigroi.stock.bean.db;

public class Label {

	private long id;
	
	private String category;
	
	private String name;
	
	private String enUs;
	
	private String plPl;
	
	private String ruBy;
	
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

	public String getPlPl() {
		return plPl;
	}

	public void setPlPl(String plPl) {
		this.plPl = plPl;
	}

	public String getRuBy() {
		return ruBy;
	}

	public void setRuBy(String ruBy) {
		this.ruBy = ruBy;
	}

	public String getCurrentLanguage() {
		if (currentLanguage == null){
			return getAnonimLabel(category, name);
		} else {
			return currentLanguage;
		}
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	
	public String generateKey(){
		return new StringBuilder("label.")
				.append(category).append(".")
				.append(name).toString();
	}
	
	
	public static String getAnonimLabel(String category, String name){
		return "LABEL:" + category.toUpperCase() + ":" + name.toLowerCase();
	}
}
