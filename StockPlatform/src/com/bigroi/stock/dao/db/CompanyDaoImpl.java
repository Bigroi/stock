package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;

public class CompanyDaoImpl implements CompanyDao {
	
	private static final String GET_COMPANY_BY_ID = 
			  " SELECT C.ID, C.NAME, C.PHONE, C.REG_NUMBER, C.COUNTRY, C.CITY, "
			+ " C.ADDRESS, C.STATUS, C.LONGITUDE, C.LATITUDE, U.LOGIN "
			+ " FROM COMPANY C "
			+ " JOIN USER U "
			+ " ON C.ID = U.COMPANY_ID "
			+ " WHERE C.ID = ?";

	private static final String ADD_COMPANY = "INSERT INTO COMPANY"
			+ " (NAME, PHONE, REG_NUMBER, COUNTRY, CITY, ADDRESS, STATUS, LONGITUDE, LATITUDE ) " 
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_COMPANY_BY_ID = "UPDATE COMPANY SET  NAME = ?, "
			+ " PHONE = ?, REG_NUMBER = ?, COUNTRY = ?, CITY = ?, ADDRESS = ?, "
			+ "STATUS = ?, LONGITUDE = ?, LATITUDE = ? WHERE ID = ? ";
	
	private static final String GET_ALL_COMPANIES ="SELECT ID, NAME, SELLER_ID, CUSTOMER_ID, "
			+ "PHONE, REG_NUMBER, COUNTRY, CITY, ADDRESS, STATUS, LONGITUDE, LATITUDE FROM COMPANY";
	
	private static final String SET_STATUS_BY_ID = 
			"UPDATE COMPANY SET "
			+ "STATUS = ? "
			+ "WHERE ID = ?";
	
	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public Company getById(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		final Company company = new Company();
		template.query(GET_COMPANY_BY_ID, new RowMapper<Company>(){
			@Override
			public Company mapRow(ResultSet rs, int arg1) throws SQLException {
				if (company.getName() == null){
					company.setAddress(rs.getString("ADDRESS"));
					company.setCity(rs.getString("CITY"));
					company.setCountry(rs.getString("COUNTRY"));
					company.setId(rs.getInt("ID"));
					company.setLatitude(rs.getDouble("LATITUDE"));
					company.setLongitude(rs.getDouble("LONGITUDE"));
					company.setName(rs.getString("NAME"));
					company.setPhone(rs.getString("PHONE"));
					company.setRegNumber(rs.getString("REG_NUMBER"));
					company.setStatus(CompanyStatus.valueOf(rs.getString("STATUS")));
				}
				company.addEmail(rs.getString("LOGIN"));
				return company;
			}
		},id);
		return company;
		
	}

	@Override
	public void add(Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_COMPANY, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, company.getName());
				ps.setString(2, company.getPhone());
				ps.setString(3, company.getRegNumber());
				ps.setString(4, company.getCountry());
				ps.setString(5, company.getCity());
				ps.setString(6, company.getAddress());
				ps.setString(7, company.getStatus().name().toUpperCase());
				ps.setDouble(8, company.getLongitude());
				ps.setDouble(9, company.getLatitude());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		company.setId(id);
	}

	@Override
	public boolean update(Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_COMPANY_BY_ID, company.getName(), 
				company.getPhone(), company.getRegNumber(), company.getCountry(), 
				company.getCity(), company.getAddress(), company.getStatus().name().toUpperCase(), 
				company.getLongitude(), company.getLatitude(),
				company.getId()) == 1;
	}

	@Override
	public List<Company> getAllCompany() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(GET_ALL_COMPANIES, 
				new BeanPropertyRowMapper<Company>(Company.class));
	}

	@Override
	public void setStatus(long id, CompanyStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(SET_STATUS_BY_ID, status.toString(), id);
	}

	
	

}
