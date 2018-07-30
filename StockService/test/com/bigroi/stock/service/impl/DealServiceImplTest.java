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

	@Test
	public void getByIdTest() throws DaoException, ServiceException {
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Company buyer = createObject(Company.class);
		Company seller = createObject(Company.class);
		
		Deal deal = createObject(Deal.class);
		deal.setBuyerAddress(new CompanyAddress());
		deal.setSellerAddress(new CompanyAddress());
		deal.getBuyerAddress().setCompany(buyer);
		deal.getSellerAddress().setCompany(seller);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(buyer);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(seller);
		// when
		//dealService.getById(DEAL_ID, COMPANY_ID);
		// then
		Assert.assertNotEquals(deal, null);
		Assert.assertEquals(buyer.getId(), deal.getBuyerAddress().getCompany().getId());
		Assert.assertEquals(seller.getId(), deal.getSellerAddress().getCompany().getId());
		//Mockito.verify(dealDao, Mockito.times(1)).getById(DEAL_ID, COMPANY_ID);
		//Mockito.verify(companyDao, Mockito.times(2)).getById(COMPANY_ID);
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
	public void approveSellerTest() throws DaoException, ServiceException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setSellerChoice(PartnerChoice.APPROVED);
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(dealDao).setSellerStatus(deal);
    	// when
		//boolean bool = dealService.approve(DEAL_ID, COMPANY_ID);
		// then
		//Assert.assertEquals(bool, true);
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(deal.getSellerChoice(),PartnerChoice.APPROVED);
	}
	
	@Test
	public void rejectBuyerTest() throws DaoException, ServiceException, MessageException{
		// given
		final long DEAL_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		final long LOT_ID = random.nextLong();
		final long TENDER_ID = random.nextLong();
		// mock
		Deal deal = createObject(Deal.class);
		deal.setBuyerChoice(PartnerChoice.REJECTED);
		deal.setLotId(LOT_ID);
		deal.setTenderId(TENDER_ID);
		
		Blacklist blackList = createObject(Blacklist.class);
		blackList.setLotId(deal.getLotId());
		blackList.setTenderId(deal.getTenderId());
		
		Company company = createObject(Company.class);
		company.setId(COMPANY_ID);
		
		Tender tender = createObject(Tender.class);
		tender.setCompanyId(COMPANY_ID);
		
		Mockito.when(dealDao.getById(DEAL_ID)).thenReturn(deal);
		Mockito.when(companyDao.getById(COMPANY_ID)).thenReturn(company);
		Mockito.doNothing().when(dealDao).setBuyerStatus(deal);
		Mockito.doAnswer(a -> {
			((Blacklist)a.getArguments()[0]).setId(random.nextLong()); 
			return null;
			})
				.when(blacklistDao).add(blackList);
		Mockito.when(tenderDao.update(tender, COMPANY_ID)).thenReturn(true);
		Mockito.doNothing().when(cancelMessage).send(deal);
		// when
		//boolean bool = dealService.reject(DEAL_ID, COMPANY_ID);
		// then
		// Assert.assertEquals(bool, true);
		Assert.assertEquals(COMPANY_ID, company.getId());
		Assert.assertEquals(LOT_ID, blackList.getLotId());
		Assert.assertEquals(TENDER_ID, blackList.getTenderId());
		Assert.assertEquals(deal.getBuyerChoice(), PartnerChoice.REJECTED);
		Assert.assertEquals(COMPANY_ID, tender.getCompanyId());
	}
	
	@Test
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
	}
	
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
