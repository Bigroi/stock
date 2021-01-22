package com.stock.entity.business;

import java.io.Serializable;
import java.util.UUID;

public class AddressRecord implements Serializable {

    private static final long serialVersionUID = 8556087465990620455L;

    private UUID id;
    private String city;
    private String country;
    private String address;
    private double latitude;
    private double longitude;
    private UUID companyId;
    private boolean primaryAddress;

    public AddressRecord() {

    }

    public AddressRecord(
            UUID id,
            String city,
            String country,
            String address,
            double latitude,
            double longitude,
            UUID companyId,
            boolean primaryAddress
    ) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.companyId = companyId;
        this.primaryAddress = primaryAddress;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public boolean isPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(boolean primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
}
