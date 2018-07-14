package com.bigroi.stock.bean.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.util.DateUtil;

public abstract class Bid {

private long id;
	
	private Product product;
	private long productId = -1l;	
	
	private BidStatus status;
	
	private double price;
	private int maxVolume;
	private Date exparationDate = DateUtil.shiftMonths(new Date(), 1);
	
	private Date creationDate = new Date();
	
	private long companyId;
	private String description;
	private int minVolume;
	
	private long addressId;
	private CompanyAddress companyAddress;
	
	private List<Bid> posiblePartners = new ArrayList<>();
	
	public boolean isExpired() {
		return DateUtil.isBeforToday(exparationDate);
	}
	
	public List<Bid> getPosiblePartners() {
		return posiblePartners;
	}

	public void addPosiblePartner(Bid bid) {
		posiblePartners.add(bid);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public BidStatus getStatus() {
		return status;
	}
	public void setStatus(BidStatus status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getMaxVolume() {
		return maxVolume;
	}
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	public Date getExparationDate() {
		return exparationDate;
	}
	public void setExparationDate(Date exparationDate) {
		this.exparationDate = exparationDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinVolume() {
		return minVolume;
	}
	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public CompanyAddress getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(CompanyAddress companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public void removeFromPosiblePartners(){
		for (Bid partner : getPosiblePartners()){
			partner.getPosiblePartners().remove(this);
		}
		getPosiblePartners().clear();
	}
	
	public Bid getBestPartner() {
		return Collections.max(getPosiblePartners(), (o1, o2) -> compareBids(o1, o2, this));
	}
	
	private int compareBids(Bid o1, Bid o2, Bid thisObject) {
		int o2Volume = Math.min(o2.getMaxVolume(), thisObject.getMaxVolume());
		double o2Price = (o2.getPrice() * o2Volume + getDistancePrice(o2, thisObject)) / o2Volume;
		
		int o1Volume = Math.min(o1.getMaxVolume(), thisObject.getMaxVolume());
		double o1Price = (o1.getPrice() * o1Volume + getDistancePrice(o1, thisObject)) / o1Volume;
		
		int result = (int)((o1Price - o2Price) * 10000);
		if (result == 0){
			return (int)(o1.getCreationDate().getTime() - o2.getCreationDate().getTime());
		} else {
			return result;
		}
	}
	
	public int getTotalPosibleVolume(){
		int result = 0;
		for (Bid bid : getPosiblePartners()){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	public static double getDistancePrice(Bid o1, Bid o2){
		return distance(o1, o2) * o1.getProduct().getDelivaryPrice();
	}
	
	private static double distance(Bid o1, Bid o2) {

	    final int R = 6371; // Radius of the earth
	    double latDistance = Math.toRadians(o2.getCompanyAddress().getLatitude() - o1.getCompanyAddress().getLatitude());
	    double lonDistance = Math.toRadians(o2.getCompanyAddress().getLongitude() - o1.getCompanyAddress().getLongitude());
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(o1.getCompanyAddress().getLatitude())) 
	            * Math.cos(Math.toRadians(o2.getCompanyAddress().getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return R * c;// convert to km
	}
	
}
