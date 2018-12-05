package com.bigroi.stock.service.impl;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.dao.BidDao;
import com.bigroi.stock.dao.LotDao;
import com.bigroi.stock.service.LotService;

@Repository
public class LotServiceImpl extends BidBaseService<Lot, TradeLot> implements LotService {

	@Autowired
	private LotDao lotDao;

	@Override
	protected Supplier<Lot> getConstructor() {
		return Lot::new;
	}

	@Override
	protected BidDao<Lot, TradeLot> getBidDao() {
		return lotDao;
	}
	
}
