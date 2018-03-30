package com.bigroi.stock.bean;

import java.util.Date;

import com.bigroi.stock.util.Generator;

public class GeneratedKey {
	
	private  long id;
	private String generatedKey = Generator.generateLinkKey(50);
	private Date expirationDate = new Date();
	
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
	
	@Override
	public String toString() {
		return "GeneratedKeys [id=" + id + ", generatedKey=" + generatedKey + ", expirationDate=" + expirationDate
				+ "]";
	}
	
	
}
