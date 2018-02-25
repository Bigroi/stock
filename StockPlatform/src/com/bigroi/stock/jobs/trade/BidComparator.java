package com.bigroi.stock.jobs.trade;

import java.util.Comparator;

public class BidComparator implements Comparator<TradeBid>{

	@SuppressWarnings("unused")
	private final int partnerVolume;
	
	public BidComparator(int partnerVolume){
		this.partnerVolume = partnerVolume;
	}
	
	@Override
	public int compare(TradeBid o1, TradeBid o2) {
		int result = (int)((o2.getPrice() - o1.getPrice()) * 100);
		if (result == 0){
			return (int)(o2.getCreationDate().getTime() - o1.getCreationDate().getTime());
		} else {
			return result;
		}
	}


}
