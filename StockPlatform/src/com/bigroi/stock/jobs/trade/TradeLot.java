package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.List;

import com.bigroi.stock.bean.Lot;

public class TradeLot extends Lot {

	private List<TradeTender> posibleTenders = new ArrayList<>();
	
	public void addPosibleTender(TradeTender tender){
		posibleTenders.add(tender);
	}
	
	public boolean removePosibleTender(TradeTender tender){
		if (tender.getVolume() > this.getVolume()){
			posibleTenders.remove(tender);
			return true;
		} else {
			return false;
		}
	}
	
	public List<TradeTender> getPosibleTenders() {
		return posibleTenders;
	}
}
