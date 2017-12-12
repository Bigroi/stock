package com.bigroi.stock.bean;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;

public class Company {

	@Id
	private long id;
	
	@Column("skock.table.company.name")
	private String name;
	
	@Column("skock.table.company.email")
	private String email;
	
	@Column("skock.table.company.phone")
	private String phone;
	
	@Column("skock.table.company.regNumber")
	private String regNumber;
	
	@Column("skock.table.company.country")
	private String country;
	
	@Column("skock.table.company.city")
	private String city;
	
	@Column("skock.table.company.status")
	private CompanyStatus status;
	
	@Column("skock.table.company.longitude")
	private double longitude = 26.001813399999946;
	
	@Column("skock.table.company.latitude")
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", regNumber="
				+ regNumber + ", country=" + country + ", city=" + city + ", status=" + status + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

  

}
