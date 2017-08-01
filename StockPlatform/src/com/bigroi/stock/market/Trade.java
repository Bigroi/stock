package com.bigroi.stock.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.util.KeyGenerator;

public class Trade implements Runnable {
	private List<Lot> lots;
	private List<Tender> tenders;
	private Map<Long, Top> topLots;
	private Map<Long, Top> topTenders;
	private static final int KEY_LENGTH = 40;

	@Override
	public void run() {
		try {
			List<Long> productIds = DaoFactory.getProductDao().getAllProductIdInGame();
			for (long productId : productIds) {
				lots = DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(productId);
				tenders = DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPriceDesc(productId);
				// if (lots.isEmpty() || tenders.isEmpty())continue; // У нас
				// это отсекается SQL

				removeLotsTendersWrongPrice();

				if (lots.size() <= tenders.size()){
					linkingTransactionsByLots();
					pairingByLots();
				}else  {
					linkingTransactionsByTenders();
					pairingByTenders();
				}	
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}

	}
	
	private void linkingTransactionsByLots() throws DaoException {
		topLots = new HashMap<>();
		topTenders = new HashMap<>();
		for (Lot lot : lots) {
			for (Tender tender : tenders) {				
				if (DaoFactory.getBlacklistDao().getTenderIdAndLotId(tender.getId(), lot.getId()))
					continue;
				if ((tender.getMaxPrice()) < (lot.getMinPrice()))
					continue;
				LinkingOneTransaction(lot, tender);				
			}
		}
	}
			
	//метод не проверен, но аналогичен проверенному linkingTransactionsByLots()
	private void linkingTransactionsByTenders() throws DaoException {
		
		topLots = new HashMap<>();
		topTenders = new HashMap<>();
		for (Tender tender : tenders) {
			for (Lot lot : lots) {
				if (DaoFactory.getBlacklistDao().getTenderIdAndLotId(tender.getId(), lot.getId()))
					if (DaoFactory.getBlacklistDao().getTenderIdAndLotId(tender.getId(), lot.getId()))
						continue;
					if ((tender.getMaxPrice()) < (lot.getMinPrice()))
						continue;
					LinkingOneTransaction(lot, tender);	
			}
		}
		
	}

	private void LinkingOneTransaction(Lot lot, Tender tender) {
		Top topLot;
		if (topLots.containsKey(lot.getId())) {
			topLot = topLots.get(lot.getId());
		} else {
			topLot = new Top();
		}

		Top topTender;
		if (topTenders.containsKey(tender.getId())){
			topTender = topTenders.get(tender.getId());
		}else{
			topTender = new Top();
		}				
		
		List<Long>linkedTenders = topLot.getLinkedId();
		if (linkedTenders == null)linkedTenders = new ArrayList<>();
		linkedTenders.add(tender.getId());
		topLot.setLinkedId(linkedTenders);
		topLots.put(lot.getId(), topLot);				 
		
		List<Long>linkedLots = topTender.getLinkedId();
		if (linkedLots == null)linkedLots = new ArrayList<>();
		linkedLots.add(lot.getId());
		topTender.setLinkedId(linkedLots);
		topTenders.put(tender.getId(), topTender);
		
	}		
	
private void pairingByLots() throws DaoException {		
		while (!topLots.isEmpty()) {
			int count = 0;
			long lotId = 0;
			for (Iterator<Map.Entry<Long, Top>> it = topLots.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Long, Top> top = it.next();
				List<Long> linkedId = top.getValue().getLinkedId();
				if (linkedId == null || linkedId.isEmpty()) {
					it.remove();
					continue;
				}				
				if (count ==0 || linkedId.size() < count) {
					count = linkedId.size();
					lotId = top.getKey();
				}
			}
			if (count == 0){
				break;
			}
			
			List<Long> linkedIdTenders = topLots.get(lotId).getLinkedId();
			Collections.sort(linkedIdTenders);
			long tenderId = linkedIdTenders.get(linkedIdTenders.size()-1);			
			List<Long> linkedIdLots = topTenders.get(tenderId).getLinkedId();					
			addPredeal(lotId, tenderId);			
			for (Long id : linkedIdLots) {
				if (topLots.containsKey(id)){
					topLots.get(id).getLinkedId().remove(tenderId);
				}
			}			
			topLots.remove(lotId);
			topTenders.remove(tenderId);
		}				
	}

	//метод не проверен, но аналогичен проверенному pairingByLots()
	private void pairingByTenders() throws DaoException {
		while (!topTenders.isEmpty()) {
			int count = 0;
			long tenderId = 0;
			for (Iterator<Map.Entry<Long, Top>> it = topTenders.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Long, Top> top = it.next();
				List<Long> linkedId = top.getValue().getLinkedId();
				if (linkedId == null || linkedId.isEmpty()) {
					it.remove();
					continue;
				}				
				if (count ==0 || linkedId.size() < count) {
					count = linkedId.size();
					tenderId = top.getKey();
				}
			}
			if (count == 0){
				break;
			}
			
			List<Long> linkedIdLots = topTenders.get(tenderId).getLinkedId();
			Collections.sort(linkedIdLots);
			long lotId = linkedIdLots.get(0);	/////////////////////////		
			List<Long> linkedIdtenders = topLots.get(lotId).getLinkedId();					
			addPredeal(lotId, tenderId);			
			for (Long id : linkedIdtenders) {
				if (topTenders.containsKey(id)){
					topTenders.get(id).getLinkedId().remove(lotId);
				}
			}			
			topLots.remove(lotId);
			topTenders.remove(tenderId);
		}	
	}
	
	
	private void addPredeal(long lotId, long tenderId) throws DaoException {
		PreDeal  predeal = new PreDeal();
		predeal.setLotId(lotId);
		predeal.setSellerHashCode(KeyGenerator.generate(KEY_LENGTH));
		predeal.setCustomerHashCode(KeyGenerator.generate(KEY_LENGTH));
		predeal.setTenderId(tenderId);
		predeal.setSellerApprovBool(false);
		predeal.setCustApprovBool(false);
		predeal.setDealDate(new Date());
		DaoFactory.getPreDealDao().add(predeal);	
		
	}
	
	private void removeLotsTendersWrongPrice() {

		double minPraceLot = lots.get(0).getMinPrice();
		double maxPraceTender = tenders.get(0).getMaxPrice();

		Iterator<Tender> iterTender = tenders.iterator();
		while (iterTender.hasNext()) {
			Tender tender = iterTender.next();
			if (tender.getMaxPrice() < minPraceLot) {
				iterTender.remove();
			}
		}
		Iterator<Lot> iterLot = lots.iterator();
		while (iterLot.hasNext()) {
			Lot lot = iterLot.next();
			if (lot.getMinPrice() > maxPraceTender) {
				iterLot.remove();
			}
		}
	}

}
