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
	public void delete(long dealId, long companyId) throws ServiceException {
		try {
			 propostionDao.deleteProposition(dealId, companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
