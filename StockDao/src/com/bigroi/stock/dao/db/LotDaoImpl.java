package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.dao.LotDao;

@Repository
public class LotDaoImpl implements LotDao {
	
	private static final String ADD_LOT = 
			"INSERT INTO LOT "
			+ "(DESCRIPTION, PRODUCT_ID, CATEGORY_ID, PRICE, MIN_VOLUME, "
			+ " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
			+ " ADDRESS_ID, FOTO, DISTANCE) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	private static final String UPDATE_MAX_VOLUME_BY_ID = 
			"UPDATE LOT "
			+ " SET MAX_VOLUME = ? "
			+ " WHERE ID = ?";
	
	private static final String UPDATE_STATUS_BY_ID = 
			"UPDATE LOT "
			+ " SET STATUS = ? "
			+ " WHERE ID = ?";
	
	private static final String UPDATE_LOT_BY_ID_AND_SELLER = 
			"UPDATE LOT SET "
			+ " DESCRIPTION = ?, PRODUCT_ID = ?, CATEGORY_ID = ?, PRICE = ?, MIN_VOLUME = ?, "
			+ " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
			+ " ADDRESS_ID = ?, FOTO = ?, DISTANCE = ? "
			+ " WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String GET_LOT_BY_ID = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ LotRowMapper.FROM
			+ " WHERE L.ID = ?";
	
	private static final String GET_ACTIVE_LOTS = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ LotRowMapper.FROM
			+ " WHERE  L.`STATUS` = '" + BidStatus.ACTIVE.name() + "'";
	
	private static final String GET_ACTIVE_LOTS_BY_PRODUCT_ID = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ LotRowMapper.FROM
			+ " WHERE L.PRODUCT_ID = ? "
			+ " AND L.`STATUS` = '" + BidStatus.ACTIVE.name() + "' AND L.MIN_VOLUME <= L.MAX_VOLUME ";
	
	private static final String GET_LOTS_BY_COMPANY = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ LotRowMapper.FROM
			+ " WHERE L.COMPANY_ID = ?"
			+ " AND MIN_VOLUME <= MAX_VOLUME";
	
	private static final String GET_LOTS_BY_DESCRIPTION = 
			"SELECT " + LotRowMapper.ALL_COLUMNS
			+ LotRowMapper.FROM
			+ " WHERE L.COMPANY_ID = 0 "
			+ " AND L.DESCRIPTION = ?";
	
	private static final String SET_STATUS_BY_COMPANY =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE COMPANY_ID = ?";
	
	private static final String SET_STATUS_BY_ID_AND_COMPANY =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String SET_STATUS_BY_PRODUCT_ID =
			  "UPDATE LOT SET "
			+ "STATUS = ? "
			+ "WHERE PRODUCT_ID = ?";

	private static final String DELETE_BY_ID_AND_COMPANY = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE ID = ? AND COMPANY_ID = ?";
	
	private static final String DELETE_BY_DESCRIPTION = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE DESCRIPTION = ? AND COMPANY_ID = 0";
	
	private static final String CLOSE_LOTS = 
			"DELETE "
			+ " FROM LOT "
			+ " WHERE MIN_VOLUME > MAX_VOLUME";
	
	@Autowired
	private DataSource datasource;

