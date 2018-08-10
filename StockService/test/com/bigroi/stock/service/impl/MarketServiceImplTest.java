package com.bigroi.stock.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.LotExparationMessage;
import com.bigroi.stock.messager.message.TenderExparationMessage;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForCustomer;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForSeller;
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
	private DealExparationMessageForSeller dealExparationMessageForSellerByOpponent;
	@Mock
	private DealExparationMessageForCustomer dealExparationMessageForCustomer;
	@Mock
	private DealExparationMessageForSeller dealExparationMessageForSeller;
	@Mock
	private DealExparationMessageForCustomer dealExparationMessageForCustomerByOpponent;
	
	@Test
	public void checkLotExparationsTest() throws ParseException{
		// given
		final BidStatus STATUS = BidStatus.INACTIVE;
		final Date DATE = getExpDate();
		
		Lot lot = createObject(Lot.class);
		lot.setExparationDate(DATE);
		List<Lot> expectedList = ImmutableList.of(lot);
		// mock
		Mockito.when(lotDao.getActive()).thenReturn(expectedList);
		Mockito.doNothing().when(lotExparationMessage).send(lot);
		Mockito.doNothing().when(lotDao).update(expectedList);
		// when
		marketService.checkExparations();
		// then
		Assert.assertNotEquals(expectedList, null);
		Assert.assertEquals(STATUS, lot.getStatus());
		Mockito.verify(lotDao, Mockito.times(1)).getActive();
		Mockito.verify(lotDao, Mockito.times(1)).update(expectedList);
		Mockito.verify(lotExparationMessage, Mockito.times(1)).send(lot);
	}
	
	@Test
	public void checkTenderExparationsTest() throws ParseException{
		// given
		final BidStatus STATUS = BidStatus.INACTIVE;
		final Date DATE = getExpDate();
		
		Tender tender = createObject(Tender.class);
		tender.setExparationDate(DATE);
		List<Tender> expectedList = ImmutableList.of(tender);
		// mock
		Mockito.when(tenderDao.getActive()).thenReturn(expectedList);
		Mockito.doNothing().when(tenderExparationMessage).send(tender);
		Mockito.doNothing().when(tenderDao).update(expectedList);
		// when
		marketService.checkExparations();
		// then
		Assert.assertNotEquals(expectedList, null);
		Assert.assertEquals(STATUS, tender.getStatus());
		Mockito.verify(tenderDao, Mockito.times(1)).getActive();
		Mockito.verify(tenderDao, Mockito.times(1)).update(expectedList);
		Mockito.verify(tenderExparationMessage, Mockito.times(1)).send(tender);
	}
	
	@Test
	public void clearPreDealNullTest(){
		// given
		Deal deal = createObject(Deal.class);
		deal.setLotId(null);
		
		List<Deal> dealList = ImmutableList.of(deal);
		// mock
		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
		Mockito.doNothing().when(dealDao).deleteOnApprove();
		Mockito.doNothing().when(lotDao).closeLots();
		Mockito.doNothing().when(tenderDao).closeTeners();
		// when
		marketService.clearPreDeal();
		// then
		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
	}
	
	@Test
	public void clearPreDealSellerNotOnApproveTest(){
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		
		Deal deal = createObject(Deal.class);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		deal.setSellerChoice(PartnerChoice.REJECTED);
		deal.setBuyerChoice(PartnerChoice.ON_APPROVE);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		List<Deal> dealList = ImmutableList.of(deal);
		
		Lot lot = createObject(Lot.class);
		lot.setId(TENDER_ID);
		lot.setCompanyId(COMPANY_ID);
		
		Tender tender = createObject(Tender.class);
		tender.setId(TENDER_ID);
		tender.setCompanyId(COMPANY_ID);
		// mock
		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
		Mockito.doNothing().when(dealExparationMessageForSellerByOpponent).send(deal);
		Mockito.doNothing().when(dealExparationMessageForCustomer).send(deal);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(dealDao).deleteOnApprove();
		Mockito.doNothing().when(lotDao).closeLots();
		Mockito.doNothing().when(tenderDao).closeTeners();
		// when
		marketService.clearPreDeal();
		// then
		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
		Mockito.verify(dealExparationMessageForSellerByOpponent, Mockito.times(1)).send(deal);
		Mockito.verify(dealExparationMessageForCustomer, Mockito.times(1)).send(deal);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
	}
	
	@Test
	public void clearPreDealBuyerNotOnApproveTest(){
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		
		Deal deal = createObject(Deal.class);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		deal.setBuyerChoice(PartnerChoice.REJECTED);
		deal.setSellerChoice(PartnerChoice.ON_APPROVE);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		List<Deal> dealList = ImmutableList.of(deal);
		
		Lot lot = createObject(Lot.class);
		lot.setId(TENDER_ID);
		lot.setCompanyId(COMPANY_ID);
		
		Tender tender = createObject(Tender.class);
		tender.setId(TENDER_ID);
		tender.setCompanyId(COMPANY_ID);
		// mock
		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
		Mockito.doNothing().when(dealExparationMessageForSeller).send(deal);
		Mockito.doNothing().when(dealExparationMessageForCustomerByOpponent).send(deal);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(dealDao).deleteOnApprove();
		Mockito.doNothing().when(lotDao).closeLots();
		Mockito.doNothing().when(tenderDao).closeTeners();
		// when
		marketService.clearPreDeal();
		// then
		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
		Mockito.verify(dealExparationMessageForSeller, Mockito.times(1)).send(deal);
		Mockito.verify(dealExparationMessageForCustomerByOpponent, Mockito.times(1)).send(deal);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
	}
	
	@Test
	public void clearPreDealNotOnApproveTest(){
		// given
		final long LOT_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		
		Deal deal = createObject(Deal.class);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		deal.setBuyerChoice(PartnerChoice.ON_APPROVE);
		deal.setSellerChoice(PartnerChoice.ON_APPROVE);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		List<Deal> dealList = ImmutableList.of(deal);
		
		Lot lot = createObject(Lot.class);
		lot.setId(TENDER_ID);
		lot.setCompanyId(COMPANY_ID);
		
		Tender tender = createObject(Tender.class);
		tender.setId(TENDER_ID);
		tender.setCompanyId(COMPANY_ID);
		// mock
		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
		Mockito.doNothing().when(dealExparationMessageForSeller).send(deal);
		Mockito.doNothing().when(dealExparationMessageForCustomer).send(deal);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(dealDao).deleteOnApprove();
		Mockito.doNothing().when(lotDao).closeLots();
		Mockito.doNothing().when(tenderDao).closeTeners();
		// when
		marketService.clearPreDeal();
		// then
		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
		Mockito.verify(dealExparationMessageForSeller, Mockito.times(1)).send(deal);
		Mockito.verify(dealExparationMessageForCustomer, Mockito.times(1)).send(deal);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
	}
	
	@Test
	public void sendConfirmationMessagesTest(){
		// given
		Deal deal = createObject(Deal.class);
		List<Deal> dealList = ImmutableList.of(deal);
		// mock
		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
		Mockito.doNothing().when(dealConfirmationMessageForCustomer).send(deal);
		Mockito.doNothing().when(dealConfirmationMessageForSeller).send(deal);
		// when
		marketService.sendConfirmationMessages();
		// then
		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
		Mockito.verify(dealConfirmationMessageForCustomer, Mockito.times(1)).send(deal);
		Mockito.verify(dealConfirmationMessageForSeller, Mockito.times(1)).send(deal);
	}
}
