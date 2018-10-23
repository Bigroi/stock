package com.bigroi.stock.bean.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class TradeBid {

	private long id;
	
	private long productId;
	
	private List<TradeBid> posiblePartners = new ArrayList<>();
	
	private double price;
	
	private int maxVolume;
	
	private Date creationDate;
	
	private double delivaryPrice;
	
	private double latitude;
	
	private double longitude;
	
	private int distance;
	
	private int minVolume;
	
	private String language;
	
	private String email;
	
	private long addressId;
	
	public long getAddressId() {
		return addressId;
	}
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public int getMinVolume() {
		return minVolume;
	}
	
	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	
	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getDelivaryPrice() {
		return delivaryPrice;
	}
	
	public void setDelivaryPrice(double delivaryPrice) {
		this.delivaryPrice = delivaryPrice;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public int getMaxVolume() {
		return maxVolume;
	}
	
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public List<TradeBid> getPosiblePartners() {
		return posiblePartners;
	}

	public void addPosiblePartner(TradeBid bid) {
		posiblePartners.add(bid);
	}
	
	public void removeFromPosiblePartners(){
		for (TradeBid partner : getPosiblePartners()){
			partner.getPosiblePartners().remove(this);
		}
		getPosiblePartners().clear();
	}
	
	public TradeBid getBestPartner() {
		return Collections.max(getPosiblePartners(), (o1, o2) -> compareBids(o1, o2, this));
	}
	
	private int compareBids(TradeBid o1, TradeBid o2, TradeBid thisObject) {
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
		for (TradeBid bid : getPosiblePartners()){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	public static double getDistancePrice(TradeBid o1, TradeBid o2){
		return distance(o1, o2) * o1.getDelivaryPrice();
	}
	
	private static double distance(TradeBid o1, TradeBid o2) {

	    final int R = 6371; // Radius of the earth
	    double latDistance = Math.toRadians(o2.getLatitude() - o1.getLatitude());
	    double lonDistance = Math.toRadians(o2.getLongitude() - o1.getLongitude());
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(o1.getLatitude())) 
	            * Math.cos(Math.toRadians(o2.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    return R * c;// convert to km
	}
}
