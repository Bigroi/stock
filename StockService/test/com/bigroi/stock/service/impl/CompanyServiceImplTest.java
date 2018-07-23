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

import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceImplTest extends BaseTest {

	@InjectMocks
	private CompanyServiceImpl companyService;
	@Mock
	private CompanyDao companyDao;

	@Test
	public void getAllCompaniesTest() throws ServiceException, DaoException {
		// mock
		List<Company> expectedList = new ArrayList<>();
		expectedList.add(new Company());
		Mockito.when(companyDao.getAllCompany()).thenReturn(expectedList);
		// when
		List<Company> actualList = companyService.getAllCompanies();
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(companyDao, Mockito.times(1)).getAllCompany();
	}

	@Test
	public void getCompanyByIdTest() throws ServiceException, DaoException {
		// given
		final long COMPANY_ID = random.nextLong();
		//mock
		Company expectedCompany = new Company();
		expectedCompany.setId(COMPANY_ID);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(expectedCompany);
		//when
		Company actualCompany = companyService.getCompanyById(COMPANY_ID);
		//then
		Assert.assertEquals(expectedCompany, actualCompany);
		Mockito.verify(companyDao, Mockito.times(1)).getById(COMPANY_ID);
	}
	
	/*@Test
	public void getByNameTest() throws ServiceException, DaoException {
		// given
		final long COMPANY_ID = random.nextLong();
		//mock
		Company expectedCompany = new Company();
		expectedCompany.setId(COMPANY_ID);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(expectedCompany);
		//when
		Company actualCompany = companyService.getCompanyById(COMPANY_ID);
		//then
		Assert.assertEquals(expectedCompany, actualCompany);
		Mockito.verify(companyDao, Mockito.times(1)).getById(COMPANY_ID);
	}*/
}
