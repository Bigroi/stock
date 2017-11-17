package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.jobs.trade.TradeLot;
import com.bigroi.stock.jobs.trade.TradeTender;

public interface PreDealDao {

	void add(PreDeal preDeal) throws DaoException;

	boolean deletedById(long id) throws DaoException;

	void deleteAll() throws DaoException;

	boolean update(PreDeal preDeal) throws DaoException;

	List<PreDeal> getAll() throws DaoException;

	PreDeal getById(long id) throws DaoException;
	
	void add(List<PreDeal> preDeals) throws DaoException;

	void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) throws DaoException;
		

}
