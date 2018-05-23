package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.dao.DaoException;

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
	
	private static final String GET_ADDRESSES_BY_ID = "SELECT ID, CITY, COUNTRY, "
			+ " ADDRESS, LATITUDE, LONGITUDE FROM ADDRESS WHERE ID = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	@Override
	public List<Address> getAddressesForCompany(long companyId) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ADDRESSES_BY_COMPANY_ID, new BeanPropertyRowMapper<Address>(Address.class), companyId);
	}
	
	@Override
	public boolean updateAddress(Address address) throws DaoException{
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
	public void addAddress(Address address) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(ADD_ADDRESS, 
				address.getCity(),
				address.getCountry(),
				address.getAddress(),
				address.getLatitude(),
				address.getLongitude(),
				address.getCompanyId());
	}
	
	@Override
	public boolean deleteAddress(long id, long companyId) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(DELETE_ADDRESS_BY_ID_AND_COMPANY_ID, companyId, id) == 1;
	}

	@Override
	public Address getAddressById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Address> listAddresses = template.query(GET_ADDRESSES_BY_ID, 
				new BeanPropertyRowMapper<Address>(Address.class),id);
		if(listAddresses.size() == 0){
			return null;
		}else{
			return listAddresses.get(0);
		}
	}
}
