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
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.bean.db.TradeTender;
import com.bigroi.stock.dao.TenderDao;

@Repository
public class TenderDaoImpl implements TenderDao{
	
	private static final String ADD_TENDER = 
			  " INSERT INTO TENDER "
			+ " (DESCRIPTION, PRODUCT_ID, CATEGORY_ID, PRICE, MIN_VOLUME, "
			+ " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
			+ " ADDRESS_ID, DISTANCE, PACKAGING, PROCESSING) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String UPDATE_MAX_VOLUME_BY_ID = 
			  " UPDATE TENDER "
			+ " SET MAX_VOLUME = ? "
			+ " WHERE ID = ? ";
	
	private static final String UPDATE_STATUS_BY_ID = 
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE ID = ? ";
	
	private static final String UPDATE_TENDER_BY_ID_AND_COMPANY = 
			  " UPDATE TENDER "
			  + " SET DESCRIPTION = ?, PRODUCT_ID = ?, CATEGORY_ID = ?, PRICE = ?, MIN_VOLUME = ?, "
			  + " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
			  + " ADDRESS_ID = ?, DISTANCE = ?, PACKAGING = ?, PROCESSING = ?"
			  + " WHERE ID = ? AND COMPANY_ID = ? ";
	
	private static final String GET_TENDERS_BY_COMPANY = 
			" SELECT " + TenderRowMapper.ALL_COLUMNS
			+ TenderRowMapper.FROM 
			+ " WHERE T.COMPANY_ID = ? "
			+ " AND MIN_VOLUME <= MAX_VOLUME";
	
	private static final String GET_TENDERS_BY_DESCRIPTION = 
			" SELECT " + TenderRowMapper.ALL_COLUMNS
			+ TenderRowMapper.FROM 
			+ " WHERE T.COMPANY_ID = 0 "
			+ " AND T.DESCRIPTION = ?";
	
	private static final String GET_TENDER_BY_ID = 
			" SELECT " + TenderRowMapper.ALL_COLUMNS
			+ TenderRowMapper.FROM
			+ " WHERE T.ID = ? ";
	
	private static final String GET_ACTIVE_TENDERS = 
			" SELECT " + TenderRowMapper.ALL_COLUMNS
			+ TenderRowMapper.FROM
			+ " WHERE T.`STATUS` = '" + BidStatus.ACTIVE + "'";
	
	private static final String GET_ACTIVE_TENDERS_BY_PRODUCT_ID = 
			" SELECT " + TenderRowMapper.ALL_COLUMNS
			+ TenderRowMapper.FROM
			+ " WHERE T.PRODUCT_ID = ? "
			+ " AND T.`STATUS` = '" + BidStatus.ACTIVE + "'  AND T.MIN_VOLUME <= T.MAX_VOLUME ";
	
	private static final String UPDATE_STATUS_BY_COMPANY_ID =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE COMPANY_ID = ?";
	
	private static final String UPDATE_STATUS_BY_PRODUCT_ID =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE PRODUCT_ID = ?";
	
	private static final String UPDATE_STATUS_BY_ID_AND_COMPANY =
			  " UPDATE TENDER "
			+ " SET STATUS = ? "
			+ " WHERE ID = ? AND COMPANY_ID = ? ";
	
	private static final String DELETE_BY_ID_AND_COMPANY =
			  " DELETE "
			+ " FROM TENDER "
			+ " WHERE ID = ? AND COMPANY_ID = ? ";
	
	private static final String CLOSE_TENDERS = 
			"DELETE "
			+ " FROM TENDER "
			+ " WHERE MIN_VOLUME > MAX_VOLUME";
	
	private static final String DELETE_BY_DESCRIPTION = 
			"DELETE "
			+ " FROM TENDER "
			+ " WHERE DESCRIPTION = ? AND COMPANY_ID = 0";
			
	@Autowired
	private DataSource datasource;

