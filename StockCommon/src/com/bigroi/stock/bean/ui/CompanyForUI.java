package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class CompanyForUI {

	@Id
	private final long id;
	
	@Column(value = "label.companies.name", responsivePriority=-3)
	private final String name;
	
	@Column(value = "label.companies.phone", responsivePriority=-1)
	private final String phone;
	
	@Column(value = "label.companies.reg_number", responsivePriority=-2)
	private final String regNumber;
	
	@Column(value = "label.companies.status", responsivePriority=-4)
	@Status(activate="/company/json/admin/ChangeStatus", 
			deactivate="/company/json/admin/ChangeStatus")
	private final CompanyStatus status;
	
	public CompanyForUI(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.phone = company.getPhone();
		this.regNumber = company.getRegNumber();
		this.status = company.getStatus();
	}
	
}
