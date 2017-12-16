package com.bigroi.stock.bean;

import java.util.Date;

import com.bigroi.stock.bean.common.DealStatus;

public class Deal {
	
	private long id;
	private Long lotId;
	private Long tenderId;
	private long sellerId;
	private long customerId;
	private double price;
	private int volume;
	private Date time;
	private String customerApproved;
	private String sellerApproved;
	
	public Deal(Lot lot, Tender tender, int volume) {
		this.lotId = lot.getId();
		this.tenderId = tender.getId();
		this.sellerId = lot.getSellerId();
		this.customerId = tender.getCustomerId();
		this.price = (lot.getMinPrice() + tender.getMaxPrice()) / 2.;
		this.volume = volume;
		this.time = new Date();
	}
	
	public Deal() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getLotId() {
		return lotId;
	}
	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}
	public Long getTenderId() {
		return tenderId;
	}
	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getCustomerApproved() {
		return customerApproved;
	}
	public void setCustomerApproved(String customerApproved) {
		this.customerApproved = customerApproved;
	}
	public String getSellerApproved() {
		return sellerApproved;
	}
	public void setSellerApproved(String sellerApproved) {
		this.sellerApproved = sellerApproved;
	}
	public DealStatus getStatus(){
		if (sellerApproved != null && sellerApproved.equals("N")){
			return DealStatus.REJECTED;
		} else if (customerApproved != null && customerApproved.equals("N")){
			return DealStatus.REJECTED;
		} else if (sellerApproved == null || customerApproved == null){
			return DealStatus.ON_APPROVE;
		} else {
			return DealStatus.APPROVED;
		}
	}
	
	public Boolean getCustomerApprovedBool(){
		if (customerApproved == null){
			return null;
		} else {
			return customerApproved.equalsIgnoreCase("Y");
		}
	}
	
	public void setCustomerApprovedBool(Boolean customerApproved){
		if (customerApproved == null){
			this.customerApproved = null;
		} else {
			this.customerApproved = customerApproved?"Y":"N";
		}
	}
	
	public Boolean getSellerApprovedBool(){
		if (sellerApproved == null){
			return null;
		} else {
			return sellerApproved.equalsIgnoreCase("Y");
		}
	}
	
	public void setSellerApprovedBool(Boolean sellerApproved){
		if (customerApproved == null){
			this.sellerApproved = null;
		} else {
			this.sellerApproved = sellerApproved?"Y":"N";
		}
	}
}
