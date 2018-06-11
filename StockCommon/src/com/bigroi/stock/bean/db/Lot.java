package com.bigroi.stock.bean.db;

import java.util.Date;

import com.bigroi.stock.bean.common.Bid;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.json.DateTimeAdapter;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.util.DateUtil;
import com.google.gson.annotations.JsonAdapter;

public class Lot implements Bid{
	
	@Id
	private long id;
	
	private Product product;
	private long productId = -1l;	
	
	private BidStatus status;
	
	private double minPrice;
	private int maxVolume;
	private Date exparationDate = new Date();
	
	@JsonAdapter(DateTimeAdapter.class)
	private Date creationDate = new Date();
	
	private long companyId;
	private String description;
	private int minVolume;
	private String foto;
	private long addressId;
	private Address address;
	
	public boolean isExpired() {
		return DateUtil.beforToday(exparationDate);
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
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

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
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
	
	@Override
	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Lot && ((Lot)obj).getId() == this.getId();
	}

	@Override
	public double getPrice() {
		return minPrice;
	}
}