package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PreDeal;

public class PreDealDaoImpl implements PreDeal{
	
	//TODO   in developing
	
	private static final String ADD_PREDEALS_BY_ID = "";

	private static final String DELETE_PREDEALS_BY_ID = "";
	
	private static final String DELETE_ALL_PREDEALS_BY_ID = "";

	private static final String UPDATE_PREDEALS_BY_ID = "";
	
	private static final String GET_ALL_PREDEALS_BY_ID = "";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(PreDeal preDeal) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long id, PreDeal preDeal) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PreDeal> getAllPreDeal() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