	@Override
	public void add(Tender tender) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_TENDER,PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, tender.getDescription());
				ps.setLong(2, tender.getProductId());
				ps.setLong(3, tender.getCategoryId());
				ps.setDouble(4, tender.getPrice());
				ps.setInt(5, tender.getMinVolume());
				ps.setInt(6, tender.getMaxVolume());
				ps.setLong(7, tender.getCompanyId());
				ps.setString(8, tender.getStatus().name().toUpperCase());
				ps.setDate(9, new Date(tender.getCreationDate().getTime()));
				ps.setDate(10, new Date(tender.getExparationDate().getTime()));
				ps.setLong(11, tender.getAddressId());
				ps.setInt(12, tender.getDistance());
				ps.setString(13, tender.getPackaging());
				ps.setString(14, tender.getProcessing());
				
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
	}

	@Override
	public boolean update(Tender tender, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_TENDER_BY_ID_AND_COMPANY, 
				tender.getDescription(), 
				tender.getProductId(),
				tender.getCategoryId(),
				tender.getPrice(), 
				tender.getMinVolume(), 
				tender.getMaxVolume(), 
				tender.getStatus().name(), 
				tender.getCreationDate(),
				tender.getExparationDate(), 
				tender.getAddressId(),
				tender.getDistance(),
				tender.getPackaging(),
				tender.getProcessing(),
				tender.getId(),
				companyId) == 1;
	}

	@Override
	public Tender getById(long id, long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tenders = template.query(GET_TENDER_BY_ID, new TenderRowMapper(), id);
		if (tenders.isEmpty() || (companyId != -1 && tenders.get(0).getCompanyId() != companyId)) {
			return null;
		} else {
			return tenders.get(0);
		}
	}
	
	@Override
	public List<Tender> getByCompanyId(long companyId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_TENDERS_BY_COMPANY, new TenderRowMapper(), companyId);
	}

	@Override
	public boolean setStatusByCompanyId(long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_COMPANY_ID, status.toString(), companyId) == 1;
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	@Override
	public void setStatusById(long id, long companyId, BidStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_STATUS_BY_ID_AND_COMPANY, status.name(), id, companyId);
	}
	
	@Override
	public void update(Collection<TradeTender> tenders) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_MAX_VOLUME_BY_ID, tenders, tenders.size(), new ParameterizedPreparedStatementSetter<TradeTender>() {

			@Override
			public void setValues(PreparedStatement ps, TradeTender tender) throws SQLException {
				ps.setInt(1, tender.getMaxVolume());
				ps.setLong(2, tender.getId());
			}
		});
	}
	
	@Override
	public void updateStatus(List<Tender> lots) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_STATUS_BY_ID, lots, lots.size(), new ParameterizedPreparedStatementSetter<Tender>() {

			@Override
			public void setValues(PreparedStatement ps, Tender lot) throws SQLException {
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
	public void closeTeners() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(CLOSE_TENDERS);
	}

	@Override
	public List<Tender> getActive() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_TENDERS, new TenderRowMapper());
	}

	@Override
	public List<Tender> getActiveByProductId(long productId) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_TENDERS_BY_PRODUCT_ID, new TenderRowMapper(), productId);
	}
	
	@Override
	public List<Tender> getByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_TENDERS_BY_DESCRIPTION, new TenderRowMapper(), description);
	}
	
	@Override
	public void deleteByDescription(String description) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_DESCRIPTION, description);
	}
	
	private static class TenderRowMapper implements RowMapper<Tender>{

		public static final String ALL_COLUMNS = 
				" T.ID, T.DESCRIPTION, T.PRODUCT_ID, T.PRICE, T.MIN_VOLUME, "
			+ " T.MAX_VOLUME, T.COMPANY_ID, T.`STATUS`, T.CREATION_DATE, "
			+ " T.EXPARATION_DATE, T.ADDRESS_ID, T.DISTANCE, T.PACKAGING, T.PROCESSING, "
			+ " P.NAME PRODUCT_NAME, T.CATEGORY_ID, "
			+ " A.LONGITUDE LONGITUDE, A.LATITUDE LATITUDE,"
			+ " C.LANGUAGE ";
		
		public static final String FROM = 
				" FROM TENDER T "
			+ " JOIN PRODUCT P "
			+ " ON T.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS A "
			+ " ON T.ADDRESS_ID = A.ID "
			+ " JOIN COMPANY C "
			+ " ON T.COMPANY_ID = C.ID ";
		
		@Override
		public Tender mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tender tender = new Tender();
			tender.setAddressId(rs.getLong("ADDRESS_ID"));
			tender.setCompanyId(rs.getLong("COMPANY_ID"));
			tender.setCreationDate(rs.getTimestamp("CREATION_DATE"));
			tender.setDescription(rs.getString("DESCRIPTION"));
			tender.setExparationDate(rs.getTimestamp("EXPARATION_DATE"));
			tender.setId(rs.getLong("ID"));
			tender.setMaxVolume(rs.getInt("MAX_VOLUME"));
			tender.setMinVolume(rs.getInt("MIN_VOLUME"));
			tender.setPrice(rs.getDouble("PRICE"));
			tender.setProductId(rs.getLong("PRODUCT_ID"));
			tender.setStatus(BidStatus.valueOf(rs.getString("STATUS")));
			tender.setDistance(rs.getInt("DISTANCE"));
			tender.setPackaging(rs.getString("PACKAGING"));
			tender.setProcessing(rs.getString("PROCESSING"));
			tender.setCategoryId(rs.getLong("CATEGORY_ID"));
			
			Product product = new Product();
			product.setName(rs.getString("PRODUCT_NAME"));
			product.setId(rs.getLong("PRODUCT_ID"));
			tender.setProduct(product);

			CompanyAddress address = new CompanyAddress();
			address.setLatitude(rs.getDouble("LATITUDE"));
			address.setLongitude(rs.getDouble("LONGITUDE"));
			tender.setCompanyAddress(address);
			
			Company company = new Company();
			company.setLanguage(rs.getString("LANGUAGE"));
			address.setCompany(company);
			
			return tender;
		}
	}

}
