package com.bigroi.stock.bean.db;

public class Blacklist {

	private long id;
	private long tenderId;
	private long lotId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTenderId() {
		return tenderId;
	}
	public void setTenderId(long tenderId) {
		this.tenderId = tenderId;
	}
	public long getLotId() {
		return lotId;
	}
	public void setLotId(long lotId) {
		this.lotId = lotId;
	}
	@Override
	public String toString() {
		return "Blacklist [id=" + id + ", tenderId=" + tenderId + ", lotId=" + lotId + "]";
	}
}
