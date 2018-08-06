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

import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Blacklist;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.deal.CustomerCanceledMessage;
import com.bigroi.stock.messager.message.deal.SellerCanceledMessage;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForCustomer;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForSeller;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceImplTest extends BaseTest {
	
	@InjectMocks
	private DealServiceImpl dealService;
	@Mock
	private DealDao dealDao;
	@Mock
	private CompanyDao companyDao;
	@Mock
	private BlacklistDao blacklistDao;
	@Mock
	private CustomerCanceledMessage buyerCancelMessage;
	@Mock
	private SellerCanceledMessage sellerCanceledMessage;
	@Mock
	private TenderDao tenderDao;
	@Mock
	private LotDao lotDao;
	@Mock
	private SuccessDealMessageForCustomer successDealMessageForCustomer;
	@Mock
	private SuccessDealMessageForSeller successDealMessageForSeller;

	@Test
	public void getByIdTest() throws DaoException, ServiceException {
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		Company buyer = createObject(Company.class);
		
		Company seller = createObject(Company.class);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(deal.getBuyerAddress().getCompanyId())).thenReturn(buyer);
		Mockito.when(companyDao.getById(deal.getSellerAddress().getCompanyId())).thenReturn(seller);
		// when
		dealService.getById(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertNotEquals(deal, null);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(companyDao, Mockito.times(1)).getById(deal.getBuyerAddress().getCompanyId());
	}
	
	@Test
	public void getByUserIdTest() throws ServiceException, DaoException{
		// given
		final long COMPANY_ID = random.nextLong();
		// mock
		List<Deal> expectedList = new ArrayList<>();
		Mockito.when(dealDao.getByCompanyId(COMPANY_ID)).thenReturn(expectedList);
		// when
		List<Deal> actualList = dealService.getByUserId(COMPANY_ID);
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(dealDao, Mockito.times(1)).getByCompanyId(COMPANY_ID);
	}
	
	@Test
	public void getListBySellerAndBuyerApprovedTest() throws DaoException, ServiceException{
		// mock
		List<Deal> expectedList = new ArrayList<>();
		Mockito.when(dealDao.getListBySellerAndBuyerApproved()).thenReturn(expectedList);
		// when
		List<Deal> actualList = dealService.getListBySellerAndBuyerApproved();
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(dealDao, Mockito.times(1)).getListBySellerAndBuyerApproved();
	}
	
	@Test
	public void approveNotAuthorizedTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
    	// when
		boolean bool = dealService.approve(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(bool, false);
		Mockito.verify(dealDao, Mockito.times(0)).setBuyerStatus(Mockito.any());
		Mockito.verify(dealDao, Mockito.times(0)).setSellerStatus(Mockito.any());
	}
	
	@Test
	public void approveSellerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);
		
		Deal deal = createObject(Deal.class);
		deal.setSellerAddress(companyAddress);
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		// mock
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setBuyerChoice(PartnerChoice.APPROVED);
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
		Mockito.doNothing().when(successDealMessageForCustomer).send(deal);
		Mockito.doNothing().when(successDealMessageForSeller).send(deal);
    	// when
		dealService.approve(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(deal.getSellerAddress().getCompanyId(), company.getId());
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.times(1)).setSellerStatus(deal);
	}
	
	@Test
	public void approveBuyerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(companyAddress);
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		// mock
		deal.setSellerAddress(createObject(CompanyAddress.class));
		deal.setSellerChoice(PartnerChoice.APPROVED);
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
		Mockito.doNothing().when(successDealMessageForCustomer).send(deal);
		Mockito.doNothing().when(successDealMessageForSeller).send(deal);
    	// when
		dealService.approve(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(deal.getBuyerAddress().getCompanyId(), company.getId());
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.times(1)).setBuyerStatus(deal);
	}
	
	@Test
	public void calculateStatusTest() throws MessageException, ServiceException, DaoException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		deal.setBuyerChoice(PartnerChoice.APPROVED);
		deal.setSellerChoice(PartnerChoice.APPROVED);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(successDealMessageForCustomer).send(deal);
		Mockito.doNothing().when(successDealMessageForSeller).send(deal);
		// when
		dealService.approve(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(PartnerChoice.APPROVED, deal.getBuyerChoice());
		Assert.assertEquals(PartnerChoice.APPROVED, deal.getSellerChoice());
	}
	
	@Test
	public void rejectBuyerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long LOT_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		
		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(companyAddress);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		
		Blacklist blackList = createObject(Blacklist.class);
		blackList.setLotId(LOT_ID);
		blackList.setTenderId(TENDER_ID);
		
		Tender tender = createObject(Tender.class);
		tender.setId(TENDER_ID);
		tender.setCompanyId(COMPANY_ID);
		
		// mock
		deal.setSellerAddress(createObject(CompanyAddress.class));
		Lot lot = createObject(Lot.class);
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		Mockito.doAnswer(a -> {
			((Blacklist)a.getArguments()[0]).setId(random.nextLong()); 
			return null;
			})
				.when(blacklistDao).add(blackList);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.when(tenderDao.update(tender,COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(buyerCancelMessage).send(deal);
		// when
		dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, deal.getBuyerAddress().getCompanyId());
		Assert.assertEquals(deal.getBuyerChoice(), PartnerChoice.REJECTED);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.timeout(1)).setBuyerStatus(deal);
		Mockito.verify(blacklistDao, Mockito.times(1)).add(Mockito.any());
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(tenderDao, Mockito.timeout(1)).update(tender,COMPANY_ID);
		Mockito.verify(buyerCancelMessage, Mockito.timeout(1)).send(deal);
		
	}
	
	@Test
	public void rejectSellerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long LOT_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();

		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);

		Deal deal = createObject(Deal.class);
		deal.setSellerAddress(companyAddress);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);

		Blacklist blackList = createObject(Blacklist.class);
		blackList.setLotId(LOT_ID);
		blackList.setTenderId(TENDER_ID);

		Lot lot = createObject(Lot.class);
		lot.setId(TENDER_ID);
		lot.setCompanyId(COMPANY_ID);

		// mock
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		Tender tender = createObject(Tender.class);
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		Mockito.doAnswer(a -> {
			((Blacklist) a.getArguments()[0]).setId(random.nextLong());
			return null;
		}).when(blacklistDao).add(blackList);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(sellerCanceledMessage).send(deal);
		// when
		dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, deal.getSellerAddress().getCompanyId());
		Assert.assertEquals(deal.getSellerChoice(), PartnerChoice.REJECTED);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.timeout(1)).setSellerStatus(deal);
		//Mockito.verify(blacklistDao, Mockito.times(1)).add(blackList); //different arguments...
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
		Mockito.verify(sellerCanceledMessage, Mockito.timeout(1)).send(deal);
	}
	
	@Test
	public void rejectNotAuthorizedTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		// mock
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		// when
		boolean bool = dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(bool, false);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
	}
	
	@Test
	public void transportBuyerTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(companyAddress);
		deal.setSellerAddress(createObject(CompanyAddress.class));
		
		// mock
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		// when
		dealService.transport(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(deal.getBuyerChoice(), PartnerChoice.TRANSPORT);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.times(1)).setBuyerStatus(deal);
	}
	
	@Test
	public void transportSellerTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		
		CompanyAddress companyAddress = createObject(CompanyAddress.class);
		companyAddress.setCompanyId(COMPANY_ID);
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(companyAddress);
		// mock
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
		// when
		dealService.transport(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(deal.getSellerChoice(), PartnerChoice.TRANSPORT);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.times(1)).setSellerStatus(deal);
	}
	
	@Test
	public void transportNotAuthorizedTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();

		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(createObject(CompanyAddress.class));
		deal.setSellerAddress(createObject(CompanyAddress.class));
		// mock
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		// when
		boolean bool = dealService.transport(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(bool, false);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
	}
}
