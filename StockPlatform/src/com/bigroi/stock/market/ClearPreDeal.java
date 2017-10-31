package com.bigroi.stock.market;

import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;
import com.bigroi.stock.messager.MessagerFactory;

public class ClearPreDeal implements Runnable {
	@Override
	public void run(){
		try {
			List<PreDeal> predials = DaoFactory.getPreDealDao().getAll();
			for (PreDeal preDeal : predials) {
				if (preDeal.getSellerApprovBool() && preDeal.getCustApprovBool())
					continue;
				setStatuses(preDeal);
				new Message().sendMessageClearPredeal(preDeal);
			}
			DaoFactory.getPreDealDao().deleteAll();
		} catch (DaoException | MailManagerException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}
	}

	private void setStatuses(PreDeal predeal) throws DaoException {
		Lot lot = DaoFactory.getLotDao().getById(predeal.getLotId());
		if (lot.isExpired()) {
			lot.setStatus(Status.EXPIRED);
		} else {
			lot.setStatus(Status.IN_GAME);
		}
		DaoFactory.getLotDao().update(lot);
		Tender tender = DaoFactory.getTenderDao().getById(predeal.getTenderId());
		if (tender.isExpired()) {
			tender.setStatus(Status.EXPIRED);
		} else {
			tender.setStatus(Status.IN_GAME);
		}
		DaoFactory.getTenderDao().update(tender);
	}

}
