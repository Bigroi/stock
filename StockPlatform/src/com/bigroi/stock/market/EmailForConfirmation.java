package com.bigroi.stock.market;

import java.io.IOException;
import java.util.List;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.messager.Message;

public class EmailForConfirmation {
	
	public void send() throws MarketException, IOException, MailManagerException {
		try {

			List<PreDeal> preDials = DaoFactory.getPreDealDao().getAllPreDeal();
			for (PreDeal preDeal : preDials) {
				setStatuses(preDeal);
				new Message().sendMessageForConfirmation(preDeal);
			}
		} catch (DaoException e) {
			throw new MarketException(e);
		}
	}

	private void setStatuses(PreDeal predeal) throws DaoException {
		Lot lot = DaoFactory.getLotDao().getById(predeal.getLotId());
		lot.setStatus(Status.ON_APPROVAL);
		DaoFactory.getLotDao().updateById(lot);
		Tender tender = DaoFactory.getTenderDao().getById(predeal.getTenderId());
		tender.setStatus(Status.ON_APPROVAL);
		DaoFactory.getTenderDao().updateById(tender);

	}
	
}
