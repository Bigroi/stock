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
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForBuyer;
import com.bigroi.stock.messager.message.deal.DealConfirmationMessageForSeller;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForBuyer;
import com.bigroi.stock.messager.message.deal.DealExparationMessageForSeller;
import com.bigroi.stock.service.MarketService;
import com.bigroi.stock.util.LabelUtil;

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
	private DealConfirmationMessageForBuyer dealConfirmationMessageForBuyer;
	@Autowired
	private DealConfirmationMessageForSeller dealConfirmationMessageForSeller;
	@Autowired
	@Qualifier("dealExparationMessageForSellerByOpponent")
	private DealExparationMessageForSeller dealExparationMessageForSellerByOpponent;
	@Autowired
	@Qualifier("dealExparationMessageForBuyer")
	private DealExparationMessageForBuyer dealExparationMessageForBuyer;
	@Autowired
	@Qualifier("dealExparationMessageForSeller")
	private DealExparationMessageForSeller dealExparationMessageForSeller;
	@Autowired
	@Qualifier("dealExparationMessageForBuyerByOpponent")
	private DealExparationMessageForBuyer dealExparationMessageForBuyerByOpponent;
	
	@Override
	@Transactional
	public void checkExparations() {
		List<Lot> lots = lotDao.getActive();
		for (Lot lot : lots) {
			if (lot.isExpired()){
				lot.setStatus(BidStatus.INACTIVE);
				lotExparationMessage.send(lot, LabelUtil.parseString(lot.getCompanyAddress().getCompany().getLanguage()));
			}
		}
		lotDao.updateStatus(lots);
		
		List<Tender> tenders = tenderDao.getActive();
		for (Tender tender : tenders) {
			if (tender.isExpired()){
				tender.setStatus(BidStatus.INACTIVE);
				tenderExparationMessage.send(tender, LabelUtil.parseString(tender.getCompanyAddress().getCompany().getLanguage()));
			}
		}
		tenderDao.updateStatus(tenders);
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
				dealExparationMessageForSellerByOpponent.send(deal, LabelUtil.parseString(deal.getSellerLanguage()));
				
				dealExparationMessageForBuyer.send(deal, LabelUtil.parseString(deal.getBuyerLanguage()));
			} else if (deal.getBuyerChoice() != PartnerChoice.ON_APPROVE){
				dealExparationMessageForSeller.send(deal, LabelUtil.parseString(deal.getSellerLanguage()));
				
				dealExparationMessageForBuyerByOpponent.send(deal, LabelUtil.parseString(deal.getBuyerLanguage()));
			} else {
				dealExparationMessageForSeller.send(deal, LabelUtil.parseString(deal.getSellerLanguage()));
				
				dealExparationMessageForBuyer.send(deal, LabelUtil.parseString(deal.getBuyerLanguage()));
			}
			returnVolumeToBids(deal);
		}
		dealDao.deleteOnApprove();
		lotDao.close();
		tenderDao.close();
	}
	
	private void returnVolumeToBids(Deal deal){
		long buyerId = deal.getBuyerCompanyId();
		long sellerId = deal.getSellerCompanyId();
		
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
			dealConfirmationMessageForBuyer.send(deal, LabelUtil.parseString(deal.getBuyerLanguage()));
			
			dealConfirmationMessageForSeller.send(deal, LabelUtil.parseString(deal.getSellerLanguage()));
		}
	}
}
