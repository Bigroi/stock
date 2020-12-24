package com.stock.entity.business;

import com.stock.entity.PartnerChoice;
import org.jdbi.v3.json.Json;

import java.util.Date;
import java.util.UUID;

public class DealRecord {
    
    private UUID id;
    private UUID lotId;
    private UUID tenderId;
    private UUID sellerCompanyId;
    private UUID buyerCompanyId;
    private DealAddress sellerAddress;
    private DealAddress buyerAddress;
    private Date creationDate;
    private PartnerChoice sellerChoice;
    private PartnerChoice buyerChoice;
    private double price;
    private int volume;
    private UUID categoryId;
    private String photo;
    private String sellerDescription;
    private String buyerDescription;
    private String processing;
    private String packaging;
    private boolean sellerAlert;
    private boolean buyerAlert;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLotId() {
        return lotId;
    }

    public void setLotId(UUID lotId) {
        this.lotId = lotId;
    }

    public UUID getTenderId() {
        return tenderId;
    }

    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
    }

    public UUID getSellerCompanyId() {
        return sellerCompanyId;
    }

    public void setSellerCompanyId(UUID sellerCompanyId) {
        this.sellerCompanyId = sellerCompanyId;
    }

    public UUID getBuyerCompanyId() {
        return buyerCompanyId;
    }

    public void setBuyerCompanyId(UUID buyerCompanyId) {
        this.buyerCompanyId = buyerCompanyId;
    }

    @Json
    public DealAddress getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(DealAddress sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    @Json
    public DealAddress getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(DealAddress buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public PartnerChoice getSellerChoice() {
        return sellerChoice;
    }

    public void setSellerChoice(PartnerChoice sellerChoice) {
        this.sellerChoice = sellerChoice;
    }

    public PartnerChoice getBuyerChoice() {
        return buyerChoice;
    }

    public void setBuyerChoice(PartnerChoice buyerChoice) {
        this.buyerChoice = buyerChoice;
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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }

    public String getBuyerDescription() {
        return buyerDescription;
    }

    public void setBuyerDescription(String buyerDescription) {
        this.buyerDescription = buyerDescription;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public boolean isSellerAlert() {
        return sellerAlert;
    }

    public void setSellerAlert(boolean sellerAlert) {
        this.sellerAlert = sellerAlert;
    }

    public boolean isBuyerAlert() {
        return buyerAlert;
    }

    public void setBuyerAlert(boolean buyerAlert) {
        this.buyerAlert = buyerAlert;
    }

    public static class DealAddress {

        private String address;
        private double latitude;
        private double longitude;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
    }
}
