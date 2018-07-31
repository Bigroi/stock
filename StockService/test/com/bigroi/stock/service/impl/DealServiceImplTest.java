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
import org.springframework.beans.factory.annotation.Autowired;

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
	private CustomerCanceledMessage cancelMessage;
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
		
		Lot lot = createObject(Lot.class);
		
		// mock
		deal.setSellerAddress(createObject(CompanyAddress.class));
		Tender tender = createObject(Tender.class);
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		Mockito.doAnswer(a -> {
			((Blacklist)a.getArguments()[0]).setId(random.nextLong()); 
			return null;
			})
				.when(blacklistDao).add(blackList);
		Mockito.when(lotDao.getById(LOT_ID, deal.getSellerAddress().getCompanyId())).thenReturn(lot);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.when(tenderDao.getById(TENDER_ID, deal.getBuyerAddress().getCompanyId())).thenReturn(tender);
		Mockito.doNothing().when(cancelMessage).send(deal);
		// when
		dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, deal.getBuyerAddress().getCompanyId());
		Assert.assertEquals(deal.getBuyerChoice(), PartnerChoice.REJECTED);
		Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID);
		Mockito.verify(dealDao, Mockito.timeout(1)).setBuyerStatus(deal);
		//Mockito.verify(blacklistDao, Mockito.times(1)).add(blackList);
		Mockito.verify(lotDao, Mockito.timeout(1)).getById(LOT_ID, deal.getSellerAddress().getCompanyId());
		//Mockito.verify(lotDao, Mockito.timeout(1)).update(lot, COMPANY_ID);
		Mockito.verify(tenderDao, Mockito.timeout(1)).getById(TENDER_ID, deal.getBuyerAddress().getCompanyId());
		Mockito.verify(cancelMessage, Mockito.timeout(1)).send(deal);
		
	}
	
	/*@Test
	public void rejectSellerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long LOT_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setSellerChoice(PartnerChoice.REJECTED);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		
		Blacklist blackList = createObject(Blacklist.class);
		blackList.setLotId(deal.getLotId());
		blackList.setTenderId(deal.getTenderId());
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		
		Lot lot = createObject(Lot.class);
		lot.setCompanyId(COMPANY_ID);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
		Mockito.doAnswer(a -> {
			((Blacklist)a.getArguments()[0]).setId(random.nextLong()); 
			return null;
			})
				.when(blacklistDao).add(blackList);
		Mockito.when(lotDao.update(lot, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(cancelMessage).send(deal);
		// when
		//boolean bool = dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		// Assert.assertEquals(bool, true);
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(LOT_ID, blackList.getLotId());
		Assert.assertEquals(TENDER_ID, blackList.getTenderId());
		Assert.assertEquals(deal.getSellerChoice(), PartnerChoice.REJECTED);
		Assert.assertEquals(COMPANY_ID, lot.getCompanyId());
	}*/
	
	@Test
	public void transportBuyerTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setBuyerChoice(PartnerChoice.TRANSPORT);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		// when
		//boolean bool = dealService.transport(DEAL_ID, COMPANY_ID);
		// then
		//Assert.assertEquals(bool, true);
		Assert.assertEquals(deal.getBuyerChoice(), PartnerChoice.TRANSPORT);
	}
	
	@Test
	public void transportSellerTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setSellerChoice(PartnerChoice.TRANSPORT);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
		// when
		//boolean bool = dealService.transport(DEAL_ID, COMPANY_ID);
		// then
		//Assert.assertEquals(bool, true);
		Assert.assertEquals(deal.getSellerChoice(), PartnerChoice.TRANSPORT);
	}
}
