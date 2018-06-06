package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Edit;
import com.bigroi.stock.json.Id;

public class AddressForUI {

	@Id
	private long id;

	@Column(value = "label.address.city")
	private String city;

	@Column(value = "label.address.country")
	private String country;

	@Column(value = "label.address.address")
	private String address;
	
	@Column("")
	@Edit(details="", remove="", edit="/account/FormAddress.spr")
	private String edit = "YNN";
	
	private double latitude;
	private double longitude;

	public AddressForUI(Address address) {
		this.id = address.getId();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.address = address.getAddress();
		this.latitude = address.getLatitude();
		this.longitude = address.getLongitude();
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
