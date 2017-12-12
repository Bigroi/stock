package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bigroi.stock.bean.Tender;

public class TradeTender extends Tender implements TradeBid{

	private List<TradeLot> posiblePartners = new ArrayList<>();
	
	@Override
	public int getTotalPosibleVolume(){
		int result = 0;
		for (TradeLot bid : posiblePartners){
			result += bid.getVolume();
		}
		return result;
	}

	@Override
	public List<? extends TradeBid> getPosiblePartners() {
		return posiblePartners;
	}

	@Override
	public void addPosiblePartner(TradeBid bid) {
		posiblePartners.add((TradeLot)bid);
	}

	@Override
	public TradeBid getBestPartner() {
		return Collections.min(posiblePartners, new Comparator<TradeLot>() {
			@Override
			public int compare(TradeLot o1, TradeLot o2) {
				int result = (int)((o2.getMinPrice() - o1.getMinPrice()) * 100);
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
		for (TradeLot partner : new ArrayList<>(posiblePartners)){
			if (partner.getMinVolume() > volume){
				posiblePartners.remove(partner);
			}
		}
	}
}
