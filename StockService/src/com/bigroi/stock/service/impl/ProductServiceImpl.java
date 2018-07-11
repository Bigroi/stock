package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.bean.ui.TradeOffer;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

@Repository
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	
	@Override
	public List<Product> getAllProducts() throws ServiceException {
		try {
			return productDao.getAllProducts();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<ProductForUI> getAllActiveProductsForUI() throws ServiceException {
		try {
			return productDao.getAllActiveProductsForUI();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Product> getAllActiveProducts() throws ServiceException {
		try {
			return productDao.getAllActiveProducts();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Product getProductById(long id) throws ServiceException{
		try {
			Product product;
			if (id == -1) {
				product = new Product();
				product.setId(-1);
			} else {
				product = productDao.getById(id);
			}
			return product;
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	@Override
	public void merge(Product product) throws ServiceException{
		try {
			if (product.getId() == -1) {
				productDao.add(product);
			} else {
				productDao.update(product);
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
	@Override
	@Transactional
	public void delete(long id) throws ServiceException {
		try {
			Product product = productDao.getById(id);
			product.setRemoved("Y");
			productDao.update(product);
			lotDao.setStatusByProductId(id, BidStatus.INACTIVE);
			tenderDao.setStatusByProductId(id, BidStatus.INACTIVE);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<TradeOffer> getTradeOffers(long productId) throws ServiceException{
		try{
			List<Bid> bids = new ArrayList<>();
			bids.addAll(lotDao.getActiveByProductId(productId));
			bids.addAll(tenderDao.getActiveByProductId(productId));
			
			Collections.sort(bids, (a, b) -> ((int)(a.getPrice() - b.getPrice() * 100)));
			int size = bids.size();
			
			
			if (size <= 5){
				return getTrageOffersForSmallBids(bids);
			}
			
			double minPrice = bids.subList((int)Math.round(size * 0.07), size - 1).get(0).getPrice();
			double maxPrice = bids.subList((int)(size - size * 0.07 - 1), size - 1).get(0).getPrice();
			double avgMinPrice = minPrice + (maxPrice - minPrice) / 3;
			double avgMaxPrice = minPrice + (maxPrice - minPrice) * 2 / 3;
			
			List<TradeOffer> tradeOffers = new ArrayList<>();
			
			
			tradeOffers.add(new TradeOffer(
					"< " + ProductForUI.DECIMAL_FORMAT.format(minPrice)
					));
			tradeOffers.add(new TradeOffer(
					ProductForUI.DECIMAL_FORMAT.format(minPrice) + " .. " + ProductForUI.DECIMAL_FORMAT.format(avgMinPrice)
					));
			tradeOffers.add(new TradeOffer(
					ProductForUI.DECIMAL_FORMAT.format(avgMinPrice) + " .. " + ProductForUI.DECIMAL_FORMAT.format(avgMaxPrice)
					));
			tradeOffers.add(new TradeOffer(
					ProductForUI.DECIMAL_FORMAT.format(avgMaxPrice) + " .. " + ProductForUI.DECIMAL_FORMAT.format(maxPrice)
					));
			tradeOffers.add(new TradeOffer(
					"> " + ProductForUI.DECIMAL_FORMAT.format(maxPrice)
					));
			
			for (Bid bid : bids){
				TradeOffer currentOffer;
				if (bid.getPrice() < minPrice){
					currentOffer = tradeOffers.get(0);
				} else if (bid.getPrice() < avgMinPrice){
					currentOffer = tradeOffers.get(1);
				} else if (bid.getPrice() < avgMaxPrice){
					currentOffer = tradeOffers.get(2);
				} else if (bid.getPrice() <= maxPrice){
					currentOffer = tradeOffers.get(3);
				} else {
					currentOffer = tradeOffers.get(4);
				}
				
				if (bid instanceof Lot){
					currentOffer.setLotVolume(currentOffer.getLotVolume() + bid.getMaxVolume());
				} else {
					currentOffer.setTenderVolume(currentOffer.getTenderVolume() + bid.getMaxVolume());
				}
			}
			
			return tradeOffers;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<TradeOffer> getTrageOffersForSmallBids(List<Bid> bids){
		List<TradeOffer> list = new ArrayList<>();
		for (Bid bid : bids){
			TradeOffer offer = new TradeOffer(ProductForUI.DECIMAL_FORMAT.format(bid.getPrice()));
			if (bid instanceof Lot){
				offer.setLotVolume(bid.getMaxVolume());
			} else {
				offer.setTenderVolume(bid.getMaxVolume());
			}
			list.add(offer);
		}
		return list;
	}

}
