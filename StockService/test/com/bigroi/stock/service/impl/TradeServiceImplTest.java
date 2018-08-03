package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTest extends BaseTest{

	@InjectMocks
	private TradeServiceImpl tradeService;
	@Mock
	private ProductDao productDao;
	@Mock
	private DealDao dealDao;
	@Mock
	private LotDao lotDao;
	@Mock
	private TenderDao tenderDao;
	@Mock
	private DealConfirmationMessageForCustomer dealConfirmationMessageForCustomer;
	@Mock
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	
	@Test
	public void tradeTest() throws DaoException, ServiceException{
		// given
		final long PRODUCT_ID = random.nextLong();
		
		Set<Tender> tendersToUpdate = new HashSet<>();
		Set<Lot> lotsToUpdate = new HashSet<>();
		List<Deal> deals = new ArrayList<>();
		
		List<Lot> tradeLots = new ArrayList<>();
		List<Tender> tradeTenders = new ArrayList<>();
		List<Product> listProd = new ArrayList<>();
		for(int i = 0; i < 3; i++){
			tradeLots.add(createObject(Lot.class));
			tradeTenders.add(createObject(Tender.class));
			listProd.add(createObject(Product.class));
		}
		
		
		// mock
		Mockito.when(productDao.getAllActiveProducts()).thenReturn(listProd);
		Mockito.doNothing().when(dealDao).getPosibleDeals(tradeLots, tradeTenders, PRODUCT_ID);//???
		Mockito.doNothing().when(dealDao).add(deals);
		Mockito.doNothing().when(lotDao).update(lotsToUpdate);
		Mockito.doNothing().when(tenderDao).update(tendersToUpdate);
		// when
		tradeService.trade();
		// then
		/*Mockito.verify(productDao, Mockito.timeout(1)).getAllActiveProducts();
		Mockito.verify(dealDao, Mockito.timeout(1)).getPosibleDeals(tradeLots, tradeTenders, PRODUCT_ID);
		Mockito.verify(dealDao, Mockito.timeout(1)).add(deals);
		Mockito.verify(lotDao, Mockito.timeout(1)).update(lotsToUpdate);
		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tendersToUpdate);*/
	}
}
