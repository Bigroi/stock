package com.bigroi.stock.bean;

import java.util.Date;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class Deal {
	
	@Id
	private long id;
	private Long lotId;
	private Long tenderId;
	private long sellerId;
	private long customerId;
	private double price;
	private int volume;
	@Column("lable.deal.time")
	private Date time;
	private String customerApproved;
	private String sellerApproved;
	private long productId;
	
	@Column("lable.deal.productName")
	private String productName;
	
	@Column("lable.deal.partnerAddress")
	private String partnerAddress;
	
	@Column("lable.deal.partnerComment")
	private String partnerComment;
	
	private String partnerPhone;
	
	private String partnerRegNumber;
	
	private String partnerName;
	
	private double latitude;
	
	private double longitude;
	
	@Column("lable.deal.status")
	private DealStatus status;
	
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
	
	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
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
		if (sellerApproved == null){
			this.sellerApproved = null;
		} else {
			this.sellerApproved = sellerApproved?"Y":"N";
		}
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPartnerAddress() {
		return partnerAddress;
	}

	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}

	public String getPartnerComment() {
		return partnerComment;
	}

	public void setPartnerComment(String partnerComment) {
		this.partnerComment = partnerComment;
	}

	public void setStatus(DealStatus status) {
		this.status = status;
	}

	public DealStatus getStatus() {
		return status;
	}

	public String getPartnerPhone() {
		return partnerPhone;
	}

	public void setPartnerPhone(String partnerPhone) {
		this.partnerPhone = partnerPhone;
	}

	public String getPartnerRegNumber() {
		return partnerRegNumber;
	}

	public void setPartnerRegNumber(String partnerRegNumber) {
		this.partnerRegNumber = partnerRegNumber;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double partnerLatitude) {
		this.latitude = partnerLatitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double partnerLongitude) {
		this.longitude = partnerLongitude;
	}

}
