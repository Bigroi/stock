package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DealsDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class DealsDaoImpl implements DealsDao {
	
	private static final Logger logger = Logger.getLogger(DealsDaoImpl.class);

	private static final String ADD_DEALS_BY_ID = "INSERT INTO deals(id, lot_Id, tender_Id, deals_Time) "
			+ "VALUES (?, ?, ?, ?) ";

	private static final String DELETE_DEALS_BY_ID = "DELETE FROM deals WHERE id = ? ";

	private static final String UPDATE_DEALS_BY_ID = "UPDATE deals SET lot_Id = ?, "
			+ "tender_Id = ?, deals_Time = ? WHERE id = ? ";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Deals deals) throws DaoException {
		logger.info("exection DealsDaoImpl.add");
		logger.info(deals);
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_DEALS_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, deals.getId());
				ps.setLong(2, deals.getLotId());
				ps.setLong(3, deals.getTenderId());
				ps.setDate(4, new Date(deals.getDealsTime().getTime()));
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		deals.setId(id);
		logger.info("exection DealsDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection DealsDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection DealsDaoImpl.deletedById successfully finished");
		return template.update(DELETE_DEALS_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById(Deals deals) throws DaoException {
		logger.info("exection DealsDaoImpl.updateById");
		logger.info(deals);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection DealsDaoImpl.updateById successfully finished");
		return template.update(UPDATE_DEALS_BY_ID,  deals.getLotId(),
				 deals.getTenderId(),  deals.getDealsTime(), deals.getId()) == 1;
	}

	
}
