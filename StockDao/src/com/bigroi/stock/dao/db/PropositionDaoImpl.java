package com.bigroi.stock.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.common.PropositionStatus;
import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.Deal;
import com.bigroi.stock.bean.db.Product;
import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PropositionDao;

@Repository
public class PropositionDaoImpl implements PropositionDao {
	
	private static final String GET_APPROVE_PROPOSITIONS = 
			" SELECT " + PropostionRowMapper.ALL_COLUMNS + " "
			+ " FROM TRANSPORT_PROPOSITION T  "
			+ " JOIN DEAL D ON T.DEAL_ID = D.ID "
			+ " JOIN PRODUCT P ON D.PRODUCT_ID = P.ID  "
			+ " JOIN ADDRESS BA ON BA.ID = D.BUYER_ADDRESS_ID "
			+ " JOIN ADDRESS SA ON SA.ID = D.SELLER_ADDRESS_ID "
			+ " WHERE T.STATUS = '"+ PropositionStatus.APPROVE +"' ";
	
	private static final String DELETE_PROPOSITION_BY_DEAL_ID_AND_COMPANY_ID = 
			" DELETE FROM TRANSPORT_PROPOSITION WHERE "
			+ " DEAL_ID = ? AND COMPANY_ID = ? ";
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<Proposition> getListPropositions() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		List<Proposition> list = template.query(GET_APPROVE_PROPOSITIONS, 
				new PropostionRowMapper());
		return list;
	}
	
	@Override
	public boolean deleteProposition(long dealId, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		return template.update(DELETE_PROPOSITION_BY_DEAL_ID_AND_COMPANY_ID, dealId, companyId) == 1;
	}
	
	private static final class PropostionRowMapper implements RowMapper<Proposition>{
		
		private static final String ALL_COLUMNS = 
			"T.ID, T.PRICE, T.DEAL_ID, "
			+ " D.VOLUME, "
			+ " P.NAME, "
			+ " BA.CITY BUYER_CITY, BA.COUNTRY BUYER_COUNTRY, BA.ADDRESS BUYER_ADDRESS, BA.LATITUDE BUYER_LATITUDE, BA.LONGITUDE BUYER_LONGITUDE, "
			+ " SA.CITY SELLER_CITY, SA.COUNTRY SELLER_COUNTRY, SA.ADDRESS SELLER_ADDRESS, SA.LATITUDE SELLER_LATITUDE, SA.LONGITUDE SELLER_LONGITUDE ";

		@Override
		public Proposition mapRow(ResultSet rs, int rowNum) throws SQLException {
			Proposition prop = new Proposition();
			prop.setId(rs.getLong("ID"));
			prop.setDealId(rs.getLong("DEAL_ID"));
			prop.setPrice(rs.getInt("PRICE"));
			
			Deal deal = new Deal();
			deal.setVolume(rs.getInt("VOLUME"));
			prop.setDeal(deal);
			
			Product product = new Product();
			product.setName(rs.getString("NAME"));
			prop.setProduct(product);
			
			Address buyerAddress = new Address();
			buyerAddress.setCity(rs.getString("BUYER_CITY"));
			buyerAddress.setCountry(rs.getString("BUYER_COUNTRY"));
			buyerAddress.setAddress(rs.getString("BUYER_ADDRESS"));
			buyerAddress.setLatitude(rs.getDouble("BUYER_LATITUDE"));
			buyerAddress.setLongitude(rs.getDouble("BUYER_LONGITUDE"));
			prop.setBuyerAddress(buyerAddress);
			
			Address sellerAddress = new Address();
			sellerAddress.setCity(rs.getString("SELLER_CITY"));
			sellerAddress.setCountry(rs.getString("SELLER_COUNTRY"));
			sellerAddress.setAddress(rs.getString("SELLER_ADDRESS"));
			sellerAddress.setLatitude(rs.getDouble("SELLER_LATITUDE"));
			sellerAddress.setLongitude(rs.getDouble("SELLER_LONGITUDE"));
			prop.setSellerAaddress(sellerAddress);
			return prop;
		}
	}
}
