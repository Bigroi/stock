package com.bigroi.stock.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class LotServiceImplTest extends BaseTest{
	
	@InjectMocks
	private LotServiceImpl lotService;
	@Mock
	private LotDao lotDao;
	@Mock
	private ProductDao productDao;
	
	@Test
	public void getByIdTest() throws DaoException, ServiceException{
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Lot lot = createObject(Lot.class);
		lot.setCompanyId(COMPANY_ID);
		lot.setStatus(BidStatus.INACTIVE);
		lot.setId(-1);
		Mockito.when(lotDao.getById(LOT_ID, COMPANY_ID)).thenReturn(lot);
		// when
		Lot actualLot = lotService.getById(LOT_ID, COMPANY_ID);
		Lot newLot = lotService.getById(-1, COMPANY_ID);
		// then
		Assert.assertEquals(lot, actualLot);
		Assert.assertEquals(COMPANY_ID, lot.getCompanyId());
		Assert.assertEquals(BidStatus.INACTIVE, lot.getStatus());
		Assert.assertEquals(-1, newLot.getId());
		Mockito.verify(lotDao, Mockito.times(1)).getById(LOT_ID, COMPANY_ID);
	}
	
	@Test
	public void mergeAddTest() throws DaoException, ServiceException {
		// given
		final long COMPANY_ID = random.nextLong();
		final long PRODUCT_ID = random.nextLong();
		
		Lot lot = createObject(Lot.class);
		lot.setId(-1);
		
		Product product = createObject(Product.class);
		product.setId(PRODUCT_ID);
		//mock
		Mockito.doAnswer(a -> {
								((Lot)a.getArguments()[0]).setId(random.nextLong()); 
								return null;
								})
			.when(lotDao).add(lot);
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		// when
		lotService.merge(lot, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, lot.getCompanyId());
		Assert.assertNotEquals(-1, lot.getId());
		Assert.assertEquals(PRODUCT_ID, product.getId());
		Mockito.verify(lotDao, Mockito.times(1)).add(lot);
		//Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void mergeUpdateTest() throws DaoException, ServiceException {
		// given
		final long COMPANY_ID = random.nextLong();
		final long PRODUCT_ID = random.nextLong();
		
		Lot lot = createObject(Lot.class);
		lot.setCompanyId(COMPANY_ID);
		
		Product product = createObject(Product.class);
		product.setId(PRODUCT_ID);
		// mock
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		// when
		lotService.merge(lot, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, lot.getCompanyId());
		Assert.assertEquals(PRODUCT_ID, product.getId());
		Mockito.verify(lotDao, Mockito.times(1)).update(lot, COMPANY_ID);
		//Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
}
