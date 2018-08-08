package com.bigroi.stock.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Lot;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Tender;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DealDao;

@Repository
public class DealDaoImpl implements DealDao {
	
	private static final String ADD = 
					" INSERT INTO DEAL(LOT_ID, TENDER_ID, TIME, BUYER_CHOICE, SELLER_CHOICE, "
					+ " PRICE, VOLUME, PRODUCT_ID, SELLER_FOTO, MAX_TRANSPORT_PRICE, "
					+ " SELLER_ADDRESS_ID, BUYER_ADDRESS_ID, SELLER_DESCRIPTION, BUYER_DESCRIPTION) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String GET_POSIBLE_BIDS = 
			"SELECT "
			+ " L.ID LOT_ID, L.DESCRIPTION LOT_DESCRIPTION, L.PRICE LOT_PRICE, "
			+ " L.`STATUS` LOT_STATUS, L.EXPARATION_DATE LOT_EXP_DATE, L.MAX_VOLUME LOT_VOLUME, L.MIN_VOLUME LOT_MIN_VOLUME, "
			+ " LA.LONGITUDE LOT_LONGITUDE, LA.LATITUDE LOT_LATITUDE, LA.COMPANY_ID LOT_COMPANY_ID, LA.ID LOT_ADDRESS_ID, "
			
			+ " T.ID TENDER_ID, T.DESCRIPTION TENDER_DESCRIPTION, T.PRICE TENDER_PRICE, "
			+ " T.`STATUS` TENDER_STATUS, T.EXPARATION_DATE TENDER_EXP_DATE, T.MAX_VOLUME TENDER_VOLUME, T.MIN_VOLUME TENDER_MIN_VOLUME,  "
			+ " TA.LONGITUDE TENDER_LONGITUDE, TA.LATITUDE TENDER_LATITUDE, TA.COMPANY_ID TENDER_COMPANY_ID, TA.ID TENDER_ADDRESS_ID, "
			  
			+ " L.PRODUCT_ID PRODUCT_ID "
			+ " FROM LOT L "
			+ " JOIN ADDRESS LA "
			+ " ON L.ADDRESS_ID = LA.ID "
			+ " AND L.`STATUS` = 'ACTIVE' "
			+ " AND L.MAX_VOLUME >= L.MIN_VOLUME "
			+ " AND LOT_SPEC_CONDITION "
			
			+ " JOIN TENDER T "
			+ " ON L.PRODUCT_ID = T.PRODUCT_ID "
			+ " AND T.`STATUS` = 'ACTIVE' "
			+ " AND T.MAX_VOLUME >= T.MIN_VOLUME "
			+ " AND T.PRICE > L.PRICE "
			+ " AND T.MIN_VOLUME <= L.MAX_VOLUME "
			+ " AND L.MIN_VOLUME <= T.MAX_VOLUME "
			+ " AND TENDER_SPEC_CONDITION "
			+ " COMPAMY_CONDITION "
			
			+ " JOIN ADDRESS TA "
			+ " ON T.ADDRESS_ID = TA.ID "
			+ " LEFT JOIN BLACK_LIST BL "
			+ " ON BL.TENDER_ID = T.ID AND BL.LOT_ID = L.ID "
			+ " WHERE BL.ID IS NULL AND T.PRODUCT_ID = ?";

	private static final String GET_BY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
			+ " FROM DEAL D "
			+ " JOIN PRODUCT P "
			+ " ON D.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS SA "
			+ " ON SA.ID = D.SELLER_ADDRESS_ID "
			+ " JOIN COMPANY SC "
			+ " ON SA.COMPANY_ID = SC.ID "
			+ " JOIN USER SU "
			+ " ON SU.COMPANY_ID = SC.ID "
			+ " JOIN ADDRESS BA "
			+ " ON BA.ID = D.BUYER_ADDRESS_ID "
			+ " JOIN COMPANY BC "
			+ " ON BA.COMPANY_ID = BC.ID "
			+ " JOIN USER BU "
			+ " ON BU.COMPANY_ID = BC.ID "
			+ " WHERE D.ID = ?";

