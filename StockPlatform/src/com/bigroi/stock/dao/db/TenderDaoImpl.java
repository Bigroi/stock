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
	
	private static final String ADD_TENDER = 
			  " INSERT INTO TENDER "
			+ " (DESCRIPTION, PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, "
			+ " STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, CREATION_DATE, "
			+ " DELIVERY, PACKAGING) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String UPDATE_TENDER_BY_ID = 
			  " UPDATE TENDER "
			+ " SET DESCRIPTION = ?, PRODUCT_ID = ?, MAX_PRICE = ?, "
			+ " STATUS = ?, EXP_DATE = ?, MAX_VOLUME = ?, MIN_VOLUME = ?, "
			+ " DELIVERY = ?, PACKAGING = ? "
			+ " WHERE ID = ? ";
	
	private static final String UPDATE_TENDER_BY_ID_AND_CUSTOMER = 
			  " UPDATE TENDER "
			+ " SET DESCRIPTION = ?, PRODUCT_ID = ?, MAX_PRICE = ?, "
			+ " STATUS = ?, EXP_DATE = ?, MAX_VOLUME = ?, MIN_VOLUME = ?, "
			+ " DELIVERY = ?, PACKAGING = ? "
			+ " WHERE ID = ? AND CUSTOMER_ID = ? ";
	
	private static final String GET_TENDER_BY_ID = 
			  " SELECT ID, DESCRIPTION, PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, "
			+ " STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME, CREATION_DATE, DELIVERY, PACKAGING "
			+ " FROM TENDER WHERE ID = ? ";
	
	private static final String GET_TENDERS_BY_CUSTOMER_ID = 
			  " SELECT T.ID, T.DESCRIPTION, T.PRODUCT_ID, T.MAX_PRICE, T.CUSTOMER_ID, "
			+ " T.STATUS, T.EXP_DATE, T.MAX_VOLUME, T.MIN_VOLUME, "
			+ " P.NAME AS PRODUCT_NAME, CREATION_DATE, DELIVERY, PACKAGING "
			+ " FROM TENDER T "
			+ " JOIN PRODUCT P "
			+ " ON T.PRODUCT_ID = P.ID"
			+ " WHERE CUSTOMER_ID = ? ";
	
	private static final String GET_ACTIVE_TENDERS_BY_PRODUCT_ID = 
			  " SELECT ID, DESCRIPTION, PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, "
			+ " STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME "
			+ " FROM TENDER "
			+ " WHERE PRODUCT_ID = ? AND STATUS = '" + BidStatus.ACTIVE +"' "
			+ " ORDER BY MAX_PRICE DESC";
	
	private static final String GET_ALL_ACTIVE_TENDERS = 
			  " SELECT ID, DESCRIPTION, PRODUCT_ID, MAX_PRICE, CUSTOMER_ID, "
			+ " STATUS, EXP_DATE, MAX_VOLUME, MIN_VOLUME "
			+ " FROM TENDER "
			+ " WHERE STATUS = '" + BidStatus.ACTIVE +"' ";
	
	private static final String UPDATE_STATUS_BY_CUSTOMER_ID =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE CUSTOMER_ID = ?";
	
	private static final String UPDATE_STATUS_BY_PRODUCT_ID =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE PRODUCT_ID = ?";
	
	private static final String UPDATE_STATUS_BY_ID =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE ID = ? AND CUSTOMER_ID = ? ";
	
	private static final String DELETE_BY_ID =
			  " DELETE "
			+ " FROM TENDER "
			+ " WHERE ID = ? AND CUSTOMER_ID = ? ";
			
			
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
				ps.setDate(9, new Date(tender.getCreationDate().getTime()));
				ps.setInt(10, tender.getDelivery());
				ps.setInt(11, tender.getPackaging());
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
	}

	@Override
	public boolean update(Tender tender, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_TENDER_BY_ID_AND_CUSTOMER, 
				tender.getDescription(), 
				tender.getProductId(),
				tender.getMaxPrice(), 
				tender.getStatus().name(), 
				tender.getExpDate(), 
				tender.getMaxVolume(), 
				tender.getMinVolume(), 
				tender.getDelivery(), 
				tender.getPackaging(), 
				tender.getId(),
				companyId) == 1;
	}

	@Override
	public Tender getById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tenders = template.query(GET_TENDER_BY_ID, new BeanPropertyRowMapper<Tender>(Tender.class), id);
		if (tenders.size() == 0 || (companyId != -1 && tenders.get(0).getCustomerId() != companyId)) {
			return null;
		} else {
			return tenders.get(0);
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
	public void setStatusById(long id, long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_STATUS_BY_ID, status.name(), id, companyId);
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
				ps.setString(4, tender.getStatus().name());
				ps.setDate(5, new Date(tender.getExpDate().getTime()));
				ps.setInt(6, tender.getMaxVolume());
				ps.setInt(7, tender.getMinVolume());
				ps.setInt(8, tender.getDelivery());
				ps.setInt(9, tender.getPackaging());
				ps.setLong(10, tender.getId());
			}
		});
	}

	@Override
	public void delete(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID, id, companyId);
	}
}
