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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DealRecord that = (DealRecord) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (volume != that.volume) return false;
        if (sellerAlert != that.sellerAlert) return false;
        if (buyerAlert != that.buyerAlert) return false;
        if (!id.equals(that.id)) return false;
        if (!lotId.equals(that.lotId)) return false;
        if (!tenderId.equals(that.tenderId)) return false;
        if (!sellerCompanyId.equals(that.sellerCompanyId)) return false;
        if (!buyerCompanyId.equals(that.buyerCompanyId)) return false;
        if (!sellerAddress.equals(that.sellerAddress)) return false;
        if (!buyerAddress.equals(that.buyerAddress)) return false;
        if (!creationDate.equals(that.creationDate)) return false;
        if (sellerChoice != that.sellerChoice) return false;
        if (buyerChoice != that.buyerChoice) return false;
        if (!categoryId.equals(that.categoryId)) return false;
        if (!photo.equals(that.photo)) return false;
        if (!sellerDescription.equals(that.sellerDescription)) return false;
        if (!buyerDescription.equals(that.buyerDescription)) return false;
        if (!processing.equals(that.processing)) return false;
        return packaging.equals(that.packaging);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + lotId.hashCode();
        result = 31 * result + tenderId.hashCode();
        result = 31 * result + sellerCompanyId.hashCode();
        result = 31 * result + buyerCompanyId.hashCode();
        result = 31 * result + sellerAddress.hashCode();
        result = 31 * result + buyerAddress.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + sellerChoice.hashCode();
        result = 31 * result + buyerChoice.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + volume;
        result = 31 * result + categoryId.hashCode();
        result = 31 * result + (photo == null ? 0 : photo.hashCode());
        result = 31 * result + (sellerDescription == null ? 0 : sellerDescription.hashCode());
        result = 31 * result + (buyerDescription == null ? 0 : buyerDescription.hashCode());
        result = 31 * result + (processing == null ? 0 : processing.hashCode());
        result = 31 * result + (packaging == null ? 0 : packaging.hashCode());
        result = 31 * result + (sellerAlert ? 1 : 0);
        result = 31 * result + (buyerAlert ? 1 : 0);
        return result;
    }

    public static class DealAddress {

        private String address;
        private double latitude;
        private double longitude;

        public DealAddress() {

        }

        public DealAddress(String address, double latitude, double longitude) {
            this.address = address;
            this.latitude = latitude;
            this.longitude = longitude;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DealAddress that = (DealAddress) o;

            if (Double.compare(that.latitude, latitude) != 0) return false;
            if (Double.compare(that.longitude, longitude) != 0) return false;
            return address.equals(that.address);
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = address.hashCode();
            temp = Double.doubleToLongBits(latitude);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(longitude);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}
