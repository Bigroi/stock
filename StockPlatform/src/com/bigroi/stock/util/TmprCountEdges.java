package com.bigroi.stock.util;



public class TmprCountEdges {
	
	private long tenderId;
	private long lotId;
	private long productId;
	private double maxPrice;
	private double minPrice;
	
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
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	@Override
	public String toString() {
		return "TmprCountEdges [tenderId=" + tenderId + ", lotId=" + lotId + ", productId=" + productId + ", maxPrice="
				+ maxPrice + ", minPrice=" + minPrice + "]";
	}
	
	
	
	
}
