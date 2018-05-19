package com.bigroi.stock.jobs.trade;

import java.util.List;

import com.bigroi.stock.bean.common.Bid;

public interface TradeBid extends Bid{

	void addPosiblePartner(TradeBid bid);
	
	List<? extends TradeBid> getPosiblePartners();
	
	int getTotalPosibleVolume();

	TradeBid getBestPartner();

	void removeFromPosiblePartners();

}
