package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest extends BaseTest {

	@InjectMocks
	private CompanyServiceImpl companyService;
	@Mock
	private CompanyDao companyDao;
	@Mock
	private LotDao lotDao;
	@Mock
	private TenderDao tenderDao;

	@Test
	public void getAllCompaniesTest()  {
		// given
		List<Company> expectedList = new ArrayList<>();
		// mock
		Mockito.when(companyDao.getAllCompany()).thenReturn(expectedList);
		// when
		List<Company> actualList = companyService.getAllCompanies();
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(companyDao, Mockito.times(1)).getAllCompany();
	}

	@Test
	public void getCompanyByIdTest()  {
		// given
		final long COMPANY_ID = random.nextLong();
		Company expectedCompany = createObject(Company.class);
		// mock
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(expectedCompany);
		// when
		Company actualCompany = companyService.getCompanyById(COMPANY_ID);
		// then
		Assert.assertEquals(expectedCompany, actualCompany);
		Mockito.verify(companyDao, Mockito.times(1)).getById(COMPANY_ID);
	}

	@Test
	public void getByNameTest()  {
		// given
		final String name = randomString();
		
		Company expectedCompany = createObject(Company.class);
		expectedCompany.setName(name);
		// mock
		Mockito.when(companyDao.getByName(name)).thenReturn(expectedCompany);
		// when
		Company actualCompany = companyService.getByName(name);
		// then
		Assert.assertEquals(expectedCompany, actualCompany);
		Mockito.verify(companyDao, Mockito.times(1)).getByName(name);
	}

	@Test
	public void getByRegNumberTest()  {
		// given
		final String regNumber = randomString();
		
		Company expectedCompany = createObject(Company.class);
		expectedCompany.setRegNumber(regNumber);
		// mock
		Mockito.when(companyDao.getByRegNumber(regNumber)).thenReturn(expectedCompany);
		// when
		Company actualCompany = companyService.getByRegNumber(regNumber);
		// then
		Assert.assertEquals(expectedCompany, actualCompany);
		Mockito.verify(companyDao, Mockito.times(1)).getByRegNumber(regNumber);
	}

	@Test
	public void changeStatusCompanyAsNotVerifiedTest()  {
		// given
		final long COMPANY_ID = random.nextLong();
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		company.setStatus(CompanyStatus.NOT_VERIFIED);
		// mock
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(companyDao).setStatus(COMPANY_ID, CompanyStatus.VERIFIED);
		// when
		companyService.changeStatusCompany(COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(company.getStatus(), CompanyStatus.NOT_VERIFIED);
		
		Mockito.verify(companyDao, Mockito.times(1)).setStatus(COMPANY_ID, CompanyStatus.VERIFIED);
	}
	
	@Test
	public void changeStatusCompanyAsVerifiedTest()  {
		// given
		final long COMPANY_ID = random.nextLong();
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		company.setStatus(CompanyStatus.VERIFIED);
		// mock
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(companyDao).setStatus(COMPANY_ID, CompanyStatus.REVOKED);
		Mockito.when(lotDao.setStatusByCompanyId(COMPANY_ID, BidStatus.INACTIVE)).thenReturn(true);
		Mockito.when(tenderDao.setStatusByCompanyId(COMPANY_ID, BidStatus.INACTIVE)).thenReturn(true);
		// when
		companyService.changeStatusCompany(COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(company.getStatus(), CompanyStatus.VERIFIED);
		
		Mockito.verify(companyDao, Mockito.times(1)).setStatus(COMPANY_ID, CompanyStatus.REVOKED);
		Mockito.verify(lotDao, Mockito.times(1)).setStatusByCompanyId(COMPANY_ID, BidStatus.INACTIVE);
		Mockito.verify(tenderDao, Mockito.times(1)).setStatusByCompanyId(COMPANY_ID, BidStatus.INACTIVE);
	}
	
	@Test
	public void changeStatusCompanyAsRevokedTest()  {
		// given
		final long COMPANY_ID = random.nextLong();
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		company.setStatus(CompanyStatus.REVOKED);
		// mock
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(companyDao).setStatus(COMPANY_ID, CompanyStatus.VERIFIED);
		// when
		companyService.changeStatusCompany(COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(company.getStatus(), CompanyStatus.REVOKED);
		
		Mockito.verify(companyDao, Mockito.times(1)).setStatus(COMPANY_ID, CompanyStatus.VERIFIED);
	}
}
