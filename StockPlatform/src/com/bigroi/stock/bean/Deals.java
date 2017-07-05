package com.bigroi.stock.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deals {
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

	private long id;
	private long lotId;
	private long tenderId;
	private Date dealsTime = new Date();
	
	
	public String getDateStr(){
		return FORMATTER.format(dealsTime);
	}
	
	public void setDateStr(String dateStr) throws ParseException{
		dealsTime = FORMATTER.parse(dateStr);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLotId() {
		return lotId;
	}

	public void setLotId(long lotId) {
		this.lotId = lotId;
	}

	public long getTenderId() {
		return tenderId;
	}

	public void setTenderId(long tenderId) {
		this.tenderId = tenderId;
	}

	public Date getDealsTime() {
		return dealsTime;
	}

	public void setDealsTime(Date dealsTime) {
		this.dealsTime = dealsTime;
	}

	@Override
	public String toString() {
		return "Deals [id=" + id + ", lotId=" + lotId + ", tenderId=" + tenderId + ", dealsTime=" + dealsTime + "]";
	}
}
