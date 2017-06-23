package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

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

	private static final String SELECT_COMPANY_BY_ID = "SELECT id,name,email,phone,"
			+ "reg_number,country,city,user_id  FROM company WHERE id = ?";

	private static final String DELETE_COMPANY_BY_ID = "DELETE FROM company " 
	        + "WHERE id = ?";

	private static final String ADD_COMPANY_BY_ID = "INSERT INTO company"
			+ " (id,name,email,phone,reg_number,country,city,user_id) " 
			+ "VALUES(?,?,?,?,?,?,?,?)";

	private static final String UPDATE_COMPANY_BY_ID = "UPDATE company SET "
			+ "id=?,name=?,email=?,phone=?,reg_number=?,country=?,city=?," 
			+ "user_id=? WHERE id =?";

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
		Company company = template.queryForObject(SELECT_COMPANY_BY_ID, new Object[] { id },
				new BeanPropertyRowMapper<Company>(Company.class));
		return company;
	}

	@Override
	public void add(Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_COMPANY_BY_ID, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, company.getId());
				ps.setString(2, company.getName());
				ps.setString(3, company.getEmail());
				ps.setInt(4, company.getPhone());
				ps.setString(5, company.getRegNumber());
				ps.setString(6, company.getCountry());
				ps.setString(7, company.getCity());
				ps.setLong(8, company.getUserId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		company.setId(id);
	}

	@Override
	public void delete(long id) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(DELETE_COMPANY_BY_ID, id);
	}

	@Override
	public void update(long id, Company company) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		template.update(UPDATE_COMPANY_BY_ID, company.getId(), company.getName(), company.getEmail(),
				company.getPhone(), company.getRegNumber(), company.getCountry(), company.getCity(),
				company.getUserId(), id);
	}

}
