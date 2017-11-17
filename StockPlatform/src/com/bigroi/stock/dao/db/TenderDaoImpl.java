package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;

public class TenderDaoImpl implements TenderDao{
	
	private static final String ADD_TENDER_BY_ID = "INSERT INTO tender "
			+ " (description, product_Id, max_price, customer_Id, "
			+ " status, exp_date, volume_of_tender) VALUES ( ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String UPDATE_TENDER_BY_ID = "UPDATE tender SET "
			+ " description = ?, product_Id = ?, max_price = ?, customer_Id = ?, "
			+ " status = ?, exp_date = ?, volume_of_tender = ? "
			+ " WHERE id = ? ";
	
	private static final String SELECT_TENDER_BY_ID = "SELECT id, description, product_Id, "
			+ " max_price, customer_Id, status, exp_date, volume_of_tender FROM tender WHERE id = ? ";
	
	private static final String SELECT_TENDER_BY_CUSTOMER_ID = "SELECT id, description, "
			+ "product_Id, max_price, customer_Id, status, exp_date, volume_of_tender "
			+ "FROM tender WHERE customer_Id = ? ";
	
	private static final String SELECT_TENDER_BY_PRODUCT_ID_IN_GAME_ORDER_MAX_PRACE_DESC = "SELECT id, description, "
			+ "product_Id, max_price, customer_Id, status, exp_date, volume_of_tender FROM tender WHERE "
			+ "product_Id = ? AND status = 'IN_GAME' ORDER BY max_price DESC";
	
	private static final String SELECT_ALL_ACTIVE_TENDERS = "SELECT id, description, "
			+ " product_Id, max_price, customer_Id, status, exp_date, volume_of_tender FROM tender "
			+ " WHERE status = 'IN_GAME' ";
	
	private static final String UPDATE_STATUS_BY_CUSTOMER_ID ="UPDATE tender SET "
			+ "status = ? WHERE customer_id = ?";
	
	private static final String UPDATE_STATUS_BY_PRODUCT_ID ="UPDATE tender SET "
			+ "status = ? WHERE product_id = ?";
	
	private static final String UPDATE_STATUS_BY_ID ="UPDATE tender SET "
			+ "status = ? WHERE id = ?";
			
			
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
				PreparedStatement ps = con.prepareStatement(ADD_TENDER_BY_ID,PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, tender.getDescription());
				ps.setLong(2, tender.getProductId());
				ps.setDouble(3, tender.getMaxPrice());
				ps.setLong(4, tender.getCustomerId());
				ps.setString(5, tender.getStatus().name().toUpperCase());
				ps.setDate(6, new Date(tender.getExpDate().getTime()));
				ps.setInt(7, tender.getVolume());
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
				tender.getVolume(), tender.getId()) == 1;
	}

	@Override
	public Tender getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_ID, new BeanPropertyRowMapper<Tender>(Tender.class), id);
		if (tender.size() == 0) {
			return null;
		} else {
			return tender.get(0);
		}
	}
	
	@Override
	public List<Tender> getByCustomerId(long customerId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_CUSTOMER_ID, new RowMapper<Tender>(){
			@Override
			public Tender mapRow(ResultSet rs, int rowNum) throws SQLException {
				Tender tender = new Tender();
				tender.setId(rs.getLong("id"));
				tender.setDescription(rs.getString("description"));
				tender.setProductId(rs.getLong("product_Id"));
				tender.setMaxPrice(rs.getDouble("max_price"));
				tender.setCustomerId(rs.getLong("customer_Id"));
				tender.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
				tender.setExpDate(rs.getDate("exp_date"));
				tender.setVolume(rs.getInt("volume_of_tender"));
				return tender;
			}
		},customerId);
		return tender;
	}

	@Override
	public List<Tender> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_PRODUCT_ID_IN_GAME_ORDER_MAX_PRACE_DESC,
				new BeanPropertyRowMapper<Tender>(Tender.class), productId);
		return tender;
	}

	@Override
	public List<Tender> getAllActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_ALL_ACTIVE_TENDERS, 
				new BeanPropertyRowMapper<Tender>(Tender.class));
		return tender;
	}

	@Override
	public boolean setStatusByCustomerId(long customerId, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_CUSTOMER_ID, status.toString(), customerId) == 1;
	}

	@Override
	public boolean setStatusByProductId(long productId, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	@Override
	public boolean setStatusById(long id, Status status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_ID, status.toString(), id) == 1;
	}
	
	@Override
	public void update(Set<Tender> tendersToUpdate) throws DaoException {
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
				ps.setInt(7, tender.getVolume());
				ps.setLong(8, tender.getId());
			}
		});
	}
}
