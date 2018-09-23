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
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.ui.ProductForUI;
import com.bigroi.stock.bean.ui.TradeOffer;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.dao.ProductDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.ProductService;

@Repository
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private LotDao lotDao;
	@Autowired
	private TenderDao tenderDao;
	
	@Override
	public List<Product> getAllProducts() {
		return productDao.getAllProducts();
	}
	
	@Override
	public List<ProductForUI> getAllActiveProductsForUI() {
		return productDao.getAllActiveProductsForUI();
	}
	
	@Override
	public List<Product> getAllActiveProducts() {
		return productDao.getAllActiveProducts();
	}

	@Override
	public Product getProductById(long id){
		Product product;
		if (id == -1) {
			product = new Product();
			product.setId(-1);
		} else {
			product = productDao.getById(id);
		}
		return product;
	}
	
	@Override
	public void merge(Product product){
		if (product.getId() == -1) {
			productDao.add(product);
		} else {
			productDao.update(product);
		}
	}
	
	@Override
	@Transactional
	public void delete(long id) {
		Product product = productDao.getById(id);
		product.setRemoved("Y");
		productDao.update(product);
		lotDao.setStatusByProductId(id, BidStatus.INACTIVE);
		tenderDao.setStatusByProductId(id, BidStatus.INACTIVE);
	}
	
	public List<TradeOffer> getTradeOffers(long productId){
		List<Bid> bids = new ArrayList<>();
		bids.addAll(lotDao.getActiveByProductId(productId));
		bids.addAll(tenderDao.getActiveByProductId(productId));
		
		Collections.sort(bids, (a, b) -> (int)((a.getPrice() - b.getPrice()) * 100));
		int size = bids.size();
		
		
		if (size <= 5){
			return getTrageOffersForSmallBids(bids);
		} else {
			return getTradeOffersForBigBids(bids);
		}
		
		
	}
	
	private List<TradeOffer> getTradeOffersForBigBids(List<Bid> bids) {
		double totalMonye = 0;
		int totalVolume = 0;
		double minPrice = Integer.MAX_VALUE;
		for (Bid bid : bids){
			totalMonye += bid.getPrice() * bid.getMaxVolume();
			totalVolume += bid.getMaxVolume();
			if (minPrice > bid.getPrice()){
				minPrice = bid.getPrice();
			}
		}
		
		double avgPice = totalMonye / totalVolume;
		
		double point1 = minPrice + (avgPice - minPrice) * 0.3;
		double point2 = minPrice + (avgPice - minPrice) * 0.7;
		double point3 = minPrice + (avgPice - minPrice) * 1.3;
		double point4 = minPrice + (avgPice - minPrice) * 1.7;
		
		List<TradeOffer> tradeOffers = new ArrayList<>();
		
		//< point1
		TradeOffer tradeOffer = new TradeOffer("< " + ProductForUI.DECIMAL_FORMAT.format(point1));
		tradeOffers.add(tradeOffer);
		tradeOffer.setLotVolume(bids.stream()
				.filter(b -> b instanceof Lot)
				.filter(b -> b.getPrice() < point1)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		tradeOffer.setTenderVolume(bids.stream()
				.filter(b -> b instanceof Tender)
				.filter(b -> b.getPrice() <point1)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		//point1 .. point2
		tradeOffer = new TradeOffer(
				ProductForUI.DECIMAL_FORMAT.format(point1) + 
				" .. " +
				ProductForUI.DECIMAL_FORMAT.format(point2));
		tradeOffers.add(tradeOffer);
		tradeOffer.setLotVolume(bids.stream()
				.filter(b -> b instanceof Lot)
				.filter(b -> b.getPrice() >= point1 && b.getPrice() < point2)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		tradeOffer.setTenderVolume(bids.stream()
				.filter(b -> b instanceof Tender)
				.filter(b -> b.getPrice() >= point1 && b.getPrice() < point2)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		//point2 .. point3
		tradeOffer = new TradeOffer(
				ProductForUI.DECIMAL_FORMAT.format(point2) + 
				" .. " +
				ProductForUI.DECIMAL_FORMAT.format(point3));
		tradeOffers.add(tradeOffer);
		tradeOffer.setLotVolume(bids.stream()
				.filter(b -> b instanceof Lot)
				.filter(b -> b.getPrice() >= point2 && b.getPrice() < point3)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		tradeOffer.setTenderVolume(bids.stream()
				.filter(b -> b instanceof Tender)
				.filter(b -> b.getPrice() >= point2 && b.getPrice() < point3)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		// point3 .. point4
		tradeOffer = new TradeOffer(
				ProductForUI.DECIMAL_FORMAT.format(point3) + 
				" .. " +
				ProductForUI.DECIMAL_FORMAT.format(point4));
		tradeOffers.add(tradeOffer);
		tradeOffer.setLotVolume(bids.stream()
				.filter(b -> b instanceof Lot)
				.filter(b -> b.getPrice() >= point3 && b.getPrice() < point4)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		tradeOffer.setTenderVolume(bids.stream()
				.filter(b -> b instanceof Tender)
				.filter(b -> b.getPrice() >= point4 && b.getPrice() < point4)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		//> point4
		tradeOffer = new TradeOffer(
				"> " +
				ProductForUI.DECIMAL_FORMAT.format(point4));
		tradeOffers.add(tradeOffer);
		tradeOffer.setLotVolume(bids.stream()
				.filter(b -> b instanceof Lot)
				.filter(b -> b.getPrice() >= point4)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		tradeOffer.setTenderVolume(bids.stream()
				.filter(b -> b instanceof Tender)
				.filter(b -> b.getPrice() >= point4)
				.map(Bid::getMaxVolume)
				.reduce(Integer::sum)
				.orElse(0));
		return tradeOffers;
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
