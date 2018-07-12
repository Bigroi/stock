package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.DaoException;

@Repository
public class AddressDaoImpl implements AddressDao{

	private static final String GET_ADDRESSES_BY_COMPANY_ID = 
			"SELECT ID, CITY, COUNTRY, ADDRESS, LATITUDE, LONGITUDE, COMPANY_ID "
			+ " FROM ADDRESS "
			+ " WHERE COMPANY_ID = ?";
	
	private static final String DELETE_ADDRESS_BY_ID_AND_COMPANY_ID = 
			"DELETE "
			+ " FROM ADDRESS "
			+ " WHERE COMPANY_ID = ? AND ID = ?";
	
	private static final String UPDATE_ADDRESS_BY_ID_AND_COMPANY_ID = 
			"UPDATE ADDRESS "
			+ " SET CITY = ?, COUNTRY = ?, ADDRESS = ?, LATITUDE = ?, LONGITUDE = ? "
			+ " WHERE COMPANY_ID = ? AND ID = ?";
	
	private static final String ADD_ADDRESS = 
			"INSERT INTO ADDRESS (CITY, COUNTRY, ADDRESS, LATITUDE, LONGITUDE, COMPANY_ID)"
			+ " VALUES (?, ?, ?, ?, ? , ?) ";
	
	private static final String GET_ADDRESS_BY_ID = "SELECT ID, CITY, COUNTRY, "
			+ " ADDRESS, LATITUDE, LONGITUDE, COMPANY_ID FROM ADDRESS WHERE ID = ? AND COMPANY_ID = ?";
	
	@Autowired
	private DataSource datasource;

	@Override
	public List<CompanyAddress> getAddressesForCompany(long companyId) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ADDRESSES_BY_COMPANY_ID, new BeanPropertyRowMapper<CompanyAddress>(CompanyAddress.class), companyId);
	}
	
	@Override
	public boolean updateAddress(CompanyAddress address) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_ADDRESS_BY_ID_AND_COMPANY_ID, 
				address.getCity(),
				address.getCountry(),
				address.getAddress(),
				address.getLatitude(),
				address.getLongitude(),
				address.getCompanyId(),
				address.getId()) == 1;
	}
	
	@Override
	public void addAddress(CompanyAddress address) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_ADDRESS, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, address.getCity());
				ps.setString(2, address.getCountry());
				ps.setString(3, address.getAddress());
				ps.setDouble(4, address.getLatitude());
				ps.setDouble(5, address.getLongitude());
				ps.setLong(6, address.getCompanyId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		address.setId(id);
	}
		
	@Override
	public boolean deleteAddress(long id, long companyId) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_ADDRESS_BY_ID_AND_COMPANY_ID, companyId, id) == 1;
	}

	@Override
	public CompanyAddress getAddressById(long id, long companyId) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<CompanyAddress> listAddresses = template.query(GET_ADDRESS_BY_ID, 
				new BeanPropertyRowMapper<CompanyAddress>(CompanyAddress.class), id, companyId);
		if(listAddresses.isEmpty()){
			return null;
		}else{
			return listAddresses.get(0);
		}
	}
}
