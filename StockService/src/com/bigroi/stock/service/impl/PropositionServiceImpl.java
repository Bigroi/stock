package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PropositionDao;
import com.bigroi.stock.service.PropositionService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class PropositionServiceImpl implements PropositionService {

	@Autowired
	private PropositionDao propostionDao;
	
	@Override
	public List<Proposition> getListProposition() throws ServiceException {
		try {
			return propostionDao.getListPropositions();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(long id, long companyId) throws ServiceException {
		try {
			 propostionDao.deleteProposition(id, companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Proposition> getListHystoryProposition(long companyId) throws ServiceException {
		try {
			return propostionDao.getListPropositionsByStatusAndUserId(companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Proposition> getListPropositionsByTrans() throws ServiceException {
		try {
			return propostionDao.getListPropositionsTrans();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
