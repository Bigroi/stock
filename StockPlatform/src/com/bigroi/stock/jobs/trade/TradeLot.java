package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bigroi.stock.bean.Lot;

public class TradeLot extends Lot implements TradeBid{

	private List<TradeTender> posiblePartners = new ArrayList<>();
	
	@Override
	public List<? extends TradeBid> getPosiblePartners() {
		return posiblePartners;
	}
	
	@Override
	public int getTotalPosibleVolume(){
		int result = 0;
		for (TradeTender tender : posiblePartners){
			result += tender.getVolume();
		}
		return result;
	}

	@Override
	public void addPosiblePartner(TradeBid bid) {
		posiblePartners.add((TradeTender)bid);
	}

	@Override
	public TradeBid getBestPartner() {
		return Collections.max(posiblePartners, new Comparator<TradeTender>() {
			@Override
			public int compare(TradeTender o1, TradeTender o2) {
				int result = (int)((o2.getMaxPrice() - o1.getMaxPrice()) * 100);
				if (result == 0){
					return (int)(o2.getExpDate().getTime() - o1.getExpDate().getTime());
				} else {
					return result;
				}
			}
		
		});
	}

	@Override
	public void removeFromPosiblePartners() {
		for (TradeBid partner : posiblePartners){
			partner.getPosiblePartners().remove(this);
		}
		posiblePartners = new ArrayList<>();
	}
	
	@Override
	public void setVolume(int volume) {
		super.setVolume(volume);
		for (TradeTender partner : new ArrayList<>(posiblePartners)){
			if (partner.getMinVolume() > volume){
				posiblePartners.remove(partner);
			}
		}
	}
}
