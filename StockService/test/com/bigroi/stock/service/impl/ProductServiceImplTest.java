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
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest extends BaseTest {
	
	@InjectMocks
	private ProductServiceImpl prodService;
	@Mock
	private ProductDao productDao;
	@Mock
	private LotDao lotDao;
	@Mock
	private TenderDao tenderDao;
	
	@Test
	public void getAllProductsTest() throws DaoException, ServiceException{
		// given
		Product product = createObject(Product.class);
		List<Product> listPRod = ImmutableList.of(product);
		// mock
		Mockito.when(productDao.getAllProducts()).thenReturn(listPRod);
		// when
		prodService.getAllProducts();
		// then
		Mockito.verify(productDao, Mockito.timeout(1)).getAllProducts();
	}
	
	@Test
	public void getAllActiveProductsForUITest() throws DaoException, ServiceException{
		// given
		ProductForUI productForUI = createObject(ProductForUI.class);
		List<ProductForUI> listPRod = ImmutableList.of(productForUI);
		// mock
		Mockito.when(productDao.getAllActiveProductsForUI()).thenReturn(listPRod);
		// when
		prodService.getAllActiveProductsForUI();
		// then
		Mockito.verify(productDao, Mockito.timeout(1)).getAllActiveProductsForUI();
	}
	
	@Test
	public void getAllActiveProductsTest() throws DaoException, ServiceException{
		// given
		Product product = createObject(Product.class);
		List<Product> listPRod = ImmutableList.of(product);
		// mock
		Mockito.when(productDao.getAllActiveProducts()).thenReturn(listPRod);
		// when
		prodService.getAllActiveProducts();
		// then
		Mockito.verify(productDao, Mockito.timeout(1)).getAllActiveProducts();
	}
	
	@Test
	public void getProductByIdTest() throws DaoException, ServiceException{
		// given
		final long PRODUCT_ID = random.nextLong();
		
		Product product = createObject(Product.class);
		product.setId(PRODUCT_ID);
		// mock
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		// when
		Product actualProduct = prodService.getProductById(PRODUCT_ID);
		Product actualNewProduct = prodService.getProductById(-1);
		// then
		Assert.assertEquals(product, actualProduct);
		Assert.assertEquals(-1, actualNewProduct.getId());
		Mockito.verify(productDao, Mockito.timeout(1)).getById(PRODUCT_ID);
	}
	
	@Test
	public void mergeAddTest() throws DaoException, ServiceException{
		// given
		Product product = createObject(Product.class);
		product.setId(-1);
		// mock
		Mockito.doAnswer(a -> {
								((Product)a.getArguments()[0]).setId(random.nextLong()); 
								return null;
								})
			.when(productDao).add(product);
		// when
		prodService.merge(product);
		// then
		Mockito.verify(productDao, Mockito.timeout(1)).add(product);
	}
	
	@Test
	public void mergeUdateTest() throws DaoException, ServiceException{
		// given
		Product product = createObject(Product.class);
		// when
		prodService.merge(product);
		// then
		Mockito.verify(productDao, Mockito.timeout(1)).update(product);
	}
	
	@Test
	public void deleteTest() throws DaoException, ServiceException{
		// given
		final long PRODUCT_ID = random.nextLong();
		final BidStatus STATUS = BidStatus.INACTIVE;
		final String YES = "Y";
		
		Product product = createObject(Product.class);
		//mock
		Mockito.when(productDao.getById(PRODUCT_ID)).thenReturn(product);
		Mockito.when(lotDao.setStatusByProductId(PRODUCT_ID, STATUS)).thenReturn(true);
		Mockito.when(tenderDao.setStatusByProductId(PRODUCT_ID, STATUS)).thenReturn(true);
		// when
		prodService.delete(PRODUCT_ID);
		// then
		Assert.assertEquals(YES, product.getRemoved());
		Mockito.verify(productDao, Mockito.timeout(1)).getById(PRODUCT_ID);
		Mockito.verify(lotDao, Mockito.timeout(1)).setStatusByProductId(PRODUCT_ID, STATUS);
		Mockito.verify(tenderDao, Mockito.timeout(1)).setStatusByProductId(PRODUCT_ID, STATUS);
	}
	
	@Test
	public void getTradeOffersLessFifthTest() throws DaoException, ServiceException{
		// given
		final long PRODUCT_ID = random.nextLong();
		
		List<Bid> bids = new ArrayList<>();
		List<Lot> listLot = ImmutableList.of(createObject(Lot.class));
		List<Tender> listTender = ImmutableList.of(createObject(Tender.class));
		// mock
		Mockito.when(lotDao.getActiveByProductId(PRODUCT_ID)).thenReturn(listLot);
		Mockito.when(tenderDao.getActiveByProductId(PRODUCT_ID)).thenReturn(listTender);
		// when
		bids.addAll(listLot);
		bids.addAll(listTender);
		prodService.getTradeOffers(PRODUCT_ID);
		// then
		Assert.assertTrue(bids.size() < 5);
		Mockito.verify(lotDao, Mockito.timeout(1)).getActiveByProductId(PRODUCT_ID);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getActiveByProductId(PRODUCT_ID);
	}
	
	@Test
	public void getTradeOffersSizeMoreFifthTest() throws DaoException, ServiceException{
		// given
		final long PRODUCT_ID = random.nextLong();
		final int SIZE = 3;
		
		List<Bid> bids = new ArrayList<>();
		List<Lot> listLot = new ArrayList<>();
		List<Tender> listTender = new ArrayList<>();
		
		for(int i = 0; i < SIZE; i++){
			listLot.add(createObject(Lot.class));
			listTender.add(createObject(Tender.class));
		}
		// mock
		Mockito.when(lotDao.getActiveByProductId(PRODUCT_ID)).thenReturn(listLot);
		Mockito.when(tenderDao.getActiveByProductId(PRODUCT_ID)).thenReturn(listTender);
		// when
		bids.addAll(listLot);
		bids.addAll(listTender);
		prodService.getTradeOffers(PRODUCT_ID);
		// then
		Assert.assertTrue(bids.size() > 5);
		Mockito.verify(lotDao, Mockito.timeout(1)).getActiveByProductId(PRODUCT_ID);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getActiveByProductId(PRODUCT_ID);
	}
}
