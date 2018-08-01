package com.bigroi.stock.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

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
		Mockito.when(lotDao.getById(LOT_ID, COMPANY_ID)).thenReturn(lot);
		// when
		Lot actualLot = lotService.getById(LOT_ID, COMPANY_ID);
		Lot newLot = lotService.getById(-1, COMPANY_ID);
		// then
		Assert.assertEquals(lot, actualLot);
		Assert.assertEquals(BidStatus.INACTIVE, newLot.getStatus());
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
		lot.setProductId(PRODUCT_ID);
		
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
		Assert.assertNotEquals(-1, lot.getId());
		Assert.assertEquals(PRODUCT_ID, product.getId());
		Mockito.verify(lotDao, Mockito.times(1)).add(lot);
		Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void mergeUpdateTest() throws DaoException, ServiceException {
		// given
		final long COMPANY_ID = random.nextLong();
		final long PRODUCT_ID = random.nextLong();
		
		Lot lot = createObject(Lot.class);
		lot.setCompanyId(COMPANY_ID);
		lot.setProductId(PRODUCT_ID);
		
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
		Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void getByCompanyIdTest() throws ServiceException, DaoException{
		// given
		final long COMPANY_ID = random.nextLong();
		// mock
		Lot lot =createObject(Lot.class);
		List<Lot> expectedList = ImmutableList.of(lot);
		Mockito.when(lotDao.getByCompanyId(COMPANY_ID)).thenReturn(expectedList);
		// when
		List<Lot> actualList = lotService. getByCompanyId(COMPANY_ID);
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(lotDao, Mockito.times(1)).getByCompanyId(COMPANY_ID);
	}
	
	@Test
	public void activateTest() throws ServiceException, DaoException{
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final BidStatus STATUS = BidStatus.ACTIVE;
		// mock
		Mockito.when(lotDao.setStatusById(LOT_ID, COMPANY_ID,STATUS)).thenReturn(true);
		// when
		lotService.activate(LOT_ID, COMPANY_ID);
		// then
		Mockito.verify(lotDao, Mockito.times(1)).setStatusById(LOT_ID, COMPANY_ID,STATUS);
	}
	
	@Test
	public void deleteTest() throws DaoException, ServiceException{
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		//mock
		Mockito.doNothing().when(lotDao).delete(LOT_ID, COMPANY_ID);
		//when
		lotService.delete(LOT_ID, COMPANY_ID);
		//then
		Mockito.verify(lotDao, Mockito.times(1)).delete(LOT_ID, COMPANY_ID);
	}
	
	@Test
	public void deactivateTest() throws DaoException, ServiceException{
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final BidStatus STATUS = BidStatus.INACTIVE;
		// mock
		Mockito.when(lotDao.setStatusById(LOT_ID, COMPANY_ID, STATUS)).thenReturn(true);
		// when
		lotService.deactivate(LOT_ID, COMPANY_ID);
		// then
		Mockito.verify(lotDao, Mockito.times(1)).setStatusById(LOT_ID, COMPANY_ID, STATUS);
	}
}
