package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.dao.PropositionDao;
import com.bigroi.stock.service.PropositionService;

@Repository
public class PropositionServiceImpl implements PropositionService {

	@Autowired
	private PropositionDao propostionDao;
	
	@Override
	public List<Proposition> getListProposition() {
		return propostionDao.getListPropositions();
	}

	@Override
	public void delete(long id, long companyId) {
		 propostionDao.deleteProposition(id, companyId);
	}

	@Override
	public List<Proposition> getListHystoryProposition(long companyId) {
		return propostionDao.getListPropositionsByStatusAndUserId(companyId);
	}

	@Override
	public List<Proposition> getListPropositionsByTrans() {
		return propostionDao.getListPropositionsTrans();
	}
}
