package com.bigroi.stock.bean.db;

import java.util.Objects;

public class Tender extends Bid{

	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Tender && ((Tender)obj).getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
