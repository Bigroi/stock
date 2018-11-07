package com.bigroi.stock.bean.ui;

import java.util.Date;
import java.util.List;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;
import com.google.common.collect.ImmutableList;

public class DealForUI {

	@Id
	private long id;
	
	@Column(value = "label.deal.productName", responsivePriority=-4)
	private final String productName;
	
	@Column(value = "label.deal.categoryName", responsivePriority=-3)
	private final String categoryName;
	
	@Column(value = "label.deal.time", allowSorting = true, responsivePriority=-1)
	private final Date time;
	
	@Column(value = "label.deal.status", responsivePriority=-2)
	private String status;

	@Column(value = "label.deal.edit", responsivePriority=-4)
	@Edit(details="/deal/Form.spr", remove="", edit="")
	private final String edit;
	
	private final double sellerLatitude;
	private final double sellerLongitude;
	private final double buyerLatitude;
	private final double buyerLongitude;
	private final String partnerAddress;
	private final String partnerPhone;
	private final String partnerRegNumber;
	private final String partnerCompanyName;
	private final String foto;
	private final double price;
	private final int volume;
	private final String partnerDescription;
	private final DealStatus statusCode;
	private boolean star_5 = false;
	private boolean star_4 = false;
	private boolean star_3 = false;
	private boolean star_2 = false;
	private boolean star_1 = false;
	private final String packaging;
	private final String processing;
	private final List<String> fieldsToRemove;
	
	public DealForUI(Deal deal, long companyId){
		this(deal, companyId, 0);
	}
	
	public DealForUI(Deal deal, long companyId, int partnerMark){
		this.id = deal.getId();
		this.productName = deal.getProductName();
		this.categoryName = deal.getCategoryName();
		setStar(partnerMark);
		this.time = deal.getTime();
		this.statusCode = getDealStatus(deal, companyId);
		this.status = this.statusCode.toString();
		this.sellerLatitude = deal.getSellerLatitude();
		this.sellerLongitude = deal.getSellerLongitude();
		this.buyerLatitude = deal.getBuyerLatitude();
		this.buyerLongitude = deal.getBuyerLongitude();
		if (deal.getBuyerCompanyId() == companyId){
			this.partnerAddress = deal.getBuyerCountry() + ", " + 
									deal.getBuyerCity() + ", " + 
									deal.getBuyerAddress();
			this.partnerDescription = deal.getBuyerDescription();
			this.packaging = deal.getBuyerPackaging();
			this.processing = deal.getBuyerProcessing();
			this.partnerCompanyName = deal.getBuyerCompanyName();
			this.fieldsToRemove = ImmutableList.of("foto");
			this.partnerPhone = deal.getBuyerPhone();
			this.partnerRegNumber = deal.getBuyerRegNumber();
			this.foto = null;
		} else {
			this.partnerAddress = deal.getSellerCountry() + ", " + 
									deal.getSellerCity() + ", " + 
									deal.getSellerAddress();
			this.partnerDescription = deal.getSellerDescription();
			this.partnerPhone = deal.getSellerPhone();
			this.partnerRegNumber = deal.getSellerRegNumber();
			this.partnerCompanyName = deal.getSellerCompanyName();
			this.foto = deal.getSellerFoto();
			this.fieldsToRemove = ImmutableList.of("processing", "packaging");
			this.packaging = null;
			this.processing = null;
		}
		this.price = deal.getPrice();
		this.volume = deal.getVolume();
		this.edit = "NNY";
	}
	
	public String getPackaging() {
		return packaging;
	}
	
	public String getProcessing() {
		return processing;
	}
	
	public List<String> getFieldsToRemove() {
		return fieldsToRemove;
	}
	
	private void setStar(int partnerMark) {
		switch (partnerMark) {
		case 1:
			star_1 = true;
			break;
		case 2:
			star_2 = true;
			break;
		case 3:
			star_3 = true;
			break;
		case 4:
			star_4 = true;
			break;
		case 5:
			star_5 = true;
			break;
		default:
			break;
		}
	}

	private DealStatus getDealStatus(Deal deal, long companyId){
		PartnerChoice companyChoice = deal.getBuyerCompanyId() == companyId ? 
				deal.getBuyerChoice() : deal.getSellerChoice();
		DealStatus dealStatus = DealStatus.calculateStatus(deal.getBuyerChoice(), deal.getSellerChoice());
		if (dealStatus == DealStatus.ON_APPROVE && companyChoice != PartnerChoice.ON_APPROVE){
			return DealStatus.ON_PARTNER_APPROVE;
		} else {
			return dealStatus;
		}
	}
	
	public String getPartnerAddress() {
		return partnerAddress;
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
	
	public String getFoto() {
		return foto;
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

	public boolean isStar_5() {
		return star_5;
	}

	public boolean isStar_4() {
		return star_4;
	}

	public boolean isStar_3() {
		return star_3;
	}

	public boolean isStar_2() {
		return star_2;
	}

	public boolean isStar_1() {
		return star_1;
	}

	public double getSellerLatitude() {
		return sellerLatitude;
	}

	public double getSellerLongitude() {
		return sellerLongitude;
	}

	public double getBuyerLatitude() {
		return buyerLatitude;
	}

	public double getBuyerLongitude() {
		return buyerLongitude;
	}
	
	public String getPartnerPhone() {
		return partnerPhone;
	}
	
	public String getPartnerRegNumber() {
		return partnerRegNumber;
	}
	
	public String getPartnerCompanyName() {
		return partnerCompanyName;
	}
	
}
