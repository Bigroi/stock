package com.bigroi.stock.bean.ui;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.json.Column;
	
public class PropositionForUI {
	
	@Column(value = "label.companies.name", responsivePriority = -4)
	private final String name;
	
	@Column(value = "label.companies.phone", responsivePriority = -3)
	private final String phone;
	
	@Column(value = "label.transport.volume", responsivePriority = -2)
	private final int volume;
	
	@Column(value = "label.transport.price", responsivePriority = -1)
	private final double price;
	
	public PropositionForUI(Proposition proposition){
		this.name = proposition.getCompanyName();
		this.phone = proposition.getCompanyPhone();
		this.volume = proposition.getDealVolume();
		this.price = proposition.getDealPrice();
	}
}
