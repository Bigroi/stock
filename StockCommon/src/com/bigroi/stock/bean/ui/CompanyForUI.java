package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class CompanyForUI {

	@Id
	private long id;
	
	@Column(value = "label.companies.name", responsivePriority=-3)
	private String name;
	
	@Column(value = "label.companies.phone", responsivePriority=-1)
	private String phone;
	
	@Column(value = "label.companies.reg_number", responsivePriority=-2)
	private String regNumber;
	
	@Column(value = "label.companies.status", responsivePriority=-4)
	@Status(activate="/company/json/admin/ChangeStatus.spr", 
			deactivate="/company/json/admin/ChangeStatus.spr")
	private CompanyStatus status;
	
	public CompanyForUI(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.phone = company.getPhone();
		this.regNumber = company.getRegNumber();
		this.status = company.getStatus();
	}
	
}
