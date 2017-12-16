package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.Deal;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public interface DealDao {
	
    void add(Deal deal) throws DaoException;

	void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws DaoException;

	Deal getById(long id) throws DaoException;

	List<Deal> getOnApprove() throws DaoException;

	void deleteOnApprove() throws DaoException;

	void add(List<Deal> deals) throws DaoException;

}