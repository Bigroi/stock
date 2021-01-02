package com.stock.entity.business;

import com.stock.entity.BidStatus;

import java.util.Date;
import java.util.UUID;

public class LotRecord {

    private UUID id;
    private String description;
    private double price;
    private int minVolume;
    private int maxVolume;
    private UUID companyId;
    private BidStatus status;
    private Date creationDate;
    private Date expirationDate;
    private UUID addressId;
    private int distance;
    private UUID categoryId;
    private boolean alert;
    private String photo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(int minVolume) {
        this.minVolume = minVolume;
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isExpired() {
        return expirationDate.before(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotRecord lotRecord = (LotRecord) o;

        if (Double.compare(lotRecord.price, price) != 0) return false;
        if (minVolume != lotRecord.minVolume) return false;
        if (maxVolume != lotRecord.maxVolume) return false;
        if (distance != lotRecord.distance) return false;
        if (alert != lotRecord.alert) return false;
        if (!id.equals(lotRecord.id)) return false;
        if (description != null ? !description.equals(lotRecord.description) : lotRecord.description != null)
            return false;
        if (!companyId.equals(lotRecord.companyId)) return false;
        if (status != lotRecord.status) return false;
        if (!creationDate.equals(lotRecord.creationDate)) return false;
        if (!expirationDate.equals(lotRecord.expirationDate)) return false;
        if (!addressId.equals(lotRecord.addressId)) return false;
        if (!categoryId.equals(lotRecord.categoryId)) return false;
        return photo != null ? photo.equals(lotRecord.photo) : lotRecord.photo == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + minVolume;
        result = 31 * result + maxVolume;
        result = 31 * result + companyId.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + expirationDate.hashCode();
        result = 31 * result + addressId.hashCode();
        result = 31 * result + distance;
        result = 31 * result + categoryId.hashCode();
        result = 31 * result + (alert ? 1 : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }
}
