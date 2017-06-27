package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;
import com.mysql.jdbc.Statement;

public class TenderDaoImpl implements TenderDao{
	
	private static final String ADD_TENDER_BY_ID = "INSERT INTO tender"
			+ " (id, description, productId, max_price, customerId,"
			+ " status, exp_date) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
		
	private static final String DELETE_TENDER_BY_ID = "DELETE FROM tender WHERE id = ?";

	private static final String UPDATE_TENDER_BY_ID = "UPDATE tender SET id = ?, description = ?, "
			+ "productId = ?, max_price = ?, customerId = ?, status = ?, exp_date = ? "
			+ "WHERE id = ?";
	
	private static final String SELECT_TENDER_BY_ID = "SELECT id, description, productId,"
			+ " max_price, customerId, status, exp_date FROM tender WHERE id = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Tender tender) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_TENDER_BY_ID,Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, tender.getId());
				ps.setString(2, tender.getDescription());
				ps.setLong(3, tender.getProductId());
				ps.setDouble(4, tender.getMaxPrice());
				ps.setLong(5, tender.getCustomerId());
				ps.setByte(6, tender.getStatus());
				ps.setDate(7, new Date(tender.getExpData().getTime()));
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
	}

	@Override
	public void delete(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_TENDER_BY_ID,id);
	}

	@Override
	public void update(long id, Tender tender) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_TENDER_BY_ID, tender.getId(), tender.getDescription(), tender.getProductId(),
				tender.getMaxPrice(), tender.getCustomerId(), tender.getStatus(), tender.getExpData(), id);
	}

	@Override
	public Tender getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		Tender tender = template.queryForObject(SELECT_TENDER_BY_ID, new Object[] {id}, 
				new BeanPropertyRowMapper<Tender>(Tender.class));
		return tender;
	}
}
