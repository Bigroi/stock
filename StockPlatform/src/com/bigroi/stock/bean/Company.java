package com.bigroi.stock.bean;

import java.util.ArrayList;
import java.util.List;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.FilterMethod;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class Company {

	@Id
	private long id;
	
	@Column(value = "label.account.name", filterMethod = FilterMethod.TEXT)
	private String name;
	
	@Column(value = "label.account.phone", filterMethod = FilterMethod.TEXT)
	private String phone;
	
	@Column(value = "label.account.reg_number", filterMethod = FilterMethod.TEXT)
	private String regNumber;
	
	private String country;
	
	private String city;
	
	private String address;
	
	@Column(value = "label.account.status", filterMethod = FilterMethod.SELECT)
	@Status(activate="/company/json/admin/ChangeStatus.spr", 
			deactivate="/company/json/admin/ChangeStatus.spr")
	private CompanyStatus status;
	
	private double longitude;
	
	private double latitude;
	
	private List<String> emails = new ArrayList<>();

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
	
	public List<String> getEmails() {
		return emails;
	}
	
	public void addEmail(String email){
		emails.add(email);
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", phone=" + phone + ", regNumber=" + regNumber + ", country="
				+ country + ", city=" + city + ", address=" + address + ", status=" + status + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

	public String getAllEmails() {
		StringBuilder builder = new StringBuilder();
		for (String email : emails){
			if (builder.length() > 0){
				builder.append(",");
			}
			builder.append(email);
		}
		return builder.toString();
	}

}
