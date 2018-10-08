package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class DealForUI {

	@Id
	private long id;
	
	@Column(value = "label.deal.productName", responsivePriority=-3)
	private final String productName;
	
	@Column(value = "label.deal.time", allowSorting = true, responsivePriority=-1)
	private final Date time;
	
	@Column(value = "label.deal.status", responsivePriority=-2)
	private String status;

	@Column(value = "label.deal.edit", responsivePriority=-4)
	@Edit(details="/deal/Form.spr", remove="", edit="")
	private final String edit;
	
	private final CompanyAddress sellerAddrress;
	private final CompanyAddress buyerAddrress;
	private final CompanyAddress partnerAddress;
	private final String sellerFoto;
	private final double price;
	private final int volume;
	private final String partnerDescription;
	private final DealStatus statusCode;
	private final int partnerMark;
	
	public DealForUI(Deal deal, long companyId){
		this(deal, companyId, 0);
	}
	
	public DealForUI(Deal deal, long companyId, int partnerMark){
		this.id = deal.getId();
		this.productName = deal.getProduct().getName();
		this.partnerMark = partnerMark;
		this.time = deal.getTime();
		this.statusCode = getDealStatus(deal, companyId);
		this.status = this.statusCode.toString();
		this.sellerAddrress = deal.getSellerAddress();
		this.buyerAddrress = deal.getBuyerAddress();
		if (deal.getSellerAddress().getCompanyId() == companyId){
			this.partnerAddress = deal.getBuyerAddress();
			this.partnerDescription = deal.getBuyerDescription();
			this.sellerFoto = null;
		} else {
			this.partnerAddress = deal.getSellerAddress();
			this.partnerDescription = deal.getSellerDescription();
			this.sellerFoto = deal.getSellerFoto();
		}
		this.partnerAddress.setAddress(
				this.partnerAddress.getCountry() + ", " +
				this.partnerAddress.getCity() + ", " +
				this.partnerAddress.getAddress()
				);
		this.price = deal.getPrice();
		this.volume = deal.getVolume();
		this.edit = "NNY";
	}
	
	private DealStatus getDealStatus(Deal deal, long companyId){
		PartnerChoice companyChoice = deal.getBuyerAddress().getCompanyId() == companyId ? 
				deal.getBuyerChoice() : deal.getSellerChoice();
		DealStatus dealStatus = DealStatus.calculateStatus(deal.getBuyerChoice(), deal.getSellerChoice());
		if (dealStatus == DealStatus.ON_APPROVE && companyChoice != PartnerChoice.ON_APPROVE){
			return DealStatus.ON_PARTNER_APPROVE;
		} else {
			return dealStatus;
		}
	}
	
	public int getPartnerMark() {
		return partnerMark;
	}
	
	public CompanyAddress getBuyerAddrress() {
		return buyerAddrress;
	}
	
	public CompanyAddress getPartnerAddress() {
		return partnerAddress;
	}
	
	public CompanyAddress getSellerAddrress() {
		return sellerAddrress;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public String getPartnerDescription() {
		return partnerDescription;
	}
	
	public String getSellerFoto() {
		return sellerFoto;
	}

	public long getId() {
		return id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
