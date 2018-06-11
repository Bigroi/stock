package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.List;

import com.bigroi.stock.bean.db.Lot;

public class TradeLot extends Lot implements TradeBid{

	private List<TradeTender> posiblePartners = new ArrayList<>();
	
	@Override
	public List<? extends TradeBid> getPosiblePartners() {
		return posiblePartners;
	}

	@Override
	public void addPosiblePartner(TradeBid bid) {
		posiblePartners.add((TradeTender)bid);
	}

}
