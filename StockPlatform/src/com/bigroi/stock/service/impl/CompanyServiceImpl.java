package com.bigroi.stock.service.impl;

import java.util.List;



import org.springframework.transaction.annotation.Transactional;


import com.bigroi.stock.bean.Company;

import com.bigroi.stock.bean.common.CompanyStatus;

import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.MessagerFactory;
import com.bigroi.stock.service.CompanyService;
import com.bigroi.stock.service.ServiceException;


public class CompanyServiceImpl implements CompanyService {

	private LotDao lotDao;
	private TenderDao tenderDao;
	private CompanyDao companyDao;

	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	@Transactional
	public void statusCancelLotAndTender(long id) throws ServiceException {
		try {
			Company company = DaoFactory.getCompanyDao().getById(id);
			if (company.getStatus() == CompanyStatus.REVOKED) {
				lotDao.LotStatusCancel(company.getId());
				tenderDao.TenderStatusCancel(company.getId());
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
	}

	@Override
	@Transactional
	public void changeStatusCompany(long id) throws ServiceException {
		try {
			Company company = DaoFactory.getCompanyDao().getById(id);
			switch (company.getStatus()) {
			case NOT_VERIFIED:
				companyDao.setStatusVerified(company);
				break;
			case VERIFIED:
				companyDao.setStatusRevoked(company);
				break;
			case REVOKED:
				companyDao.setStatusNotVerified(company);
				break;
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}

	}

	@Override
	@Transactional
	public List<Company> getAllCompsny() throws ServiceException {
		try {
			return companyDao.getAllCompany();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
		}
		return null;
	}
}	
