package com.bigroi.stock.bean.db;

import java.io.Serializable;

import com.bigroi.stock.bean.common.CompanyStatus;

public class Company implements Serializable{

	private static final long serialVersionUID = -7950361285384093767L;

	private long id;
	
	private String name;
	
	private String phone;
	
	private String regNumber;
	
	private Address address;
	
	private String email;
	
	private String type;
	
	private CompanyStatus status;
	
	private long addressId;
	
	public long getAddressId() {
		return addressId;
	}
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
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

	public CompanyStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyStatus status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", phone=" + phone + ", regNumber=" + regNumber + ", address="
				+ address + ", email=" + email + ", type=" + type + ", status=" + status + ", addressId=" + addressId
				+ "]";
	}

}
