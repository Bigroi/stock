package com.bigroi.stock.service.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.LotExparationMessage;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.TenderExparationMessage;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForSeller;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class MarketServiceImplTest extends BaseTest {
	
	@InjectMocks
	private MarketServiceImpl marketService;
	
	@Mock
	private LotDao lotDao;
	@Mock
	private TenderDao tenderDao;
	@Mock
	private DealDao dealDao;
	
	@Mock
	private LotExparationMessage lotExparationMessage;
	@Mock
	private TenderExparationMessage tenderExparationMessage;
	@Mock
	private DealConfirmationMessageForCustomer dealConfirmationMessageForCustomer;
	@Mock
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	@Mock
	//@Qualifier("dealExparationMessageForSellerByOpponent")
	private DealExparationMessageForSeller dealExparationMessageForSellerByOpponent;
	@Mock
	//@Qualifier("dealExparationMessageForCustomer")
	private DealExparationMessageForCustomer dealExparationMessageForCustomer;
	@Mock
	//@Qualifier("dealExparationMessageForSeller")
	private DealExparationMessageForSeller dealExparationMessageForSeller;
	@Mock
	//@Qualifier("dealExparationMessageForCustomerByOpponent")
	private DealExparationMessageForCustomer dealExparationMessageForCustomerByOpponent;
	
	@Test
	public void checkExparationsTest() throws DaoException, ServiceException, MessageException{
		// mock
		Lot lot = createObject(Lot.class);
		Date date = new Date();
		date.before(new Date());
		lot.setExparationDate(date);
		boolean bol = lot.isExpired();
		List<Lot> expectedList = ImmutableList.of(lot);
		Mockito.when(lotDao.getActive()).thenReturn(expectedList);
		Mockito.doNothing().when(lotExparationMessage).send(lot);
		Mockito.doNothing().when(lotDao).update(expectedList);
		// when
		marketService.checkExparations();
		// then
		Assert.assertNotEquals(expectedList, null);
		Mockito.verify(lotDao, Mockito.times(1)).getActive();
		Mockito.verify(lotDao, Mockito.times(1)).update(expectedList);
		
	}

}
