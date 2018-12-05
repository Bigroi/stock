package com.bigroi.stock.service.impl;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.db.TradeTender;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.TenderDao;
import com.bigroi.stock.service.TenderService;

@Repository
public class TenderServiceImpl extends BidBaseService<Tender, TradeTender> implements TenderService{
	
	@Autowired
	private TenderDao tenderDao;

	@Override
	protected Supplier<Tender> getConstructor() {
		return Tender::new;
	}

	@Override
	protected BidDao<Tender, TradeTender> getBidDao() {
		return tenderDao;
	}
}
