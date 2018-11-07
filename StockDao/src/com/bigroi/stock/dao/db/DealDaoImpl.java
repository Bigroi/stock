package com.bigroi.stock.dao.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.PartnerChoice;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.TradeBid;
import com.bigroi.stock.bean.db.TradeLot;
import com.bigroi.stock.bean.db.TradeTender;
import com.bigroi.stock.dao.DealDao;

@Repository
public class DealDaoImpl implements DealDao {
	
	private static final String ADD = 
			"INSERT INTO DEAL (LOT_ID, TENDER_ID, TIME, BUYER_CHOICE, SELLER_CHOICE, PRICE, "
			+ " MAX_TRANSPORT_PRICE, VOLUME, PRODUCT_ID, SELLER_FOTO, SELLER_ADDRESS_ID, "
			+ " BUYER_ADDRESS_ID, SELLER_DESCRIPTION, BUYER_DESCRIPTION, BUYER_PROCESSING, BUYER_PACKAGING, "
			+ " BUYER_LANGUAGE, SELLER_LANGUAGE, BUYER_EMAIL, SELLER_EMAIL, CATEGORY_ID ) "
			+ " SELECT L.ID, T.ID, ?, ?, ?, ?, "
			+ " ?, ?, L.PRODUCT_ID, L.FOTO, L.ADDRESS_ID, "
			+ " T.ADDRESS_ID, L.DESCRIPTION, T.DESCRIPTION, T.PROCESSING, T.PACKAGING, "
			+ " ?, ?, ?, ?, L.CATEGORY_ID "
			+ " FROM LOT L "
			+ " JOIN TENDER T "
			+ " ON L.PRODUCT_ID = T.PRODUCT_ID "
			+ " WHERE L.ID = ? AND T.ID = ?";
	
	private static final String GET_POSIBLE_BIDS = 
			"SELECT "
			+ " L.ID LOT_ID, L.PRICE LOT_PRICE, L.MAX_VOLUME LOT_VOLUME, "
			+ " L.MIN_VOLUME LOT_MIN_VOLUME, L.CREATION_DATE LOT_CREATION_DATE, "
			+ " L.DISTANCE LOT_DISTANCE, L.ADDRESS_ID LOT_ADDRESS_ID, "
			+ " LA.LONGITUDE LOT_LONGITUDE, LA.LATITUDE LOT_LATITUDE, LA.COMPANY_ID LOT_COMPANY_ID, "
			+ " LC.LANGUAGE LOT_LANGUAGE, LU.USERNAME LOT_EMAIL,"

			+ " T.ID TENDER_ID, T.PRICE TENDER_PRICE, T.MAX_VOLUME TENDER_VOLUME, "
			+ " T.MIN_VOLUME TENDER_MIN_VOLUME, T.CREATION_DATE TENDER_CREATION_DATE, "
			+ " T.DISTANCE TENDER_DISTANCE, T.ADDRESS_ID TENDER_ADDRESS_ID, "
			+ " TA.LONGITUDE TENDER_LONGITUDE, TA.LATITUDE TENDER_LATITUDE, TA.COMPANY_ID TENDER_COMPANY_ID, "
			+ " TC.LANGUAGE TENDER_LANGUAGE, TU.USERNAME TENDER_EMAIL,"
			  
			+ " P.ID PRODUCT_ID, P.DELIVARY_PRICE DELIVARY_PRICE "
			+ " FROM LOT L "
			+ " JOIN COMPANY LC "
			+ " ON LC.ID = L.COMPANY_ID "
			+ " JOIN ADDRESS LA "
			+ " ON L.ADDRESS_ID = LA.ID "
			+ " AND L.`STATUS` = 'ACTIVE' "
			+ " AND L.MAX_VOLUME >= L.MIN_VOLUME "
			+ " AND LOT_SPEC_CONDITION "
			
			+ " JOIN TENDER T "
			+ " ON L.PRODUCT_ID = T.PRODUCT_ID "
			+ " AND (L.CATEGORY_ID = T.CATEGORY_ID OR T.CATEGORY_ID IS NULL)"
			+ " AND T.`STATUS` = 'ACTIVE' "
			+ " AND T.MAX_VOLUME >= T.MIN_VOLUME "
			+ " AND T.PRICE >= L.PRICE "
			+ " AND T.MIN_VOLUME <= L.MAX_VOLUME "
			+ " AND L.MIN_VOLUME <= T.MAX_VOLUME "
			+ " AND TENDER_SPEC_CONDITION "
			+ " COMPAMY_CONDITION "
			
