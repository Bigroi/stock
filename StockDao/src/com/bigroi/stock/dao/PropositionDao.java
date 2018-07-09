package com.bigroi.stock.dao;

import java.util.List;

import com.bigroi.stock.bean.db.Proposition;

public interface PropositionDao {
	
	List<Proposition> getListPropositions() throws DaoException;
	
    boolean deleteProposition(long id, long companyId) throws DaoException;
    
    List<Proposition> getListPropositionsByStatusAndUserId(long userId) throws DaoException;
    
    List<Proposition> getListPropositionsTrans() throws DaoException;

}
