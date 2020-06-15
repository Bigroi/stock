package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class AddressForUI {

    @Id
    private final long id;

    @Column(value = "label.address.city", responsivePriority = -3)
    private final String city;

    @Column(value = "label.address.country", responsivePriority = -2)
    private final String country;

    @Column(value = "label.address.address", responsivePriority = -4)
    private final String address;

    @Column(value = "label.address.default_address", responsivePriority = -1)
    private String defaultAddress;

    @Column(value = "label.address.edit", responsivePriority = -5)
    @Edit(remove = "/address/json/Delete", edit = "getAddressDialogParams")
    private final String edit;

    private final double latitude;
    private final double longitude;

    public AddressForUI(CompanyAddress address, String defaultAddress) {
        this(address);
        this.defaultAddress = defaultAddress;
    }

    public AddressForUI(CompanyAddress address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.address = address.getAddress();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.edit = "YYN";
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
