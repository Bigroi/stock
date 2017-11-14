package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.List;

import com.bigroi.stock.bean.Tender;

public class TradeTender extends Tender {

	private List<TradeLot> posibleLots = new ArrayList<>();
	
	public void addPosibleLot(TradeLot lot){
		posibleLots.add(lot);
	}
	
	public boolean tryRemovePosibleLot(TradeLot lot){
		if (lot.getVolume() < this.getVolume()){
			posibleLots.remove(lot);
			return true;
		} else {
			return false;
		}
	}

}
