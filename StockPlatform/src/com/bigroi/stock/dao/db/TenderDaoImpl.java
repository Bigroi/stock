package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;

public class TenderDaoImpl implements TenderDao{
	
	private static final String ADD_TENDER = "INSERT INTO TENDER "
			+ " (DESCRIPTION, PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, "
			+ " STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String UPDATE_TENDER_BY_ID = "UPDATE TENDER SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, MAX_PRICE = ?, CUSTOMER_ID = ?, "
			+ " STATUS = ?, EXP_DATE = ?, MAX_VOLUME = ?, MIN_VOLUME = ? "
			+ " WHERE ID = ? ";
	
	private static final String GET_TENDER_BY_ID = "SELECT ID, DESCRIPTION, PRODUCT_ID, "
			+ " MAX_PRICE, CUSTOMER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME FROM TENDER WHERE ID = ? ";
	
	private static final String GET_TENDERS_BY_CUSTOMER_ID = "SELECT T.ID, T.DESCRIPTION, "
			+ " T.PRODUCT_ID, T.MAX_PRICE, T.CUSTOMER_ID, T.STATUS, T.EXP_DATE, "
			+ " T.MAX_VOLUME, T.MIN_VOLUME, P.NAME "
			+ " FROM TENDER T "
			+ " JOIN PRODUCT P "
			+ " ON T.PRODUCT_ID = P.ID"
			+ " WHERE CUSTOMER_ID = ? ";
	
	private static final String GET_ACTIVE_TENDERS_BY_PRODUCT_ID = "SELECT ID, DESCRIPTION, "
			+ "PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME FROM TENDER WHERE "
			+ "PRODUCT_ID = ? AND STATUS = '" + BidStatus.ACTIVE +"' ORDER BY MAX_PRICE DESC";
	
	private static final String GET_ALL_ACTIVE_TENDERS = "SELECT ID, DESCRIPTION, "
			+ " PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME FROM TENDER "
			+ " WHERE STATUS = '" + BidStatus.ACTIVE +"' ";
	
	private static final String UPDATE_STATUS_BY_CUSTOMER_ID ="UPDATE TENDER SET "
			+ "STATUS = ? WHERE CUSTOMER_ID = ?";
	
	private static final String UPDATE_STATUS_BY_PRODUCT_ID ="UPDATE TENDER SET "
			+ "STATUS = ? WHERE PRODUCT_ID = ?";
	
	private static final String UPDATE_STATUS_BY_ID ="UPDATE TENDER SET "
			+ "STATUS = ? WHERE ID = ?";
	
	private static final String DELETE_BY_ID =
			"DELETE "
			+ " FROM TENDER "
			+ " WHERE ID = ?";
			
			
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
				PreparedStatement ps = con.prepareStatement(ADD_TENDER,PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, tender.getDescription());
				ps.setLong(2, tender.getProductId());
				ps.setDouble(3, tender.getMaxPrice());
				ps.setLong(4, tender.getCustomerId());
				ps.setString(5, tender.getStatus().name().toUpperCase());
				ps.setDate(6, new Date(tender.getExpDate().getTime()));
				ps.setInt(7, tender.getMaxVolume());
				ps.setInt(8, tender.getMinVolume());
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
	}

	@Override
	public boolean update(Tender tender) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_TENDER_BY_ID, tender.getDescription(), tender.getProductId(),
				tender.getMaxPrice(), tender.getCustomerId(), 
				tender.getStatus().name().toUpperCase(), tender.getExpDate(), 
				tender.getMaxVolume(), tender.getMinVolume(), tender.getId()) == 1;
	}

	@Override
	public Tender getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(GET_TENDER_BY_ID, new BeanPropertyRowMapper<Tender>(Tender.class), id);
		if (tender.size() == 0) {
			return null;
		} else {
			return tender.get(0);
		}
	}
	
	@Override
	public List<Tender> getByCustomerId(long customerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_TENDERS_BY_CUSTOMER_ID, new BeanPropertyRowMapper<Tender>(Tender.class), customerId);
	}

	@Override
	public List<Tender> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(GET_ACTIVE_TENDERS_BY_PRODUCT_ID,
				new BeanPropertyRowMapper<Tender>(Tender.class), productId);
		return tender;
	}

	@Override
	public List<Tender> getAllActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(GET_ALL_ACTIVE_TENDERS, 
				new BeanPropertyRowMapper<Tender>(Tender.class));
		return tender;
	}

	@Override
	public boolean setStatusByCustomerId(long customerId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_CUSTOMER_ID, status.toString(), customerId) == 1;
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	@Override
	public boolean setStatusById(long id, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_ID, status.toString(), id) == 1;
	}
	
	@Override
	public void update(Collection<Tender> tendersToUpdate) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_TENDER_BY_ID, tendersToUpdate, tendersToUpdate.size(), new ParameterizedPreparedStatementSetter<Tender>() {

			@Override
			public void setValues(PreparedStatement ps, Tender tender) throws SQLException {
				ps.setString(1, tender.getDescription());
				ps.setLong(2, tender.getProductId());
				ps.setDouble(3, tender.getMaxPrice());
				ps.setLong(4, tender.getCustomerId());
				ps.setString(5, tender.getStatus().name());
				ps.setDate(6, new Date(tender.getExpDate().getTime()));
				ps.setInt(7, tender.getMaxVolume());
				ps.setInt(8, tender.getMinVolume());
				ps.setLong(9, tender.getId());
			}
		});
	}

	@Override
	public void deleteById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID, id);
	}
}
