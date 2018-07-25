package com.bigroi.stock.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceImplTest extends BaseTest {
	
	@InjectMocks
	private DealServiceImpl dealService;
	@Mock
	private DealDao dealDao;
	@Mock
	private CompanyDao companyDao;

	@Test
	public void getByIdTest() throws DaoException {

		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(new CompanyAddress());
		deal.setSellerAddress(new CompanyAddress());
		Company seller = createObject(Company.class);
		Company buyer = createObject(Company.class);
		Mockito.when(dealDao.getById(DEAL_ID, COMPANY_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(buyer);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(seller);
		// when
		deal.getBuyerAddress().setCompany(buyer);
		deal.getSellerAddress().setCompany(seller);
		// then
		Assert.assertNotEquals(deal, null);
		//in progress....
		
	}

}
