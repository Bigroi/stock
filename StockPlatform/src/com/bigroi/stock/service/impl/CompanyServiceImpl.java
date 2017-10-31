package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
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
	public void changeStatusCompany(long companyId) throws ServiceException {
		try {
			Company company = companyDao.getById(companyId);
			switch (company.getStatus()) {
			case NOT_VERIFIED:
				companyDao.setStatus(company.getId(), CompanyStatus.VERIFIED);
				break;
			case VERIFIED:
				companyDao.setStatus(company.getId(), CompanyStatus.REVOKED);
				lotDao.setStatusBySellerId(companyId, Status.CANCELED);
				tenderDao.setStatusByCustomerId(companyId, Status.CANCELED);
				break;
			case REVOKED:
				companyDao.setStatus(company.getId(), CompanyStatus.VERIFIED);
				break;
			}
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Company> getAllCompanies() throws ServiceException {
		try {
			return companyDao.getAllCompany();
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Company getCompanyById(long id) throws ServiceException {
		try {
			return companyDao.getById(id);
		} catch (DaoException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			throw new ServiceException(e);
		}
	}
}	
