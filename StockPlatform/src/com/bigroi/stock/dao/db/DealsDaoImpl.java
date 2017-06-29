package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Deals;
import com.bigroi.stock.dao.DealsDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class DealsDaoImpl implements DealsDao {

	private static final String ADD_ARCHIVE_BY_ID = "INSERT INTO deals "
			+ "(id, salerId, customerId, productId, price, tms_tmp, sellerApprov, custApprov)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String DELETE_ARCHIVE_BY_ID = "DELETE FROM deals WHERE id = ?";

	private static final String UPDATE_ARCHIVE_BY_ID = "UPDATE deals SET id = ?, salerId = ?,"
			+ "customerId = ?, productId = ?, price = ?, tms_tmp = ?, "
			+ "sellerApprov = ?, custApprov = ? WHERE id = ?";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Deals deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_ARCHIVE_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, deals.getId());
				ps.setLong(2, deals.getSalerId());
				ps.setLong(3, deals.getCustomerId());
				ps.setLong(4, deals.getProductId());
				ps.setDouble(5, deals.getPrice());
				ps.setDate(6, new Date(deals.getTmsTmp().getTime()));
				ps.setString(7, deals.getSellerApprov());
				ps.setString(8, deals.getCustApprov());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		deals.setId(id);
	}

	@Override
	public void delete(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ARCHIVE_BY_ID, id);
	}

	@Override
	public void update(long id, Deals deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_ARCHIVE_BY_ID, deals.getId(), deals.getSalerId(), 
				deals.getCustomerId(),deals.getProductId(), deals.getPrice(), 
				deals.getTmsTmp(), deals.getSellerApprov(), deals.getCustApprov(), id);
	}
}
