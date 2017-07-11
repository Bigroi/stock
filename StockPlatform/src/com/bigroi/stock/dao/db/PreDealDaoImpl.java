package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.PreDeal;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PreDealDao;


public class PreDealDaoImpl implements PreDealDao {
	
	private static final String ADD_PREDEALS_BY_ID = "INSERT INTO predeal "
			+ "(id, sellerHashCode, customerHashCode, tender_Id, lot_Id, "
			+ "sellerApprov, custApprov, dealDate) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String DELETE_PREDEALS_BY_ID = "DELETE FROM predeal "
			+ "WHERE id = ?";
	
	private static final String DELETE_ALL_PREDEALS_BY_ID = "DELETE  FROM predeal ";

	private static final String UPDATE_PREDEALS_BY_ID = "UPDATE predeal SET "
			+ "sellerHashCode =?, customerHashCode =?, tender_Id =?, lot_Id =?, "
			+ "sellerApprov =?, custApprov =?, dealDate =? WHERE id = ?";
			
	
	private static final String GET_ALL_PREDEALS_BY_ID = "SELECT id, sellerHashCode, "
			+ "customerHashCode, tender_Id, lot_Id, sellerApprov, custApprov, "
			+ "dealDate FROM predeal ";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(PreDeal preDeal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_PREDEALS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, preDeal.getId());
				ps.setString(2, preDeal.getSellerHashCode());
				ps.setString(3, preDeal.getCustomerHashCode());
				ps.setLong(4, preDeal.getTenderId());
				ps.setLong(5, preDeal.getLotId());
				ps.setString(6, preDeal.getSellerApp());
				ps.setString(7, preDeal.getCustApp());
				ps.setDate(8, new Date(preDeal.getDealDate().getTime()));
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		preDeal.setId(id);
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
	 return	template.update(DELETE_PREDEALS_BY_ID, id) == 1;
	}

	@Override
	public void deleteAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ALL_PREDEALS_BY_ID);
		
	}

	@Override
	public boolean updateById( PreDeal preDeal) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
	return	template.update(UPDATE_PREDEALS_BY_ID, preDeal.getSellerHashCode(), 
				preDeal.getCustomerHashCode(), preDeal.getTenderId(),preDeal.getLotId(), 
				preDeal.getSellerApprov(), preDeal.getCustApprov(), 
				preDeal.getDealDate(), preDeal.getId()) == 1;
	}

	@Override
	public List<PreDeal> getAllPreDeal() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<PreDeal> list = template.query(GET_ALL_PREDEALS_BY_ID, new BeanPropertyRowMapper<PreDeal>(PreDeal.class));
		return list;
	}

	
}
