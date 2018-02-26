package com.bigroi.stock.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bigroi.stock.bean.Bid;
import com.bigroi.stock.bean.Lot;
import com.bigroi.stock.bean.Product;
import com.bigroi.stock.bean.TradeOffer;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ProductService;
import com.bigroi.stock.service.ServiceException;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	private LotDao lotDao;
	private TenderDao tenderDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void setLotDao(LotDao lotDao) {
		this.lotDao = lotDao;
	}

	public void setTenderDao(TenderDao tenderDao) {
		this.tenderDao = tenderDao;
	}
	
	@Override
	public List<Product> getAllProducts() throws ServiceException {
		try {
			return productDao.getAllProducts();
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
			product.setArchive("Y");
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
			
			if (bids.size() == 0){
				return new ArrayList<>();
			}
			
			Collections.sort(bids, (a, b) -> (int) (a.getPrice() - b.getPrice()));
			
			int size = bids.size();
			
			double minPrice = bids.subList((int)Math.round(size * 0.07), size - 1).get(0).getPrice();
			double maxPrice = bids.subList((int)Math.round(size - size * 0.07 - 1), size - 1).get(0).getPrice();
			double avgMinPrice = minPrice + (maxPrice - minPrice) / 3;
			double avgMaxPrice = minPrice + (maxPrice - minPrice) * 2 / 3;
			
			List<TradeOffer> tradeOffers = new ArrayList<>();
			
			DecimalFormat df = new DecimalFormat("# ###.##");
			tradeOffers.add(new TradeOffer(
					"< " + df.format(minPrice)
					));
			tradeOffers.add(new TradeOffer(
					df.format(minPrice) + " .. " + df.format(avgMinPrice)
					));
			tradeOffers.add(new TradeOffer(
					df.format(avgMinPrice) + " .. " + df.format(avgMaxPrice)
					));
			tradeOffers.add(new TradeOffer(
					df.format(avgMaxPrice) + " .. " + df.format(maxPrice)
					));
			tradeOffers.add(new TradeOffer(
					"> " + df.format(maxPrice)
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

}