	private static final String ON_APPROVE_CRITERIA =
			  "(BUYER_CHOICE = " + PartnerChoice.ON_APPROVE.getCode()
			  		+ " AND SELLER_CHOICE <> " + PartnerChoice.ON_APPROVE.getCode() + ") "
		  + " OR (SELLER_CHOICE = " + PartnerChoice.ON_APPROVE.getCode()
			  		+ " AND BUYER_CHOICE <> " + PartnerChoice.ON_APPROVE.getCode() + ") "
		  + " OR (SELLER_CHOICE = " + PartnerChoice.ON_APPROVE.getCode()
			  		+ " AND BUYER_CHOICE = " + PartnerChoice.ON_APPROVE.getCode() + ")";
	
	private static final String GET_ON_APPROVE = 
					  " SELECT " + DealRowMapper.ALL_COLUMNS
						+ " FROM DEAL D "
						+ " JOIN PRODUCT P "
						+ " ON D.PRODUCT_ID = P.ID "
						+ " JOIN ADDRESS SA "
						+ " ON SA.ID = D.SELLER_ADDRESS_ID "
						+ " JOIN COMPANY SC "
						+ " ON SA.COMPANY_ID = SC.ID "
						+ " JOIN USER SU "
						+ " ON SU.COMPANY_ID = SC.ID "
						+ " JOIN ADDRESS BA "
						+ " ON BA.ID = D.BUYER_ADDRESS_ID "
						+ " JOIN COMPANY BC "
						+ " ON BA.COMPANY_ID = BC.ID "
						+ " JOIN USER BU "
						+ " ON BU.COMPANY_ID = BC.ID "
					    + " WHERE " + ON_APPROVE_CRITERIA;
	

	private static final String DELETE_ON_APPROVE = 
					  " DELETE "
					+ " FROM DEAL "
					+ " WHERE " + ON_APPROVE_CRITERIA;

	private static final String GET_BY_COMPANY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
			+ " FROM DEAL D "
			+ " JOIN PRODUCT P "
			+ " ON D.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS SA "
			+ " ON SA.ID = D.SELLER_ADDRESS_ID "
			+ " JOIN COMPANY SC "
			+ " ON SA.COMPANY_ID = SC.ID "
			+ " JOIN USER SU "
			+ " ON SU.COMPANY_ID = SC.ID "
			+ " JOIN ADDRESS BA "
			+ " ON BA.ID = D.BUYER_ADDRESS_ID "
			+ " JOIN COMPANY BC "
			+ " ON BA.COMPANY_ID = BC.ID "
			+ " JOIN USER BU "
			+ " ON BU.COMPANY_ID = BC.ID "
			+ " WHERE SA.COMPANY_ID = ? OR BA.COMPANY_ID = ? ";

	private static final String SET_SELLER_STATUS = 
			  " UPDATE DEAL "
			+ " SET SELLER_CHOICE = ? "
			+ " WHERE ID = ? ";
	
	private static final String SET_BUYER_STATUS = 
			  " UPDATE DEAL "
			+ " SET BUYER_CHOICE = ? "
			+ " WHERE ID = ? ";
	
	private static final String GET_LIST_BY_SELLER_BUYER_APPROVE = 
			" SELECT " + DealRowMapper.ALL_COLUMNS 
			+ " FROM DEAL D "
			+ " JOIN PRODUCT P "
			+ " ON D.PRODUCT_ID = P.ID "
			+ " JOIN ADDRESS SA "
			+ " ON SA.ID = D.SELLER_ADDRESS_ID "
			+ " JOIN COMPANY SC "
			+ " ON SA.COMPANY_ID = SC.ID "
			+ " JOIN USER SU "
			+ " ON SU.COMPANY_ID = SC.ID "
			+ " JOIN ADDRESS BA "
			+ " ON BA.ID = D.BUYER_ADDRESS_ID "
			+ " JOIN COMPANY BC "
			+ " ON BA.COMPANY_ID = BC.ID "
			+ " JOIN USER BU "
			+ " ON BU.COMPANY_ID = BC.ID "
			  + "WHERE ((BUYER_CHOICE | SELLER_CHOICE) & 15) = 8";


	@Autowired
	private DataSource datasource;
	
