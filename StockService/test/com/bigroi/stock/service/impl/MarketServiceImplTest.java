//package com.bigroi.stock.service.impl;
//
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.bigroi.stock.bean.common.BidStatus;
//import com.bigroi.stock.bean.db.Company;
//import com.bigroi.stock.bean.db.CompanyAddress;
//import com.bigroi.stock.bean.db.Lot;
//import com.bigroi.stock.bean.db.Tender;
//import com.bigroi.stock.dao.DealDao;
//import com.bigroi.stock.dao.LotDao;
//import com.bigroi.stock.dao.TenderDao;
//import com.bigroi.stock.messager.message.LotExparationMessage;
//import com.bigroi.stock.messager.message.TenderExparationMessage;
//import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForBuyer;
//import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
//import com.bigroi.stock.messager.message.deal.DealExparationMessageForBuyer;
//import com.bigroi.stock.messager.message.deal.DealExparationMessageForSeller;
//import com.bigroi.stock.util.BaseTest;
//import com.bigroi.stock.util.LabelUtil;
//import com.google.common.collect.ImmutableList;
//
//@RunWith(MockitoJUnitRunner.class)
//public class MarketServiceImplTest extends BaseTest {
//
//	private static final Locale TEST_LANGUAGE = LabelUtil.parseString("pl");
//
//	@InjectMocks
//	private MarketServiceImpl marketService;
//
//	@Mock
//	private LotDao lotDao;
//	@Mock
//	private TenderDao tenderDao;
//	@Mock
//	private DealDao dealDao;
//
//	@Mock
//	private LotExparationMessage lotExparationMessage;
//	@Mock
//	private TenderExparationMessage tenderExparationMessage;
//	@Mock
//	private DealConfirmationMessageForBuyer dealConfirmationMessageForBuyer;
//	@Mock
//	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
//	@Mock
//	private DealExparationMessageForSeller dealExparationMessageForSellerByOpponent;
//	@Mock
//	private DealExparationMessageForBuyer dealExparationMessageForBuyer;
//	@Mock
//	private DealExparationMessageForSeller dealExparationMessageForSeller;
//	@Mock
//	private DealExparationMessageForBuyer dealExparationMessageForBuyerByOpponent;
//
//	@Test
//	public void checkLotExparationsTest() throws ParseException{
//		// given
//		final BidStatus STATUS = BidStatus.INACTIVE;
//		final Date DATE = getExpDate();
//
//		Lot lot = createObject(Lot.class);
//		lot.setCompanyAddress(createObject(CompanyAddress.class));
//		lot.getCompanyAddress().setCompany(createObject(Company.class));
//		lot.getCompanyAddress().getCompany().setLanguage(TEST_LANGUAGE.toString());
//		lot.setExparationDate(DATE);
//		List<Lot> expectedList = ImmutableList.of(lot);
//		// mock
//		Mockito.when(lotDao.getActive()).thenReturn(expectedList);
//		Mockito.when(lotExparationMessage.send(lot, TEST_LANGUAGE)).thenReturn(true);
//		Mockito.doNothing().when(lotDao).updateStatus(expectedList);
//		// when
//		marketService.checkExparations();
//		// then
//		Assert.assertNotEquals(null, expectedList);
//		Assert.assertEquals(STATUS, lot.getStatus());
//		Mockito.verify(lotDao, Mockito.times(1)).getActive();
//		Mockito.verify(lotDao, Mockito.times(1)).updateStatus(expectedList);
//		Mockito.verify(lotExparationMessage, Mockito.times(1)).send(lot, TEST_LANGUAGE);
//	}
//
//	@Test
//	public void checkTenderExparationsTest() throws ParseException{
//		// given
//		final BidStatus STATUS = BidStatus.INACTIVE;
//		final Date DATE = getExpDate();
//
//		Tender tender = createObject(Tender.class);
//		tender.setCompanyAddress(createObject(CompanyAddress.class));
//		tender.getCompanyAddress().setCompany(createObject(Company.class));
//		tender.getCompanyAddress().getCompany().setLanguage(TEST_LANGUAGE.toString());
//		tender.setExparationDate(DATE);
//		List<Tender> expectedList = ImmutableList.of(tender);
//		// mock
//		Mockito.when(tenderDao.getActive()).thenReturn(expectedList);
//		Mockito.when(tenderExparationMessage.send(tender, TEST_LANGUAGE)).thenReturn(true);
//		Mockito.doNothing().when(tenderDao).updateStatus(expectedList);
//		// when
//		marketService.checkExparations();
//		// then
//		Assert.assertNotEquals(null, expectedList);
//		Assert.assertEquals(STATUS, tender.getStatus());
//		Mockito.verify(tenderDao, Mockito.times(1)).getActive();
//		Mockito.verify(tenderDao, Mockito.times(1)).updateStatus(expectedList);
//		Mockito.verify(tenderExparationMessage, Mockito.times(1)).send(tender, TEST_LANGUAGE);
//	}
//
//	@Test
//	public void clearPreDealNullTest(){
////		// given
////		Deal deal = createDeal();
////		deal.setLotId(null);
////
////		List<Deal> dealList = ImmutableList.of(deal);
////		// mock
////		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
////		Mockito.doNothing().when(dealDao).deleteOnApprove();
////		Mockito.doNothing().when(lotDao).closeLots();
////		Mockito.doNothing().when(tenderDao).closeTeners();
////		// when
////		marketService.clearPreDeal();
////		// then
////		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
////		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
////		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
////		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
//	}
//
//	@Test
//	public void clearPreDealSellerNotOnApproveTest(){
////		// given
////		final long LOT_ID = random.nextLong();
////		final long COMPANY_ID = random.nextLong();
////		final long TENDER_ID = random.nextLong();
////
////		Deal deal = createDeal();
////		deal.setLotId(LOT_ID);
////		deal.setTenderId(TENDER_ID);
////		deal.setSellerChoice(PartnerChoice.REJECTED);
////		deal.setBuyerChoice(PartnerChoice.ON_APPROVE);
////
////		List<Deal> dealList = ImmutableList.of(deal);
////
////		Lot lot = createObject(Lot.class);
////		lot.setId(TENDER_ID);
////		lot.setCompanyId(COMPANY_ID);
////		lot.setCompanyAddress(deal.getSellerAddress());
////
////		Tender tender = createObject(Tender.class);
////		tender.setId(TENDER_ID);
////		tender.setCompanyId(COMPANY_ID);
////		tender.setCompanyAddress(deal.getBuyerAddress());
////		// mock
////		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
////		Mockito.when(dealExparationMessageForSellerByOpponent.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(dealExparationMessageForBuyer.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
////		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
////		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
////		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
////		Mockito.doNothing().when(dealDao).deleteOnApprove();
////		Mockito.doNothing().when(lotDao).closeLots();
////		Mockito.doNothing().when(tenderDao).closeTeners();
////		// when
////		marketService.clearPreDeal();
////		// then
////		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
////		Mockito.verify(dealExparationMessageForSellerByOpponent, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(dealExparationMessageForBuyer, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
////		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
////		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
////		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
////		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
////		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
////		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
//	}
//
//	@Test
//	public void clearPreDealBuyerNotOnApproveTest(){
////		// given
////		final long LOT_ID = random.nextLong();
////		final long COMPANY_ID = random.nextLong();
////		final long TENDER_ID = random.nextLong();
////
////		Deal deal = createDeal();
////		deal.setLotId(LOT_ID);
////		deal.setTenderId(TENDER_ID);
////		deal.setBuyerChoice(PartnerChoice.REJECTED);
////		deal.setSellerChoice(PartnerChoice.ON_APPROVE);
////
////		List<Deal> dealList = ImmutableList.of(deal);
////
////		Lot lot = createObject(Lot.class);
////		lot.setId(TENDER_ID);
////		lot.setCompanyId(COMPANY_ID);
////
////		Tender tender = createObject(Tender.class);
////		tender.setId(TENDER_ID);
////		tender.setCompanyId(COMPANY_ID);
////		// mock
////		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
////		Mockito.when(dealExparationMessageForSeller.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(dealExparationMessageForBuyerByOpponent.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
////		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
////		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
////		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
////		Mockito.doNothing().when(dealDao).deleteOnApprove();
////		Mockito.doNothing().when(lotDao).closeLots();
////		Mockito.doNothing().when(tenderDao).closeTeners();
////		// when
////		marketService.clearPreDeal();
////		// then
////		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
////		Mockito.verify(dealExparationMessageForSeller, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(dealExparationMessageForBuyerByOpponent, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
////		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
////		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
////		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
////		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
////		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
////		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
//	}
//
//	@Test
//	public void clearPreDealNotOnApproveTest(){
////		// given
////		final long LOT_ID = random.nextLong();
////		final long COMPANY_ID = random.nextLong();
////		final long TENDER_ID = random.nextLong();
////
////		Deal deal = createDeal();
////		deal.setLotId(LOT_ID);
////		deal.setTenderId(TENDER_ID);
////		deal.setBuyerChoice(PartnerChoice.ON_APPROVE);
////		deal.setSellerChoice(PartnerChoice.ON_APPROVE);
////
////		List<Deal> dealList = ImmutableList.of(deal);
////
////		Lot lot = createObject(Lot.class);
////		lot.setId(TENDER_ID);
////		lot.setCompanyId(COMPANY_ID);
////
////		Tender tender = createObject(Tender.class);
////		tender.setId(TENDER_ID);
////		tender.setCompanyId(COMPANY_ID);
////		// mock
////		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
////		Mockito.when(dealExparationMessageForSeller.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(dealExparationMessageForBuyer.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
////		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
////		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
////		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
////		Mockito.doNothing().when(dealDao).deleteOnApprove();
////		Mockito.doNothing().when(lotDao).closeLots();
////		Mockito.doNothing().when(tenderDao).closeTeners();
////		// when
////		marketService.clearPreDeal();
////		// then
////		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
////		Mockito.verify(dealExparationMessageForSeller, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(dealExparationMessageForBuyer, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
////		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
////		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
////		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
////		Mockito.verify(dealDao, Mockito.times(1)).deleteOnApprove();
////		Mockito.verify(lotDao, Mockito.times(1)).closeLots();
////		Mockito.verify(tenderDao, Mockito.times(1)).closeTeners();
//	}
//
//	@Test
//	public void sendConfirmationMessagesTest(){
////		// given
////		Deal deal = createDeal();
////		List<Deal> dealList = ImmutableList.of(deal);
////		// mock
////		Mockito.when(dealDao.getOnApprove()).thenReturn(dealList);
////		Mockito.when(dealConfirmationMessageForBuyer.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		Mockito.when(dealConfirmationMessageForSeller.send(deal, TEST_LANGUAGE)).thenReturn(true);
////		// when
////		marketService.sendConfirmationMessages();
////		// then
////		Mockito.verify(dealDao, Mockito.times(1)).getOnApprove();
////		Mockito.verify(dealConfirmationMessageForBuyer, Mockito.times(1)).send(deal, TEST_LANGUAGE);
////		Mockito.verify(dealConfirmationMessageForSeller, Mockito.times(1)).send(deal, TEST_LANGUAGE);
//	}
//
////	private Deal createDeal(){
////		Deal deal = createObject(Deal.class);
////
////		deal.setBuyerAddress(createObject(CompanyAddress.class));
////		deal.getBuyerAddress().setCompany(createObject(Company.class));
////		deal.getBuyerAddress().getCompany().setLanguage(TEST_LANGUAGE);
////
////		deal.setSellerAddress(createObject(CompanyAddress.class));
////		deal.getSellerAddress().setCompany(createObject(Company.class));
////		deal.getSellerAddress().getCompany().setLanguage(TEST_LANGUAGE);
////		return deal;
////	}
//}