			+ " JOIN COMPANY TC "
			+ " ON TC.ID = T.COMPANY_ID "
			
			+ " JOIN ADDRESS TA "
			+ " ON T.ADDRESS_ID = TA.ID "
			+ " LEFT JOIN BLACK_LIST BL "
			+ " ON BL.TENDER_ID = T.ID AND BL.LOT_ID = L.ID "
			+ " JOIN PRODUCT P "
			+ " ON P.ID = T.PRODUCT_ID "
			+ " JOIN USER LU "
			+ " ON LU.COMPANY_ID = LC.ID "
			+ " JOIN USER TU "
			+ " ON TU.COMPANY_ID = TC.ID "
			+ " WHERE BL.ID IS NULL AND T.PRODUCT_ID = ?";

	private static final String GET_BY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
			+ DealRowMapper.FROM
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
						+ DealRowMapper.FROM
					    + " WHERE " + ON_APPROVE_CRITERIA;
	

	private static final String DELETE_ON_APPROVE = 
					  " DELETE "
					+ " FROM DEAL "
					+ " WHERE " + ON_APPROVE_CRITERIA;

	private static final String GET_BY_COMPANY_ID = 
			" SELECT " + DealRowMapper.ALL_COLUMNS
			+ DealRowMapper.FROM
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
			+ DealRowMapper.FROM
			+ "WHERE ((BUYER_CHOICE | SELLER_CHOICE) & 15) = 8";


	@Autowired
	private DataSource datasource;
	
