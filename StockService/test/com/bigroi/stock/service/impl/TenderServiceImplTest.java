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
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class TenderServiceImplTest extends BaseTest {

	@InjectMocks
	private TenderServiceImpl tenderService;
	@Mock
	private TenderDao tenderDao;
	@Mock
	private ProductDao productDao;
	
	@Test
	public void getByIdTest() throws DaoException, ServiceException{
		// given
		final long TENDER_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		Tender tender = createObject(Tender.class);
		tender.setCompanyId(COMPANY_ID);
		// mock
		Mockito.when(tenderDao.getById(TENDER_ID, COMPANY_ID)).thenReturn(tender);
		// when
		Tender actualLot = tenderService.getById(TENDER_ID, COMPANY_ID);
		Tender newLot = tenderService.getById(-1, COMPANY_ID);
		// then
		Assert.assertEquals(tender, actualLot);
		Assert.assertEquals(BidStatus.INACTIVE, newLot.getStatus());
		Assert.assertEquals(-1, newLot.getId());
		Mockito.verify(tenderDao, Mockito.times(1)).getById(TENDER_ID, COMPANY_ID);
	}
	
	@Test
	public void getByCompanyIdTest() throws DaoException, ServiceException{
		// given
		final long COMPANY_ID = random.nextLong();
		
		Tender tender = createObject(Tender.class);
		List<Tender> expectedList = ImmutableList.of(tender);
		// mock
		Mockito.when(tenderDao.getByCompanyId(COMPANY_ID)).thenReturn(expectedList);
		// when
		List<Tender> actualList = tenderService.getByCompanyId(COMPANY_ID);
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(tenderDao, Mockito.times(1)).getByCompanyId(COMPANY_ID);
	}
	
	@Test
	public void deleteTest() throws DaoException, ServiceException{
		// given
		final long TENDER_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Mockito.doNothing().when(tenderDao).delete(TENDER_ID, COMPANY_ID);
		// when
		tenderService.delete(TENDER_ID, COMPANY_ID);
		// then
		Mockito.verify(tenderDao, Mockito.times(1)).delete(TENDER_ID, COMPANY_ID);
	}
	
	@Test
	public void mergeAddTest() throws DaoException, ServiceException {
		// given
		final long COMPANY_ID = random.nextLong();
		final long PRODUCT_ID = random.nextLong();

		Tender tender = createObject(Tender.class);
		tender.setId(-1);
		tender.setProductId(PRODUCT_ID);

		Product product = createObject(Product.class);
		product.setId(PRODUCT_ID);
		// mock
		Mockito.doAnswer(a -> {
			((Tender) a.getArguments()[0]).setId(random.nextLong());
			return null;
		}).when(tenderDao).add(tender);
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		// when
		tenderService.merge(tender, COMPANY_ID);
		// then
		Assert.assertNotEquals(-1, tender.getId());
		Assert.assertEquals(PRODUCT_ID, product.getId());
		Mockito.verify(tenderDao, Mockito.times(1)).add(tender);
		Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void mergeUpdateTest() throws DaoException, ServiceException {
		// given
		final long COMPANY_ID = random.nextLong();
		final long PRODUCT_ID = random.nextLong();
		
		Tender tender = createObject(Tender.class);
		tender.setCompanyId(COMPANY_ID);
		tender.setProductId(PRODUCT_ID);
		
		Product product = createObject(Product.class);
		product.setId(PRODUCT_ID);
		// mock
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		// when
		tenderService.merge(tender, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, tender.getCompanyId());
		Assert.assertEquals(PRODUCT_ID, product.getId());
		Mockito.verify(tenderDao, Mockito.times(1)).update(tender, COMPANY_ID);
		Mockito.verify(productDao, Mockito.times(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void activateTest() throws ServiceException, DaoException{
		// given
		final long TENDER_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final BidStatus STATUS = BidStatus.ACTIVE;
		// mock
		Mockito.doNothing().when(tenderDao).setStatusById(TENDER_ID, COMPANY_ID,STATUS);
		// when
		tenderService.activate(TENDER_ID, COMPANY_ID);
		// then
		Mockito.verify(tenderDao, Mockito.times(1)).setStatusById(TENDER_ID, COMPANY_ID,STATUS);
	}
	
	@Test
	public void deactivateTest() throws ServiceException, DaoException{
		// given
		final long TENDER_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final BidStatus STATUS = BidStatus.INACTIVE;
		// mock
		Mockito.doNothing().when(tenderDao).setStatusById(TENDER_ID, COMPANY_ID, STATUS);
		// when
		tenderService.deactivate(TENDER_ID, COMPANY_ID);
		// then
		Mockito.verify(tenderDao, Mockito.times(1)).setStatusById(TENDER_ID, COMPANY_ID, STATUS);
	}
}
