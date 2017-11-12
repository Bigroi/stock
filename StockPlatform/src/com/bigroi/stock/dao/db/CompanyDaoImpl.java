package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;

public class CompanyDaoImpl implements CompanyDao {
	
	private static final String SELECT_COMPANY_BY_ID = "SELECT id, name, email, phone, "
			+ "reg_number, country, city, status FROM company WHERE id = ?";

	private static final String ADD_COMPANY_BY_ID = "INSERT INTO company"
			+ " (id, name, email, phone, reg_number, country, city,status) " 
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_COMPANY_BY_ID = "UPDATE company SET  name = ?, "
			+ "email = ?, phone = ?, reg_number = ?, country = ?, city = ?, "
			+ "status = ? WHERE id = ? ";
	private static final String SELECT_ALL_COMPANY_LIST ="SELECT id, name, email, "
			+ "phone, reg_number, country, city, status FROM company";
	
	private static final String SET_STATUS_BY_ID = 
			"UPDATE company SET "
			+ "status = ? "
			+ "WHERE id = ?";
	
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
		List<Company> companys = template.query(SELECT_COMPANY_BY_ID, new BeanPropertyRowMapper<Company>(Company.class),id);
		if (companys.size() == 0) {
			return null;
		} else {
			return companys.get(0);
		}
		
	}

	@Override
	public void add(Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_COMPANY_BY_ID, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setLong(1, company.getId());
				ps.setString(2, company.getName());
				ps.setString(3, company.getEmail());
				ps.setString(4, company.getPhone());
				ps.setString(5, company.getRegNumber());
				ps.setString(6, company.getCountry());
				ps.setString(7, company.getCity());
				ps.setString(8, company.getStatus().name().toUpperCase());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		company.setId(id);
	}

	@Override
	public boolean update(Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_COMPANY_BY_ID, company.getName(), company.getEmail(),
				company.getPhone(), company.getRegNumber(), company.getCountry(), 
				company.getCity(),company.getStatus().name().toUpperCase(), 
				company.getId()) == 1;
	}

	@Override
	public List<Company> getAllCompany() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.query(SELECT_ALL_COMPANY_LIST, 
				new BeanPropertyRowMapper<Company>(Company.class));
	}

	@Override
	public void setStatus(long id, CompanyStatus status) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(SET_STATUS_BY_ID, status.toString(), id);
	}

	
	

}
