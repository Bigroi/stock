package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.LotService;
import com.bigroi.stock.service.ServiceException;

public class LotServiceImpl implements LotService {

	LotDao lotDao;

	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}

	@Override
	@Transactional
	public Lot getById(long id) throws ServiceException {
		try {
			return lotDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public void addLot(Lot lot) throws ServiceException {
		try {
			lotDao.add(lot);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

	@Override
	@Transactional
	public boolean updateByIdLot(Lot lot) throws ServiceException {
		try {
			return lotDao.updateById(lot);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return false;
	}

	@Override
	@Transactional
	public List<Lot> getBySellerId(long salerId) throws ServiceException {
		try {
			return lotDao.getBySellerId(salerId);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}

	@Override
	@Transactional
	public void setStatusInGame(long id) throws ServiceException {
		try {
			Lot lot = DaoFactory.getLotDao().getById(id);
			if (lot.getStatus() == Status.DRAFT) {
				lot.setStatus(Status.IN_GAME);
				lotDao.updateById(lot);
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

	@Override
	@Transactional
	public void setStatusCancel(long id) throws ServiceException {
		try {
			Lot lot = DaoFactory.getLotDao().getById(id);
			if ((lot.getStatus() == Status.DRAFT) || (lot.getStatus() == Status.IN_GAME)) {
				lot.setStatus(Status.CANCELED);
				lotDao.updateById(lot);
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

}
