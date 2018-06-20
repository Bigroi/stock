package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Proposition;

public interface PropositionDao {
	
	List<Proposition> getListPropositions() throws DaoException;

}
