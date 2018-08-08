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
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.TenderDao;

@Repository
public class TenderDaoImpl implements TenderDao{
	
	private static final String ADD_TENDER = 
			  " INSERT INTO TENDER "
			+ " (DESCRIPTION, PRODUCT_ID, PRICE, MIN_VOLUME, "
			+ " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
			+ " ADDRESS_ID) "
			+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
	private static final String UPDATE_TENDER_BY_ID = 
			  " UPDATE TENDER "
			+ " SET MAX_VOLUME = ?, STATUS = ? "
			+ " WHERE ID = ? ";
	
	private static final String UPDATE_TENDER_BY_ID_AND_COMPANY = 
			  " UPDATE TENDER "
			  + " SET DESCRIPTION = ?, PRODUCT_ID = ?, PRICE = ?, MIN_VOLUME = ?, "
			  + " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
			  + " ADDRESS_ID = ? "
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
	public void add(Tender tender) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_TENDER,PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, tender.getDescription());
				ps.setLong(2, tender.getProductId());
				ps.setDouble(3, tender.getPrice());
				ps.setInt(4, tender.getMinVolume());
				ps.setInt(5, tender.getMaxVolume());
				ps.setLong(6, tender.getCompanyId());
				ps.setString(7, tender.getStatus().name().toUpperCase());
				ps.setDate(8, new Date(tender.getCreationDate().getTime()));
				ps.setDate(9, new Date(tender.getExparationDate().getTime()));
				ps.setLong(10, tender.getAddressId());
				
				return ps;
			}
		},keyHolder);
		long id = keyHolder.getKey().longValue();
		tender.setId(id);
	}

	@Override
	public boolean update(Tender tender, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return	template.update(UPDATE_TENDER_BY_ID_AND_COMPANY, 
				tender.getDescription(), 
				tender.getProductId(),
				tender.getPrice(), 
				tender.getMinVolume(), 
				tender.getMaxVolume(), 
				tender.getStatus().name(), 
				tender.getCreationDate(),
				tender.getExparationDate(), 
				tender.getAddressId(),
				tender.getId(),
				companyId) == 1;
	}

	@Override
	public Tender getById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Tender> tenders = template.query(GET_TENDER_BY_ID, new TenderRowMapper(), id);
		if (tenders.isEmpty() || (companyId != -1 && tenders.get(0).getCompanyId() != companyId)) {
			return null;
		} else {
			return tenders.get(0);
		}
	}
	
	@Override
	public List<Tender> getByCompanyId(long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_TENDERS_BY_COMPANY, new TenderRowMapper(), companyId);
	}

	@Override
	public boolean setStatusByCompanyId(long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_COMPANY_ID, status.toString(), companyId) == 1;
	}

	@Override
	public boolean setStatusByProductId(long productId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_STATUS_BY_PRODUCT_ID, status.toString(), productId) == 1;
	}

	@Override
	public void setStatusById(long id, long companyId, BidStatus status) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_STATUS_BY_ID_AND_COMPANY, status.name(), id, companyId);
	}
	
	@Override
	public void update(Collection<Tender> tendersToUpdate) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(UPDATE_TENDER_BY_ID, tendersToUpdate, tendersToUpdate.size(), new ParameterizedPreparedStatementSetter<Tender>() {

			@Override
			public void setValues(PreparedStatement ps, Tender tender) throws SQLException {
				ps.setInt(1, tender.getMaxVolume());
				ps.setString(2, tender.getStatus().name());
				ps.setLong(3, tender.getId());
			}
		});
	}

	@Override
	public void delete(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_ID_AND_COMPANY, id, companyId);
	}
	
	@Override
	public void closeTeners() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(CLOSE_TENDERS);
	}

	@Override
	public List<Tender> getActive() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_TENDERS, new TenderRowMapper());
	}

	@Override
	public List<Tender> getActiveByProductId(long productId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ACTIVE_TENDERS_BY_PRODUCT_ID, new TenderRowMapper(), productId);
	}
	
	@Override
	public List<Tender> getByDescription(String description) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_TENDERS_BY_DESCRIPTION, new TenderRowMapper(), description);
	}
	
	@Override
	public void deleteByDescription(String description) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_BY_DESCRIPTION, description);
	}
	
	private static class TenderRowMapper implements RowMapper<Tender>{

		public static final String ALL_COLUMNS = 
				" T.ID, T.DESCRIPTION, T.PRODUCT_ID, T.PRICE, T.MIN_VOLUME, "
			+ " T.MAX_VOLUME, T.COMPANY_ID, T.`STATUS`, T.CREATION_DATE, "
			+ " T.EXPARATION_DATE, T.ADDRESS_ID, "
			+ " P.NAME PRODUCT_NAME, "
			+ " A.LONGITUDE LONGITUDE, A.LATITUDE LATITUDE ";
		
		public static final String FROM = 
				" FROM TENDER T "
			+ " JOIN PRODUCT P "
			+ " ON T.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS A "
			+ " ON T.ADDRESS_ID = A.ID ";
		
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
			
			Product product = new Product();
			product.setName(rs.getString("PRODUCT_NAME"));
			product.setId(rs.getLong("PRODUCT_ID"));
			tender.setProduct(product);

			CompanyAddress address = new CompanyAddress();
			address.setLatitude(rs.getDouble("LATITUDE"));
			address.setLongitude(rs.getDouble("LONGITUDE"));
			tender.setCompanyAddress(address);
			
			return tender;
		}
	}

}