	@Override
	public void getTestPossibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId, String sessionId){
		String sql = GET_POSIBLE_BIDS
				.replace("COMPAMY_CONDITION", "")
				.replace("LOT_SPEC_CONDITION", " L.DESCRIPTION = '" + sessionId + "' ")
				.replaceAll("TENDER_SPEC_CONDITION", " T.DESCRIPTION = '" + sessionId + "' ");
		getTestPossibleDeals(sql, tradeLots, tradeTenders, productId);
	}
	
	@Override
	public void getPosibleDeals(List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId) {
		String sql = GET_POSIBLE_BIDS
				.replace("COMPAMY_CONDITION", "AND T.COMPANY_ID <> L.COMPANY_ID ")
				.replace("LOT_SPEC_CONDITION", " L.COMPANY_ID <> 0 ")
				.replaceAll("TENDER_SPEC_CONDITION", " T.COMPANY_ID <> 0 ");
		getTestPossibleDeals(sql, tradeLots, tradeTenders, productId);
	}
	
	private void getTestPossibleDeals(String sql, List<TradeLot> tradeLots, List<TradeTender> tradeTenders, long productId){
		JdbcTemplate template = new JdbcTemplate(datasource);
		Map<Long, TradeLot> lots = new HashMap<>();
		Map<Long, TradeTender> tenders = new HashMap<>();
		template.query(sql, new TradeDealRowMapper(lots, tenders), productId);
		
		tradeLots.addAll(lots.values());
		tradeTenders.addAll(tenders.values());
	}

	@Override
	public Deal getById(long id) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Deal> list = template.query(GET_BY_ID, new DealRowMapper(), id);
		if (list.isEmpty()){
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Deal> getOnApprove() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ON_APPROVE, new DealRowMapper());
	}

	@Override
	public void deleteOnApprove() {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_ON_APPROVE);
	}

	@Override
	public void add(List<Deal> deals) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.batchUpdate(ADD, deals, deals.size(), 
				new ParameterizedPreparedStatementSetter<Deal>() {
					@Override
					public void setValues(PreparedStatement ps, Deal deal) throws SQLException {
						ps.setTimestamp(1, new Timestamp(deal.getTime().getTime()));
						ps.setInt(2, deal.getBuyerChoice().getCode());
						ps.setInt(3, deal.getSellerChoice().getCode());
						ps.setDouble(4, deal.getPrice());
						ps.setDouble(5, deal.getMaxTransportPrice());
						ps.setInt(6, deal.getVolume());
						ps.setString(7, deal.getBuyerLanguage());
						ps.setString(8, deal.getSellerLanguage());
						ps.setString(9, deal.getBuyerEmail());
						ps.setString(10, deal.getSellerEmail());
						ps.setLong(11, deal.getLotId());
						ps.setLong(12, deal.getTenderId());
					}
		});
	}

	@Override
	public List<Deal> getByCompanyId(long companyId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_BY_COMPANY_ID, new DealRowMapper(), companyId, companyId);
	}
	
	@Override
	public void setBuyerStatus(Deal deal) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_BUYER_STATUS, deal.getBuyerChoice().getCode(), deal.getId());
	}
	
	@Override
	public void setSellerStatus(Deal deal) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		jdbcTemplate.update(SET_SELLER_STATUS, deal.getSellerChoice().getCode(), deal.getId());
	}
	
	@Override
	public List<Deal> getListBySellerAndBuyerApproved() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		return jdbcTemplate.query(GET_LIST_BY_SELLER_BUYER_APPROVE, new DealRowMapper());
	}

	private static final class DealRowMapper implements RowMapper<Deal>{

		public static final String FROM = 
						  " FROM DEAL D "
						+ " JOIN PRODUCT P "
						+ " ON D.PRODUCT_ID = P.ID "
						+ " JOIN PRODUCT_CATEGORY PC "
						+ " ON PC.ID = D.CATEGORY_ID "
						+ " JOIN ADDRESS SA "
						+ " ON SA.ID = D.SELLER_ADDRESS_ID "
						+ " JOIN COMPANY SC "
						+ " ON SA.COMPANY_ID = SC.ID "
						+ " JOIN ADDRESS BA "
						+ " ON BA.ID = D.BUYER_ADDRESS_ID "
						+ " JOIN COMPANY BC "
						+ " ON BA.COMPANY_ID = BC.ID ";
		
		private static final String ALL_COLUMNS = 
				" D.ID, D.LOT_ID, D.TENDER_ID, D.TIME, D.BUYER_CHOICE, D.SELLER_CHOICE, "
				+ " D.PRICE, D.MAX_TRANSPORT_PRICE, D.VOLUME, D.PRODUCT_ID, D.SELLER_FOTO, "
				+ " D.SELLER_ADDRESS_ID, D.BUYER_ADDRESS_ID, D.SELLER_DESCRIPTION, "
				+ " D.BUYER_DESCRIPTION, D.BUYER_PACKAGING, D.BUYER_PROCESSING, "
				+ " D.BUYER_LANGUAGE, D.SELLER_LANGUAGE, D.BUYER_EMAIL, D.SELLER_EMAIL, D.CATEGORY_ID, "
				+ " P.NAME PRODUCT_NAME, PC.CATEGORY_NAME CATEGORY_NAME, "
				+ " SA.COMPANY_ID SELLER_COMPANY_ID, SA.LATITUDE SELLER_LATITUDE, SA.LONGITUDE SELLER_LONGITUDE, "
				+ " SA.CITY SELLER_CITY, SA.COUNTRY SELLER_COUNTRY, SA.ADDRESS SELLER_ADDRESS, "
				+ " SC.NAME SELLER_COMPANY_NAME, SC.PHONE SELLER_PHONE, SC.REG_NUMBER SELLER_REG_NUMBER, SC.LANGUAGE SELLER_LANGUAGE, "
				+ " BA.COMPANY_ID BUYER_COMPANY_ID, BA.LATITUDE BUYER_LATITUDE, BA.LONGITUDE BUYER_LONGITUDE, "
				+ " BA.CITY BUYER_CITY, BA.COUNTRY BUYER_COUNTRY, BA.ADDRESS BUYER_ADDRESS, "
				+ " BC.NAME BUYER_COMPANY_NAME, BC.PHONE BUYER_PHONE, BC.REG_NUMBER BUYER_REG_NUMBER, BC.LANGUAGE BUYER_LANGUAGE ";
		
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
			deal.setSellerDescription(rs.getString("SELLER_DESCRIPTION"));
			deal.setBuyerDescription(rs.getString("BUYER_DESCRIPTION"));
			deal.setBuyerProcessing(rs.getString("BUYER_PROCESSING"));
			deal.setBuyerPackaging(rs.getString("BUYER_PACKAGING"));
			deal.setBuyerEmail(rs.getString("BUYER_EMAIL"));
			deal.setSellerEmail(rs.getString("SELLER_EMAIL"));
			deal.setCategoryId(rs.getLong("CATEGORY_ID"));
			
			deal.setCategoryName(rs.getString("CATEGORY_NAME"));
			deal.setProductName(rs.getString("PRODUCT_NAME"));
			
			deal.setSellerCountry(rs.getString("SELLER_COUNTRY"));
			deal.setSellerCity(rs.getString("SELLER_CITY"));
			deal.setSellerAddress(rs.getString("SELLER_ADDRESS"));
			deal.setSellerLatitude(rs.getDouble("SELLER_LATITUDE"));
			deal.setSellerLongitude(rs.getDouble("SELLER_LONGITUDE"));
			
			deal.setSellerCompanyId(rs.getLong("SELLER_COMPANY_ID"));
			deal.setSellerCompanyName(rs.getString("SELLER_COMPANY_NAME"));
			deal.setSellerPhone(rs.getString("SELLER_PHONE"));
			deal.setSellerRegNumber(rs.getString("SELLER_REG_NUMBER"));
			deal.setSellerLanguage(rs.getString("SELLER_LANGUAGE"));
			
			deal.setBuyerCountry(rs.getString("BUYER_COUNTRY"));
			deal.setBuyerCity(rs.getString("BUYER_CITY"));
			deal.setBuyerAddress(rs.getString("BUYER_ADDRESS"));
			deal.setBuyerLatitude(rs.getDouble("BUYER_LATITUDE"));
			deal.setBuyerLongitude(rs.getDouble("BUYER_LONGITUDE"));
			
			deal.setBuyerCompanyId(rs.getLong("BUYER_COMPANY_ID"));
			deal.setBuyerCompanyName(rs.getString("BUYER_COMPANY_NAME"));
			deal.setBuyerPhone(rs.getString("BUYER_PHONE"));
			deal.setBuyerRegNumber(rs.getString("BUYER_REG_NUMBER"));
			deal.setBuyerLanguage(rs.getString("BUYER_LANGUAGE"));
			
			return deal;
		}
		
	}
	
	private static final class TradeDealRowMapper implements RowMapper<Void>{

		private SQLException exception = null;
		private Map<Long, TradeLot> lots;
		private Map<Long, TradeTender> tenders;
		
		public TradeDealRowMapper(Map<Long, TradeLot> lots, Map<Long, TradeTender> tenders){
			this.lots = lots;
			this.tenders = tenders;
		}
		
		@Override
		public Void mapRow(ResultSet rs, int rowNumber) throws SQLException {
			
			long lotId = rs.getLong("LOT_ID");
			TradeLot lot = lots.computeIfAbsent(lotId, id -> 
				(TradeLot)createBidForDeal(TradeLot::new, id, rs, "LOT"));

			long tenderId = rs.getLong("TENDER_ID");
			TradeTender tender = tenders.computeIfAbsent(tenderId, id -> 
				(TradeTender)createBidForDeal(TradeTender::new, id, rs, "TENDER"));
			
			tender.addPosiblePartner(lot);
			lot.addPosiblePartner(tender);
			
			if (exception != null){
				throw exception;
			}
			return null;
		}
		
		private TradeBid createBidForDeal(Supplier<TradeBid> constructor, Long id, ResultSet rs, String prefix) {
			try{
				TradeBid bid = constructor.get();
				bid.setId(id);
				bid.setPrice(rs.getDouble(prefix + "_PRICE"));
				bid.setProductId(rs.getLong("PRODUCT_ID"));
				bid.setMaxVolume(rs.getInt(prefix + "_VOLUME"));
				bid.setMinVolume(rs.getInt(prefix + "_MIN_VOLUME"));
				bid.setLatitude(rs.getDouble(prefix + "_LATITUDE"));
				bid.setLongitude(rs.getDouble(prefix + "_LONGITUDE"));
				bid.setCreationDate(rs.getDate(prefix + "_CREATION_DATE"));
				bid.setDelivaryPrice(rs.getDouble("DELIVARY_PRICE"));
				bid.setDistance(rs.getInt(prefix + "_DISTANCE"));
				bid.setLanguage(rs.getString(prefix + "_LANGUAGE"));
				bid.setEmail(rs.getString(prefix + "_EMAIL"));
				bid.setAddressId(rs.getLong(prefix + "_ADDRESS_ID"));
				
				return bid;
			}catch (SQLException e) {
				exception = e;
				return null;
			}
		}
		
	}
}
