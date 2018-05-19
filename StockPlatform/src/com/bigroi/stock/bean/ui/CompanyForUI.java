package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.json.Column;
import com.bigroi.stock.json.FilterMethod;
import com.bigroi.stock.json.Id;
import com.bigroi.stock.json.Status;

public class CompanyForUI {

	@Id
	private long id;
	
	@Column(value = "label.account.name", filterMethod = FilterMethod.TEXT)
	private String name;
	
	@Column(value = "label.account.phone", filterMethod = FilterMethod.TEXT)
	private String phone;
	
	@Column(value = "label.account.reg_number", filterMethod = FilterMethod.TEXT)
	private String regNumber;
	
	@Column(value = "label.account.status", filterMethod = FilterMethod.SELECT)
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
