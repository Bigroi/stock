package com.bigroi.stock.jobs.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;
import com.bigroi.stock.util.Generator;

public class TradeJob implements Runnable{

	@Override
	public void run() {
		try{
			List<Product> list = ServiceFactory.getProductService().getAllActiveProducts();
			for (Product product : list){
				List<TradeLot> tradeLots = new ArrayList<>();
				List<TradeTender> tradeTenders = new ArrayList<>();
				ServiceFactory.getPreDealService().getPosibleDeals(tradeLots, tradeTenders, product.getId());
				if (tradeLots.size() < tradeTenders.size()){
					TradeLot lot = Collections.max(tradeLots, 
							new Comparator<TradeLot>() {
						@Override
						public int compare(TradeLot lot1, TradeLot lot2) {
							return lot1.getPosibleTenders().size() - 
									lot2.getPosibleTenders().size();
						}
					});
					
					if (lot.getPosibleTenders().size() == 0){
						notifyNoTenders(lot);
						tradeLots.remove(lot);
					}
					
					TradeTender tender = Collections.max(lot.getPosibleTenders(), 
							new Comparator<TradeTender>() {
								@Override
								public int compare(TradeTender tender1, TradeTender tender2) {
									return (int)((tender1.getMaxPrice() - tender2.getMaxPrice()) * 10000);
								}
					});
					
					PreDeal preDeal = createNewPreDeal(lot, tender);
					
					ServiceFactory.getPreDealService().add(preDeal);
					
					lot.setVolume(lot.getVolume() - preDeal.getVolume());
					for (TradeTender posibleTender : new ArrayList<>(lot.getPosibleTenders())){
						if (posibleTender.tryRemovePosibleLot(lot)){
							lot.getPosibleTenders().remove(posibleTender);
						}
					}
					
					if (lot.getPosibleTenders().size() == 0){
						tradeLots.remove(lot);
					}
				}
			}
		
		}catch (ServiceException e) {
			// TODO: handle exception
		}
	}

	private PreDeal createNewPreDeal(TradeLot lot, TradeTender tender) {
		PreDeal preDeal = new PreDeal();
		preDeal.setCustApprovBool(false);
		preDeal.setCustomerHashCode(Generator.generateLinkKey(15));
		preDeal.setDealDate(new Date());
		preDeal.setLotId(lot.getId());
		preDeal.setSellerApprovBool(false);
		preDeal.setSellerHashCode(Generator.generateLinkKey(15));
		preDeal.setTenderId(tender.getId());
		preDeal.setVolume(Math.abs(lot.getVolume() - tender.getVolume()));
		return preDeal;
	}

	private void notifyNoTenders(TradeLot lot) {
		System.out.println("No tenders for lot " + lot);
		
	}

	

}
