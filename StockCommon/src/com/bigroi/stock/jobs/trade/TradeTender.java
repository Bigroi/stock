package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.List;

import com.bigroi.stock.bean.db.Tender;

public class TradeTender extends Tender implements TradeBid{

	private List<TradeLot> posiblePartners = new ArrayList<>();

	@Override
	public List<? extends TradeBid> getPosiblePartners() {
		return posiblePartners;
	}

	@Override
	public void addPosiblePartner(TradeBid bid) {
		posiblePartners.add((TradeLot)bid);
	}
}
