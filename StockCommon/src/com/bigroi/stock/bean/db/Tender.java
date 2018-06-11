package com.bigroi.stock.bean.db;

import java.util.Date;

import com.bigroi.stock.bean.common.Bid;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.util.DateUtil;

public class Tender implements Bid{

	private long id;
	
	private Product product;
	
	private long productId = -1l;
	
	private BidStatus status;
	
	private double maxPrice;
	
	private int maxVolume;
	
	private Date exparationDate = new Date();
	
	private Date creationDate = new Date();
	
	private long companyId;

	private String description;
	
	private long addressId;
	
	private Address address;
	
	private int minVolume;
	
	public boolean isExpired() {
		return DateUtil.beforToday(exparationDate);
	}
	
	public Address getAddress() {
		return address;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}
	
	@Override
	public int getMaxVolume() {
		return maxVolume;
	}

	@Override
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}

	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tender && ((Tender)obj).getId() == this.getId();
	}

	@Override
	public double getPrice() {
		return maxPrice;
	}
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public Date getExparationDate() {
		return exparationDate;
	}

	public void setExparationDate(Date exparationDate) {
		this.exparationDate = exparationDate;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	
	public long getAddressId() {
		return addressId;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
