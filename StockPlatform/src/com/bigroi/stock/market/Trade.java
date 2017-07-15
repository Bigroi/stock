package com.bigroi.stock.market;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.util.KeyGenerator;

public class Trade implements Runnable {
	private List<Lot> lots;
	private List<Tender> tenders;
	private static final int KEY_LENGTH = 40;

	@Override
	public void run() {
		try {
			List<Long> productIds = DaoFactory.getProductDao().getAllProductIdInGame();
			for (long productId : productIds) {				
				lots = DaoFactory.getLotDao().getByProductIdInGameOrderMinPrice(productId);
				tenders = DaoFactory.getTenderDao().getByProductIdInGameOrderMaxPrice(productId);
//				if (lots.isEmpty() || tenders.isEmpty())continue; // У нас это отсекается SQL
				removeLotsTendersWrongPrice();
//				alignmentNumberLotsTendders(); // надо при одновременном удалении лота и тендера при несовпадении цены
				pairing();			
			}
		} catch (DaoException e) {
			// TODO Emailing Admin
			e.printStackTrace();
		}

	}

	private void pairing() throws DaoException {
		while (lots.size() >0  && tenders.size() > 0) {
			Lot lot = lots.get(0);
			Tender tender = tenders.get(0);
			if ((tender.getMaxPrice())< (lot.getMinPrice())){
				tenders.remove(0);
//				lots.remove(0); // надо при одновременном удалении лота и тендера при несовпадении цены
			}else {
				PreDeal  predeal = new PreDeal();
				predeal.setLotId(lot.getId());
				predeal.setSellerHashCode(KeyGenerator.generate(KEY_LENGTH));
				predeal.setCustomerHashCode(KeyGenerator.generate(KEY_LENGTH));
				predeal.setTenderId(tender.getId());
				predeal.setSellerApprovBool(false);
				predeal.setCustApprovBool(false);
				predeal.setDealDate(new Date());
				DaoFactory.getPreDealDao().add(predeal);
				lot.setStatus(Status.ON_APPROVAL);
				DaoFactory.getLotDao().updateById(lot);
				tender.setStatus(Status.ON_APPROVAL);
				DaoFactory.getTenderDao().updateById(tender);
				tenders.remove(0);
				lots.remove(0);
			}
		}
		
	}

//	private void alignmentNumberLotsTendders() {
//	// надо при одновременном удалении лота и тендера при несовпадении цены	
//		int delta = tenders.size() - lots.size();
//		if (delta >0)
//			for (int i = 0; i < delta; i++)
//				tenders.remove(0);			
//		
//		if (delta <0)
//			for (int i = 0; i < -delta; i++)
//				lots.remove(lots.remove(lots.size()-1));				
//	}

	private void removeLotsTendersWrongPrice() {
		
		double minPraceLot = lots.get(0).getMinPrice();
		double maxPraceTender = tenders.get(tenders.size() - 1).getMaxPrice();
		
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
