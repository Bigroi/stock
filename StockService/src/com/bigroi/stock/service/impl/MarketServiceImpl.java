package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
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
import com.bigroi.stock.service.MarketService;

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
	public void checkExparations() {
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
	}

	@Override
	@Transactional
	public void clearPreDeal(){
		List<Deal> deals = dealDao.getOnApprove();
		for (Deal deal : deals) {
			if (deal.getLotId() == null){
				continue;
			}
			if (deal.getSellerChoice() != PartnerChoice.ON_APPROVE){
				dealExparationMessageForSellerByOpponent.send(deal);
				
				dealExparationMessageForCustomer.send(deal);
			} else if (deal.getBuyerChoice() != PartnerChoice.ON_APPROVE){
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
	}
	
	private void returnVolumeToBids(Deal deal){
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
	public void sendConfirmationMessages(){
		List<Deal> deals = dealDao.getOnApprove();
		for (Deal deal : deals) {
			dealConfirmationMessageForCustomer.send(deal);
			
			dealConfirmationMessageForSeller.send(deal);
		}
	}
}
