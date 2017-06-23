package com.bigroi.stock.bean;

public class Blacklist {

	private long id;
	private  long appId;
	private long lotId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	@Override
	public String toString() {
		return "Blacklist [id=" + id + ", appId=" + appId + ", lotId=" + lotId + "]";
	}
	
	
}
