package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.DealConfirmationMessageForCustomer;
import com.bigroi.stock.messager.message.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.DealExparationMessageForCustomer;
import com.bigroi.stock.messager.message.DealExparationMessageForSeller;
import com.bigroi.stock.messager.message.LotExparationMessage;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.TenderExparationMessage;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class MarketServiceImpl implements MarketService {

	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private DealDao dealDao;
	
	@Autowired
	private LotExparationMessage lotExparationMessage;
	@Autowired
	private TenderExparationMessage tenderExparationMessage;
	@Autowired
	private DealConfirmationMessageForCustomer dealConfirmationMessageForCustomer;
	@Autowired
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	@Autowired
	@Qualifier("dealExparationMessageForSellerByOpponent")
	private DealExparationMessageForSeller dealExparationMessageForSellerByOpponent;
	@Autowired
	@Qualifier("dealExparationMessageForCustomer")
	private DealExparationMessageForCustomer dealExparationMessageForCustomer;
	@Autowired
	@Qualifier("dealExparationMessageForSeller")
	private DealExparationMessageForSeller dealExparationMessageForSeller;
	@Autowired
	@Qualifier("dealExparationMessageForCustomerByOpponent")
	private DealExparationMessageForCustomer dealExparationMessageForCustomerByOpponent;
	
	@Override
	@Transactional
	public void checkExparations() throws ServiceException{
		try {
			List<Lot> lots = lotDao.getActive();
			for (Lot lot : lots) {
				if (lot.isExpired()){
					lot.setStatus(BidStatus.INACTIVE);
					lotExparationMessage.send(lot);
				}
			}
			lotDao.update(lots);
			
			List<Tender> tenders = tenderDao.getActive();
			for (Tender tender : tenders) {
				if (tender.isExpired()){
					tender.setStatus(BidStatus.INACTIVE);
					tenderExparationMessage.send(tender);
				}
			}
			tenderDao.update(tenders);
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void clearPreDeal() throws ServiceException {
		try {
			List<Deal> deals = dealDao.getOnApprove();
			for (Deal deal : deals) {
				if (deal.getLotId() == null){
					continue;
				}
				if (deal.getSellerApproved() != null){
					dealExparationMessageForSellerByOpponent.send(deal);
					
					dealExparationMessageForCustomer.send(deal);
				} else if (deal.getBuyerApproved() != null){
					dealExparationMessageForSeller.send(deal);
					
					dealExparationMessageForCustomerByOpponent.send(deal);
				} else {
					dealExparationMessageForSeller.send(deal);
					
					dealExparationMessageForCustomer.send(deal);
				}
				returnVolumeToBids(deal);
			}
			dealDao.deleteOnApprove();
			lotDao.closeLots();
			tenderDao.closeTeners();
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
	
	private void returnVolumeToBids(Deal deal) throws DaoException {
		long buyerId = deal.getBuyerAddress().getCompanyId();
		long sellerId = deal.getSellerAddress().getCompanyId();
		
		Lot lot = lotDao.getById(deal.getLotId(), sellerId);
		lot.setMaxVolume(lot.getMaxVolume() + deal.getVolume());
		lotDao.update(lot, lot.getCompanyId());
		
		Tender tender = tenderDao.getById(deal.getTenderId(), buyerId);
		tender.setMaxVolume(tender.getMaxVolume() + deal.getVolume());
		tenderDao.update(tender, tender.getCompanyId());
	}

	@Override
	@Transactional
	public void sendConfirmationMessages() throws ServiceException {
		try {
			List<Deal> deals = dealDao.getOnApprove();
			for (Deal deal : deals) {
				dealConfirmationMessageForCustomer.send(deal);
				
				dealConfirmationMessageForSeller.send(deal);
			}
		} catch (DaoException | MessageException e) {
			throw new ServiceException(e);
		}
	}
}
