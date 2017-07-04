package com.bigroi.stock.bean;

import java.sql.Time;

public class Deals {
	
	
	
	private long id;
	private long lotId;
	private long tenderId;
	private Time dealsTime;
	
	
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
	public Time getDealsTime() {
		return dealsTime;
	}
	public void setDealsTime(Time dealsTime) {
		this.dealsTime = dealsTime;
	}
	
	@Override
	public String toString() {
		return "Deals [id=" + id + ", lotId=" + lotId + ", tenderId=" + tenderId + ", dealsTime=" + dealsTime + "]";
	}
	
}
