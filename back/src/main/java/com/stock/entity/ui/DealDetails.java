package com.stock.entity.ui;

import com.stock.entity.DealStatus;

import java.util.Date;
import java.util.UUID;

public class DealDetails  extends Deal{

    private final double price;
    private final int volume;
    private final String partnerName;
    private final double partnerMark;
    private final String partnerComment;
    private final String packaging;
    private final String processing;
    private final String phone;
    private final String regNumber;
    private final String photo;
    private final String address;
    private final double latitude;
    private final double longitude;
    private final double fromLatitude;
    private final double fromLongitude;

    public DealDetails(
            UUID id,
            String productName,
            String categoryName,
            Date creationDate,
            DealStatus status,
            double price,
            int volume,
            String partnerName,
            double partnerMark,
            String partnerComment,
            String packaging,
            String processing,
            String phone,
            String regNumber,
            String photo,
            String address,
            double latitude,
            double longitude,
            double fromLatitude,
            double fromLongitude
    ) {
        super(id, productName, categoryName, creationDate, status);
        this.price = price;
        this.volume = volume;
        this.partnerName = partnerName;
        this.partnerMark = partnerMark;
        this.partnerComment = partnerComment;
        this.packaging = packaging;
        this.processing = processing;
        this.phone = phone;
        this.regNumber = regNumber;
        this.photo = photo;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public double getPartnerMark() {
        return partnerMark;
    }

    public String getPartnerComment() {
        return partnerComment;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getProcessing() {
        return processing;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getFromLatitude() {
        return fromLatitude;
    }

    public double getFromLongitude() {
        return fromLongitude;
    }
}
