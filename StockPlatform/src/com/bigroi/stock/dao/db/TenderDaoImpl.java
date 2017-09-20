package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Tender;
import com.bigroi.stock.bean.common.Status;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;
import com.mysql.jdbc.Statement;

public class TenderDaoImpl implements TenderDao{
	
	private static final Logger logger = Logger.getLogger(TenderDaoImpl.class);
	
	private static final String ADD_TENDER_BY_ID = "INSERT INTO tender "
			+ " (id, description, product_Id, max_price, customer_Id, "
			+ " status, exp_date, volume_of_tender) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String DELETE_TENDER_BY_ID = "DELETE FROM tender WHERE id = ? ";

	private static final String UPDATE_TENDER_BY_ID = "UPDATE tender SET description = ?, "
			+ "product_Id = ?, max_price = ?, customer_Id = ?, status = ?, exp_date = ?, "
			+ "volume_of_tender = ? WHERE id = ? ";
	
	private static final String SELECT_TENDER_BY_ID = "SELECT id, description, product_Id, "
			+ " max_price, customer_Id, status, exp_date, volume_of_tender FROM tender WHERE id = ? ";
	
	private static final String SELECT_TENDER_BY_CUSTOMER_ID = "SELECT id, description, "
			+ "product_Id, max_price, customer_Id, status, exp_date, volume_of_tender "
			+ "FROM tender WHERE customer_Id = ? ";
	
	private static final String SELECT_TENDER_BY_PRODUCT_ID ="SELECT id, description, "
			+ " product_Id, max_price, customer_Id, status, exp_date, volume_of_tender FROM tender "
			+ " WHERE product_Id = ? ORDER BY  max_price DESC ";
	
	private static final String SELECT_TENDER_BY_PRODUCT_ID_IN_GAME_ORDER_MAX_PRACE_DESC = "SELECT id, description, "
			+ "product_Id, max_price, customer_Id, status, exp_date, volume_of_tender FROM tender WHERE "
			+ "product_Id = ? AND status = 'IN_GAME' ORDER BY max_price DESC";
	
	private static final String SELECT_ALL_TENDER_IN_GAME = "SELECT id, description, "
			+ " product_Id, max_price, customer_Id, status, exp_date, volume_of_tender FROM tender "
			+ " WHERE status = 'IN_GAME' ";
	
	private static final String UPDATE_STATUS_BY_CUSTOMER_ID ="UPDATE tender SET status = 'CANCELED'"
			+ " WHERE customer_Id = ?";
			
			
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(Tender tender) throws DaoException {
		logger.info("exection TenderDaoImpl.add");
		logger.info(tender);
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
				ps.setString(6, tender.getStatus().name().toUpperCase());
				ps.setDate(7, new Date(tender.getExpDate().getTime()));
				ps.setInt(8, tender.getVolumeOfTender());
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
		logger.info("exection TenderDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection TenderDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection TenderDaoImpl.deletedById successfully finished");
		return	template.update(DELETE_TENDER_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById(Tender tender) throws DaoException {
		logger.info("exection TenderDaoImpl.updateById");
		logger.info(tender);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection TenderDaoImpl.updateById successfully finished");
		return	template.update(UPDATE_TENDER_BY_ID, tender.getDescription(), tender.getProductId(),
				tender.getMaxPrice(), tender.getCustomerId(), 
				tender.getStatus().name().toUpperCase(), tender.getExpDate(), 
				tender.getVolumeOfTender(), tender.getId()) == 1;
	}

	@Override
	public Tender getById(long id) throws DaoException {
		logger.info("exection TenderDaoImpl.getById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_ID, new BeanPropertyRowMapper<Tender>(Tender.class), id);
		if (tender.size() == 0) {
			logger.info("exection TenderDaoImpl.getById return null, successfully finished");
			return null;
		} else {
			logger.info("exection TenderDaoImpl.getById return tender.get(0), successfully finished");
			return tender.get(0);
		}
	}
	
	@Override
	public List<Tender> getByCustomerId(long customerId) throws DaoException {
		logger.info("exection TenderDaoImpl.getByCustomerId");
		logger.info(customerId);
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
				tender.setVolumeOfTender(rs.getInt("volume_of_tender"));
				return tender;
			}
		},customerId);
		logger.info("exection TenderDaoImpl.getByCustomerId successfully finished");
		return tender;
	}

	@Override
	public List<Tender> getByProductId(long productId) throws DaoException {
		logger.info("exection TenderDaoImpl.getByProductId");
		logger.info(productId);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_PRODUCT_ID, new RowMapper<Tender>() {
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
				tender.setVolumeOfTender(rs.getInt("volume_of_tender"));
				return tender;
			}
		}, productId);
		logger.info("exection TenderDaoImpl.getByProductId successfully finished");
		return tender;
	}

	@Override
	public List<Tender> getByProductIdInGameOrderMaxPriceDesc(long productId) throws DaoException {
		logger.info("exection TenderDaoImpl.getByProductIdInGameOrderMaxPriceDesc");
		logger.info(productId);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_TENDER_BY_PRODUCT_ID_IN_GAME_ORDER_MAX_PRACE_DESC,
				new BeanPropertyRowMapper<Tender>(Tender.class), productId);
		logger.info("exection TenderDaoImpl.getByProductIdInGameOrderMaxPriceDesc successfully finished");
		return tender;
	}

	@Override
	public List<Tender> getAllInGame() throws DaoException {
		logger.info("exection TenderDaoImpl.getAllInGame");
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tender = template.query(SELECT_ALL_TENDER_IN_GAME, 
				new BeanPropertyRowMapper<Tender>(Tender.class));
		logger.info("exection TenderDaoImpl.getAllInGame successfully finished");
		return tender;
	}

	@Override
	public Tender setStatusCancel(Tender tender) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_STATUS_BY_CUSTOMER_ID, tender.getCustomerId());
		return tender;
	}
}