	@Override
	public void getTestPossibleDeals(List<Lot> tradeLots, List<Tender> tradeTenders, long productId, String sessionId)
			throws DaoException {
		String sql = GET_POSIBLE_BIDS
				.replace("COMPAMY_CONDITION", "")
				.replace("LOT_SPEC_CONDITION", " L.DESCRIPTION = '" + sessionId + "' ")
				.replaceAll("TENDER_SPEC_CONDITION", " T.DESCRIPTION = '" + sessionId + "' ");
		getTestPossibleDeals(sql, tradeLots, tradeTenders, productId);
	}
	
	@Override
	public void getPosibleDeals(List<Lot> tradeLots, List<Tender> tradeTenders, long productId) throws DaoException {
		String sql = GET_POSIBLE_BIDS
				.replace("COMPAMY_CONDITION", "AND T.COMPANY_ID <> L.COMPANY_ID ")
				.replace("LOT_SPEC_CONDITION", " L.COMPANY_ID <> 0 ")
				.replaceAll("TENDER_SPEC_CONDITION", " T.COMPANY_ID <> 0 ");
		getTestPossibleDeals(sql, tradeLots, tradeTenders, productId);
	}
	
	private void getTestPossibleDeals(String sql, List<Lot> tradeLots, List<Tender> tradeTenders, long productId){
		JdbcTemplate template = new JdbcTemplate(datasource);
		Map<Long, Lot> lots = new HashMap<>();
		Map<Long, Tender> tenders = new HashMap<>();
		template.query(sql, new TradeDealRowMapper(lots, tenders), productId);
		
		tradeLots.addAll(lots.values());
		tradeTenders.addAll(tenders.values());
	}

