
package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.CompanyService;

@Repository
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private CompanyDao companyDao;

	@Override
	@Transactional
	public void changeStatusCompany(long companyId) {
		Company company = companyDao.getById(companyId);
		switch (company.getStatus()) {
		case NOT_VERIFIED:
			companyDao.setStatus(company.getId(), CompanyStatus.VERIFIED);
			break;
		case VERIFIED:
			companyDao.setStatus(company.getId(), CompanyStatus.REVOKED);
			lotDao.setStatusByCompanyId(companyId, BidStatus.INACTIVE);
			tenderDao.setStatusByCompanyId(companyId, BidStatus.INACTIVE);
			break;
		case REVOKED:
			companyDao.setStatus(company.getId(), CompanyStatus.VERIFIED);
			break;
		}
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyDao.getAllCompany();
	}

	@Override
	public Company getCompanyById(long id) {
		return companyDao.getById(id);
	}
	
	@Override
	public Company getByName(String name){
		return companyDao.getByName(name);
	}

	@Override
	public Company getByRegNumber(String regNumber){
		return companyDao.getByRegNumber(regNumber);
	}

}	
