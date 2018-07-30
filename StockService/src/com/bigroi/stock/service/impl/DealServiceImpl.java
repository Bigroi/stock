package com.bigroi.stock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.DealStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Blacklist;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.BlacklistDao;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.messager.message.Message;
import com.bigroi.stock.messager.message.MessageException;
import com.bigroi.stock.messager.message.deal.CustomerCanceledMessage;
import com.bigroi.stock.messager.message.deal.SellerCanceledMessage;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForCustomer;
import com.bigroi.stock.messager.message.deal.SuccessDealMessageForSeller;
import com.bigroi.stock.service.DealService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class DealServiceImpl implements DealService{
	
	@Autowired
	private BlacklistDao blacklistDao;
	@Autowired
	private DealDao dealDao;
	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SellerCanceledMessage sellerCanceledMessage;
	@Autowired
	private CustomerCanceledMessage customerCanceledMessage;
	@Autowired
	private SuccessDealMessageForCustomer successDealMessageForCustomer;
	@Autowired
	private SuccessDealMessageForSeller successDealMessageForSeller;
	
	@Override
	public Deal getById(long id, long companyId) throws ServiceException {
		try {
			Deal deal = dealDao.getById(id);
			if (deal != null){
				Company seller = companyDao.getById(deal.getSellerAddress().getCompanyId());
				deal.getSellerAddress().setCompany(seller);
				
				Company buyer = companyDao.getById(deal.getBuyerAddress().getCompanyId());
				deal.getBuyerAddress().setCompany(buyer);
			}
			return deal;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Deal> getByUserId(long companyId) throws ServiceException {
		try {
			return dealDao.getByCompanyId(companyId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public boolean reject(long id, long companyId) throws ServiceException {
		try{
			Message<Deal> message;
			Deal deal = dealDao.getById(id);
			long buyerId = deal.getBuyerAddress().getCompanyId();
			long sellerId = deal.getSellerAddress().getCompanyId();
			if (buyerId == companyId){
				deal.setBuyerChoice(PartnerChoice.REJECTED);
				dealDao.setBuyerStatus(deal);
				message = customerCanceledMessage;
			} else if (sellerId == companyId){
				deal.setSellerChoice(PartnerChoice.REJECTED);
				dealDao.setSellerStatus(deal);
				message = sellerCanceledMessage;
			} else {
				return false;
			}
			addBlackList(deal.getLotId(), deal.getTenderId());
			setVolumeBack(deal);
			message.send(deal);
			return true;
		}catch(DaoException | MessageException e){
			throw new ServiceException(e);
		}
	}

	private void setVolumeBack(Deal deal) throws DaoException {
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
	public boolean approve(long id, long companyId) throws ServiceException {
		try{
			Deal deal = dealDao.getById(id);
			long buyerId = deal.getBuyerAddress().getCompanyId();
			long sellerId = deal.getSellerAddress().getCompanyId();
			if (buyerId == companyId){
				deal.setBuyerChoice(PartnerChoice.APPROVED);
				dealDao.setBuyerStatus(deal);
			} else if (sellerId == companyId){
				deal.setSellerChoice(PartnerChoice.APPROVED);
				dealDao.setSellerStatus(deal);
			} else {
				return false;
			}
			if (DealStatus.calculateStatus(deal.getBuyerChoice(), deal.getSellerChoice()) == DealStatus.APPROVED){
				successDealMessageForCustomer.send(deal);
				successDealMessageForSeller.send(deal);
			}
			
			return true;
		}catch(DaoException | MessageException e){
			throw new ServiceException(e);
		}
	}

	private void addBlackList(long lotId, long tenderId) throws DaoException {
		Blacklist blackList = new Blacklist();
		blackList.setLotId(lotId);
		blackList.setTenderId(tenderId);
		blacklistDao.add(blackList);
	}

	@Override
	public boolean transport(long id, long companyId) throws ServiceException {
		try{
			Deal deal = dealDao.getById(id);
			long buyerId = deal.getBuyerAddress().getCompanyId();
			long sellerId = deal.getSellerAddress().getCompanyId();
			
			if (buyerId == companyId){
				deal.setBuyerChoice(PartnerChoice.TRANSPORT);
				dealDao.setBuyerStatus(deal);
			} else if (sellerId == companyId){
				deal.setSellerChoice(PartnerChoice.TRANSPORT);
				dealDao.setSellerStatus(deal);
			} else {
				return false;
			}
			return true;
		}catch(DaoException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Deal> getListBySellerAndBuyerApproved() throws ServiceException {
		try {
			return dealDao.getListBySellerAndBuyerApproved();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