	@Override
	public Deal getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Deal> list = template.query(GET_BY_ID, new DealRowMapper(), id);
		if (list.isEmpty()){
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Deal> getOnApprove() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ON_APPROVE, new DealRowMapper());
	}

	@Override
	public void deleteOnApprove() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ON_APPROVE);
	}

	@Override
	public void add(List<Deal> deals) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(ADD, deals, deals.size(), 
				new ParameterizedPreparedStatementSetter<Deal>() {
					@Override
					public void setValues(PreparedStatement ps, Deal deal) throws SQLException {
						ps.setLong(1, deal.getLotId());
						ps.setLong(2, deal.getTenderId());
						ps.setTimestamp(3, new Timestamp(deal.getTime().getTime()));
						ps.setInt(4, deal.getBuyerChoice().getCode());
						ps.setInt(5, deal.getSellerChoice().getCode());
						ps.setDouble(6, deal.getPrice());
						ps.setInt(7, deal.getVolume());
						ps.setLong(8, deal.getProductId());
						ps.setString(9, deal.getSellerFoto());
						ps.setDouble(10, deal.getMaxTransportPrice());
						ps.setLong(11, deal.getSellerAddressId());
						ps.setLong(12, deal.getBuyerAddressId());
						ps.setString(13, deal.getSellerDescription());
						ps.setString(14, deal.getBuyerDescription());
						
					}
		});
	}

	@Override
	public List<Deal> getByCompanyId(long companyId) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_BY_COMPANY_ID, new DealRowMapper(), companyId, companyId);
	}
	
	@Override
	public void setBuyerStatus(Deal deal) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_BUYER_STATUS, deal.getBuyerChoice().getCode(), deal.getId());
	}
	
	@Override
	public void setSellerStatus(Deal deal) throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_SELLER_STATUS, deal.getSellerChoice().getCode(), deal.getId());
	}
	
	@Override
	public List<Deal> getListBySellerAndBuyerApproved() throws DaoException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_LIST_BY_SELLER_BUYER_APPROVE, new DealRowMapper());
	}

	private static final class DealRowMapper implements RowMapper<Deal>{

		private static final String ALL_COLUMNS = 
				" D.ID, D.LOT_ID, D.TENDER_ID, D.TIME, D.BUYER_CHOICE, D.SELLER_CHOICE, "
				+ " D.PRICE, D.MAX_TRANSPORT_PRICE, D.VOLUME, D.PRODUCT_ID, D.SELLER_FOTO, "
				+ " D.SELLER_ADDRESS_ID, D.BUYER_ADDRESS_ID, D.SELLER_DESCRIPTION, D.BUYER_DESCRIPTION, "
				+ " P.NAME PRODUCT_NAME,  "
				+ " SA.COMPANY_ID SELLER_COMPANY_ID, SA.LATITUDE SELLER_LATITUDE, SA.LONGITUDE SELLER_LONGITUDE, "
				+ " SA.CITY SELLER_CITY, SA.COUNTRY SELLER_COUNTRY, SA.ADDRESS SELLER_ADDRESS, "
				+ " SU.USERNAME SELLER_EMAIL, SC.NAME SELLER_COMPANY_NAME, SC.PHONE SELLER_PHONE, SC.REG_NUMBER SELLER_REG_NUMBER, "
				+ " BA.COMPANY_ID BUYER_COMPANY_ID, BA.LATITUDE BUYER_LATITUDE, BA.LONGITUDE BUYER_LONGITUDE, "
				+ " BA.CITY BUYER_CITY, BA.COUNTRY BUYER_COUNTRY, BA.ADDRESS BUYER_ADDRESS, "
				+ " BU.USERNAME BUYER_EMAIL, BC.NAME BUYER_COMPANY_NAME, BC.PHONE BUYER_PHONE, BC.REG_NUMBER BUYER_REG_NUMBER ";
		
		@Override
		public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
			Deal deal = new Deal();
			
			deal.setId(rs.getLong("ID"));
			deal.setLotId(rs.getLong("LOT_ID"));
			deal.setTenderId(rs.getLong("TENDER_ID"));
			deal.setTime(rs.getTimestamp("TIME"));
			deal.setBuyerChoice(PartnerChoice.valueOf(rs.getInt("BUYER_CHOICE")));
			deal.setSellerChoice(PartnerChoice.valueOf(rs.getInt("SELLER_CHOICE")));
			deal.setPrice(rs.getDouble("PRICE"));
			deal.setMaxTransportPrice(rs.getDouble("MAX_TRANSPORT_PRICE"));
			deal.setVolume(rs.getInt("VOLUME"));
			deal.setProductId(rs.getLong("PRODUCT_ID"));
			deal.setSellerFoto(rs.getString("SELLER_FOTO"));
			deal.setSellerAddressId(rs.getLong("SELLER_ADDRESS_ID"));
			deal.setBuyerAddressId(rs.getLong("BUYER_ADDRESS_ID"));
			deal.setSellerDescription(rs.getString("SELLER_DESCRIPTION"));
			deal.setBuyerDescription(rs.getString("BUYER_DESCRIPTION"));
			
			Product product = new Product();
			product.setId(rs.getLong("PRODUCT_ID"));
			product.setName(rs.getString("PRODUCT_NAME"));
			deal.setProduct(product);
			
			CompanyAddress address = new CompanyAddress();
			address.setCompanyId(rs.getLong("SELLER_COMPANY_ID"));
			address.setLatitude(rs.getDouble("SELLER_LATITUDE"));
			address.setLongitude(rs.getDouble("SELLER_LONGITUDE"));
			address.setCity(rs.getString("SELLER_CITY"));
			address.setCountry(rs.getString("SELLER_COUNTRY"));
			address.setAddress(rs.getString("SELLER_ADDRESS"));
			address.setId(rs.getLong("SELLER_ADDRESS_ID"));
			deal.setSellerAddress(address);
			
			Company company = new Company();
			company.setEmail(rs.getString("SELLER_EMAIL"));
			company.setName(rs.getString("SELLER_COMPANY_NAME"));
			company.setPhone(rs.getString("SELLER_PHONE"));
			company.setRegNumber(rs.getString("SELLER_REG_NUMBER"));
			address.setCompany(company);
			
			address = new CompanyAddress();
			address.setCompanyId(rs.getLong("BUYER_COMPANY_ID"));
			address.setLatitude(rs.getDouble("BUYER_LATITUDE"));
			address.setLongitude(rs.getDouble("BUYER_LONGITUDE"));
			address.setCity(rs.getString("BUYER_CITY"));
			address.setCountry(rs.getString("BUYER_COUNTRY"));
			address.setAddress(rs.getString("BUYER_ADDRESS"));
			address.setId(rs.getLong("BUYER_ADDRESS_ID"));
			deal.setBuyerAddress(address);
			
			company = new Company();
			company.setEmail(rs.getString("BUYER_EMAIL"));
			company.setName(rs.getString("BUYER_COMPANY_NAME"));
			company.setPhone(rs.getString("BUYER_PHONE"));
			company.setRegNumber(rs.getString("BUYER_REG_NUMBER"));
			address.setCompany(company);
			
			return deal;
		}
		
	}
	
	private static final class TradeDealRowMapper implements RowMapper<Void>{

		private SQLException exception = null;
		private Map<Long, Lot> lots;
		private Map<Long, Tender> tenders;
		
		public TradeDealRowMapper(Map<Long, Lot> lots, Map<Long, Tender> tenders){
			this.lots = lots;
			this.tenders = tenders;
		}
		
		@Override
		public Void mapRow(ResultSet rs, int rowNumber) throws SQLException {
			
			long lotId = rs.getLong("LOT_ID");
			Lot lot = lots.computeIfAbsent(lotId, id -> createLotForDeal(id, rs));

			long tenderId = rs.getLong("TENDER_ID");
			Tender tender = tenders.computeIfAbsent(tenderId, id -> createTenderForDeal(id, rs));
			
			tender.addPosiblePartner(lot);
			lot.addPosiblePartner(tender);
			
			if (exception != null){
				throw exception;
			}
			return null;
		}
		
		private Tender createTenderForDeal(Long tenderId, ResultSet rs) {
			try{
				Tender tender = new Tender();
				tender.setId(tenderId);
				tender.setDescription(rs.getString("TENDER_DESCRIPTION"));
				tender.setExparationDate(rs.getDate("TENDER_EXP_DATE"));
				tender.setPrice(rs.getDouble("TENDER_PRICE"));
				tender.setProductId(rs.getLong("PRODUCT_ID"));
				tender.setStatus(BidStatus.valueOf(rs.getString("TENDER_STATUS")));
				tender.setMaxVolume(rs.getInt("TENDER_VOLUME"));
				tender.setMinVolume(rs.getInt("TENDER_MIN_VOLUME"));
				tender.setAddressId(rs.getLong("TENDER_ADDRESS_ID"));
				
				CompanyAddress address = new CompanyAddress();
				address.setLatitude(rs.getDouble("TENDER_LATITUDE"));
				address.setLongitude(rs.getDouble("TENDER_LONGITUDE"));
				address.setCompanyId(rs.getLong("TENDER_COMPANY_ID"));
				address.setId(rs.getLong("TENDER_ADDRESS_ID"));
				tender.setCompanyAddress(address);
				
				return tender;
			}catch (SQLException e) {
				exception = e;
				return null;
			}
		}
		
		private Lot createLotForDeal(Long lotId, ResultSet rs) {
			try{
				Lot lot = new Lot();
				lot.setId(lotId);
				lot.setDescription(rs.getString("LOT_DESCRIPTION"));
				lot.setExparationDate(rs.getDate("LOT_EXP_DATE"));
				lot.setPrice(rs.getDouble("LOT_PRICE"));
				lot.setProductId(rs.getLong("PRODUCT_ID"));
				lot.setStatus(BidStatus.valueOf(rs.getString("LOT_STATUS")));
				lot.setMaxVolume(rs.getInt("LOT_VOLUME"));
				lot.setMinVolume(rs.getInt("LOT_MIN_VOLUME"));
				lot.setAddressId(rs.getLong("LOT_ADDRESS_ID"));
				
				CompanyAddress address = new CompanyAddress();
				address.setLatitude(rs.getDouble("LOT_LATITUDE"));
				address.setLongitude(rs.getDouble("LOT_LONGITUDE"));
				address.setCompanyId(rs.getLong("LOT_COMPANY_ID"));
				address.setId(rs.getLong("LOT_ADDRESS_ID"));
				lot.setCompanyAddress(address);
				
				return lot;
			}catch (SQLException e) {
				exception = e;
				return null;
			}
		}
		
	}
}
