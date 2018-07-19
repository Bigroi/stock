package com.bigroi.stock.bean.db;

import java.util.Date;

import com.bigroi.stock.util.Generator;

public class TempKey {
	
	private  long id;
	private String generatedKey;
	private Date expirationDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGeneratedKey() {
		return generatedKey;
	}
	public void setGeneratedKey(String generatedKey) {
		this.generatedKey = generatedKey;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public void generateKey(){
		generatedKey = Generator.generateLinkKey(30);
		expirationDate = new Date();
	}
	@Override
	public String toString() {
		return "GeneratedKeys [id=" + id + ", generatedKey=" + generatedKey + ", expirationDate=" + expirationDate
				+ "]";
	}
	
	
}
