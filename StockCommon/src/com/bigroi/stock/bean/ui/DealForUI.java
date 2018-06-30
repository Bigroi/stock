package com.bigroi.stock.bean.ui;

import java.util.Date;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class DealForUI {

	@Id
	private long id;
	
	@Column(value = "label.deal.productName", responsivePriority=-3)
	private String productName;
	
	@Column(value = "label.deal.time", allowSorting = true, responsivePriority=-1)
	private Date time;
	
	@Column(value = "label.deal.status", responsivePriority=-2)
	private String status;

	@Column(value = "edit", responsivePriority=-4)
	@Edit(details="/deal/Form.spr", remove="", edit="")
	private String edit = "NNY";
	
	private final Address sellerAddrress;
	private final Address buyerAddrress;
	private final Address partnerAddress;
	private final String sellerFoto;
	private final double price;
	private final int volume;
	private final String partnerDescription;
	private DealStatus statusCode;
	
	public DealForUI(Deal deal, long companyId){
		this.id = deal.getId();
		productName = deal.getProduct().getName();
		this.time = deal.getTime();
		this.statusCode = getDealStatus(deal, companyId);
		this.status = this.statusCode.toString();
		this.sellerAddrress = deal.getSellerAddress();
		this.buyerAddrress = deal.getBuyerAddress();
		if (deal.getSellerAddress().getCompanyId() == companyId){
			this.partnerAddress = deal.getBuyerAddress();
			this.partnerDescription = deal.getBuyerDescription();
		} else {
			this.partnerAddress = deal.getSellerAddress();
			this.partnerDescription = deal.getSellerDescription();
		}
		this.sellerFoto = deal.getSellerFoto();
		this.price = deal.getPrice();
		this.volume = deal.getVolume();
	}
	
	private DealStatus getDealStatus(Deal deal, long companyId){
		PartnerChoice companyChoice = deal.getBuyerAddress().getCompanyId() == companyId ? 
				deal.getBuyerChoice() : deal.getSellerChoice();
		DealStatus status = DealStatus.calculateStatus(deal.getBuyerChoice(), deal.getSellerChoice());
		if (status == DealStatus.ON_APPROVE && companyChoice != PartnerChoice.ON_APPROVE){
			return DealStatus.ON_PARTNER_APPROVE;
		} else {
			return status;
		}
	}
	
	public Address getBuyerAddrress() {
		return buyerAddrress;
	}
	
	public Address getPartnerAddress() {
		return partnerAddress;
	}
	
	public Address getSellerAddrress() {
		return sellerAddrress;
	}
	
	public String getSellerFoto() {
		return sellerFoto;
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
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
}
