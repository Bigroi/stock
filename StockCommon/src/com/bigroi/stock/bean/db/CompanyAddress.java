package com.bigroi.stock.bean.db;

import java.io.Serializable;

public class CompanyAddress implements Serializable{

	private static final long serialVersionUID = 8556087465990620455L;

	private long id;
	
	private String city;
	
	private String country;
	
	private String address;
	
	private double latitude;
	
	private double longitude;
	
	private long companyId;
	
	private Company company;

	public long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", country=" + country + ", address=" + address + ", latitude="
				+ latitude + ", longitude=" + longitude + ", companyId=" + companyId + ", company=" + company + "]";
	}
	
}
