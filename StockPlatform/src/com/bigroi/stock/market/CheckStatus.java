package com.bigroi.stock.market;

import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MessagerFactory;

public class CheckStatus implements Runnable {

	@Override
	public void run() {
		try {
			List<Lot> lots = DaoFactory.getLotDao().getActive();
			for (Lot lot : lots) {
				if (lot.isExpired()){
					lot.setStatus(Status.EXPIRED);
					DaoFactory.getLotDao().update(lot);
				}
			}
			List<Tender> tenders = DaoFactory.getTenderDao().getAllActive();
			for (Tender tender : tenders) {
				if (tender.isExpired()){
					tender.setStatus(Status.EXPIRED);
					DaoFactory.getTenderDao().update(tender);
				}
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}

	}

}
