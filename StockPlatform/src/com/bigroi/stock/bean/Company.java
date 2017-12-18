package com.bigroi.stock.bean;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class Company {

	@Id
	private long id;
	
	@Column("lable.company.name")
	private String name;
	
	@Column("lable.company.phone")
	private String phone;
	
	@Column("lable.company.regNumber")
	private String regNumber;
	
	private String country;
	
	private String city;
	
	private String address;
	
	@Column("stock.table.company.status")
	private CompanyStatus status;
	
	private double longitude = 26.001813399999946;
	
	private double latitude = 53.1568911;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", phone=" + phone + ", regNumber=" + regNumber + ", country="
				+ country + ", city=" + city + ", address=" + address + ", status=" + status + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}
 

  

}
