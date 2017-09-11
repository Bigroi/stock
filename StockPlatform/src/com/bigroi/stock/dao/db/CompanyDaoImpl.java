package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.Company;
import com.bigroi.stock.dao.CompanyDao;
import com.bigroi.stock.dao.DaoException;
import com.mysql.jdbc.Statement;

public class CompanyDaoImpl implements CompanyDao {
	
	private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class);

	private static final String SELECT_COMPANY_BY_ID = "SELECT id, name, email, phone, "
			+ "reg_number, country, city, status FROM company WHERE id = ?";

	private static final String DELETE_COMPANY_BY_ID = "DELETE FROM company " 
	        + "WHERE id = ?";

	private static final String ADD_COMPANY_BY_ID = "INSERT INTO company"
			+ " (id, name, email, phone, reg_number, country, city,status) " 
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_COMPANY_BY_ID = "UPDATE company SET  name = ?, "
			+ "email = ?, phone = ?, reg_number = ?, country = ?, city = ?, "
			+ "status = ? WHERE id = ? ";
	private static final String SELECT_ALL_COMPANY_LIST ="SELECT id, name, email, "
			+ "phone, reg_number, country, city, status FROM company";

	private DataSource datasource;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public Company getById(long id) throws DaoException {
		logger.info("exection CompanyDaoImpl.getById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Company> companys = template.query(SELECT_COMPANY_BY_ID, new BeanPropertyRowMapper<Company>(Company.class),id);
		if (companys.size() == 0) {
			logger.info("exection CompanyDaoImpl.getById return null, successfully finished");
			return null;
		} else {
			logger.info("exection CompanyDaoImpl.getById return companys.get(0), successfully finished");
			return companys.get(0);
		}
		
	}

	@Override
	public void add(Company company) throws DaoException {
		logger.info("exection CompanyDaoImpl.add");
		logger.info(company);
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_COMPANY_BY_ID, Statement.RETURN_GENERATED_KEYS);
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
		logger.info("exection CompanyDaoImpl.add successfully finished");
	}

	@Override
	public boolean deletedById(long id) throws DaoException {
		logger.info("exection CompanyDaoImpl.deletedById");
		logger.info(id);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection CompanyDaoImpl.deletedById successfully finished");
		return template.update(DELETE_COMPANY_BY_ID, id) == 1;
	}

	@Override
	public boolean updateById(Company company) throws DaoException {
		logger.info("exection CompanyDaoImpl.updateById");
		logger.info(company);
		JdbcTemplate template = new JdbcTemplate(datasource);
		logger.info("exection CompanyDaoImpl.updateById successfully finished");
		return template.update(UPDATE_COMPANY_BY_ID, company.getName(), company.getEmail(),
				company.getPhone(), company.getRegNumber(), company.getCountry(), 
				company.getCity(),company.getStatus().name().toUpperCase(), 
				company.getId()) == 1;
	}

	@Override
	public List<Company> getAllCompany() throws DaoException {
		logger.info("exection CompanyDaoImpl.getAllCompany");
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Company> list = template.query(SELECT_ALL_COMPANY_LIST, 
				new BeanPropertyRowMapper<Company>(Company.class));
		logger.info("exection CompanyDaoImpl.getAllCompany successfully finished");
		return list;
	}

}
