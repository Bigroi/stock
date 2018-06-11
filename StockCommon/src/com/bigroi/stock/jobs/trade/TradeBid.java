package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bigroi.stock.bean.common.Bid;

public interface TradeBid extends Bid{

	void addPosiblePartner(TradeBid bid);
	
	List<? extends TradeBid> getPosiblePartners();
	
	default void removeFromPosiblePartners(){
		for (TradeBid partner : getPosiblePartners()){
			partner.getPosiblePartners().remove(this);
		}
		getPosiblePartners().clear();
	}

	default void setMaxVolumeExt(int volume) {
		setMaxVolume(volume);
		for (TradeBid partner : new ArrayList<>(getPosiblePartners())){
			if (partner.getMinVolume() > volume){
				getPosiblePartners().remove(partner);
			}
		}
	}
	
	default TradeBid getBestPartner() {
		final TradeBid thisObject = this;
		return Collections.max(getPosiblePartners(), new Comparator<TradeBid>() {
			@Override
			public int compare(TradeBid o1, TradeBid o2) {
				double o2Price = o2.getPrice() * Math.max(o2.getMaxVolume(), thisObject.getMaxVolume()) + getDistancePrice(o2, thisObject);
				double o1Price = o1.getPrice() * Math.max(o1.getMaxVolume(), thisObject.getMaxVolume()) + getDistancePrice(o1, thisObject);
				
				int result = (int)((o2Price - o1Price) * 100);
				if (result == 0){
					return (int)(o2.getCreationDate().getTime() - o1.getCreationDate().getTime());
				} else {
					return result;
				}
			}
		
		});
	}
	
	default int getTotalPosibleVolume(){
		int result = 0;
		for (TradeBid bid : getPosiblePartners()){
			result += bid.getMaxVolume();
		}
		return result;
	}
	
	static double getDistancePrice(TradeBid o1, TradeBid o2){
		return distance(o1, o2) * o1.getProduct().getDelivaryPrice();
	}
	
	static double distance(TradeBid o1, TradeBid o2) {

	    final int R = 6371; // Radius of the earth
	    o2.getAddress();
	    double latDistance = Math.toRadians(o2.getAddress().getLatitude() - o1.getAddress().getLatitude());
	    double lonDistance = Math.toRadians(o2.getAddress().getLongitude() - o1.getAddress().getLongitude());
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(o1.getAddress().getLatitude())) 
	            * Math.cos(Math.toRadians(o2.getAddress().getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c;// convert to km

	    return distance;
	}
}