	@Override
	public void add(Lot lot) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_LOT, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, lot.getDescription());
				ps.setLong(2, lot.getProductId());
				ps.setLong(3, lot.getCategoryId());
				ps.setDouble(4, lot.getPrice());
				ps.setInt(5, lot.getMinVolume());
				ps.setInt(6, lot.getMaxVolume());
				ps.setLong(7, lot.getCompanyId());
				ps.setString(8, lot.getStatus().name().toUpperCase());
				ps.setDate(9, new Date(lot.getCreationDate().getTime()));
				ps.setDate(10, new Date(lot.getExparationDate().getTime()));
				ps.setLong(11, lot.getAddressId());
				ps.setString(12, lot.getFoto());
				ps.setInt(13, lot.getDistance());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		lot.setId(id);
	}

	@Override
	public boolean update(Lot lot, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_LOT_BY_ID_AND_SELLER, 
				lot.getDescription(), 
				lot.getProductId(), 
				lot.getCategoryId(),
				lot.getPrice(), 
				lot.getMinVolume(),
				lot.getMaxVolume(), 
				lot.getStatus().name(),
				lot.getCreationDate(),
				lot.getExparationDate(), 
				lot.getAddressId(),
				lot.getFoto(),
				lot.getDistance(),
				lot.getId(),
				companyId) == 1;
	}

	@Override
	public Lot getById(long id, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Lot> lots = template.query(GET_LOT_BY_ID, new LotRowMapper(), id);
		if(lots.isEmpty() || (companyId != -1 && lots.get(0).getCompanyId() != companyId)){
			return null;
		}else{
			return lots.get(0);
		}
	}

	@Override
	public List<Lot> getByCompanyId(long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_LOTS_BY_COMPANY, new LotRowMapper(), companyId);
	}

	@Override
	public  boolean setStatusByCompanyId(long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_COMPANY, status.toString(), companyId) == 1;
		 
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	
	@Override
	public boolean setStatusById(long id, long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(SET_STATUS_BY_ID_AND_COMPANY, status.name(), id, companyId) == 1;
	}
	
	@Override
	public void deleteByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_DESCRIPTION, description);
	}

	@Override
	public void update(Collection<TradeLot> lots) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_MAX_VOLUME_BY_ID, lots, lots.size(), new ParameterizedPreparedStatementSetter<TradeLot>() {

			@Override
			public void setValues(PreparedStatement ps, TradeLot lot) throws SQLException {
				ps.setInt(1, lot.getMaxVolume());
				ps.setLong(2, lot.getId());
			}
		});
		
	}
	
	@Override
	public void updateStatus(List<Lot> lots) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_STATUS_BY_ID, lots, lots.size(), new ParameterizedPreparedStatementSetter<Lot>() {

			@Override
			public void setValues(PreparedStatement ps, Lot lot) throws SQLException {
				ps.setString(1, lot.getStatus().name());
				ps.setLong(2, lot.getId());
			}
		});
	}

	@Override
	public void delete(long id, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID_AND_COMPANY, id, companyId);
	}
	
	@Override
	public void closeLots() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(CLOSE_LOTS);
	}

	@Override
	public List<Lot> getActive() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_LOTS, new LotRowMapper());
	}

	@Override
	public List<Lot> getActiveByProductId(long productId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_LOTS_BY_PRODUCT_ID, new LotRowMapper(), productId);
	}
	
	@Override
	public List<Lot> getByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_LOTS_BY_DESCRIPTION, new LotRowMapper(), description);
	}
	
	private static class LotRowMapper implements RowMapper<Lot>{

		public static final String ALL_COLUMNS = 
				" L.ID, L.DESCRIPTION, L.PRODUCT_ID, L.CATEGORY_ID, L.PRICE, L.MIN_VOLUME, "
			+ " L.MAX_VOLUME, L.COMPANY_ID, L.`STATUS`, L.CREATION_DATE, "
			+ " L.EXPARATION_DATE, L.ADDRESS_ID, L.FOTO, L.DISTANCE, "
			+ " P.NAME PRODUCT_NAME,"
			+ " A.LONGITUDE LONGITUDE, A.LATITUDE LATITUDE, "
			+ " C.LANGUAGE ";
		
		public static final String FROM = 
				" FROM LOT L "
			+ " JOIN PRODUCT P "
			+ " ON L.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS A "
			+ " ON L.ADDRESS_ID = A.ID "
			+ " JOIN COMPANY C "
			+ " ON L.COMPANY_ID = C.ID ";
		
		@Override
		public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
			Lot lot = new Lot();
			lot.setAddressId(rs.getLong("ADDRESS_ID"));
			lot.setCompanyId(rs.getLong("COMPANY_ID"));
			lot.setCreationDate(rs.getTimestamp("CREATION_DATE"));
			lot.setDescription(rs.getString("DESCRIPTION"));
			lot.setExparationDate(rs.getTimestamp("EXPARATION_DATE"));
			lot.setFoto(rs.getString("FOTO"));
			lot.setId(rs.getLong("ID"));
			lot.setMaxVolume(rs.getInt("MAX_VOLUME"));
			lot.setMinVolume(rs.getInt("MIN_VOLUME"));
			lot.setPrice(rs.getDouble("PRICE"));
			lot.setProductId(rs.getLong("PRODUCT_ID"));
			lot.setStatus(BidStatus.valueOf(rs.getString("STATUS")));
			lot.setDistance(rs.getInt("DISTANCE"));
			lot.setCategoryId(rs.getLong("CATEGORY_ID"));
			
			
			Product product = new Product();
			product.setName(rs.getString("PRODUCT_NAME"));
			product.setId(rs.getLong("PRODUCT_ID"));
			lot.setProduct(product);
			
			CompanyAddress address = new CompanyAddress();
			address.setLatitude(rs.getDouble("LATITUDE"));
			address.setLongitude(rs.getDouble("LONGITUDE"));
			lot.setCompanyAddress(address);
			
			Company company = new Company();
			company.setLanguage(rs.getString("LANGUAGE"));
			address.setCompany(company);
			
			return lot;
		}
	}
}
