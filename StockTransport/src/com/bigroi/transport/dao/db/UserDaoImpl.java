package com.bigroi.transport.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.bigroi.transport.bean.common.CompanyStatus;
import com.bigroi.transport.bean.db.Address;
import com.bigroi.transport.bean.db.Company;
import com.bigroi.transport.bean.db.TransUser;
import com.bigroi.transport.dao.DaoException;
import com.bigroi.transport.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {
	
	private static final String  GET_ALL_USERS = " SELECT ID, USERNAME, PASSWORD FROM USER ";
	
	private static final String GET_USER_BY_USERNAME = 
			"SELECT U.ID, U.USERNAME, U.PASSWORD, UR.ROLE, "
			+ " C.NAME, C.PHONE, C.REG_NUMBER,"
			+ " C.STATUS, A.CITY, A.COUNTRY, A.ADDRESS, C.ADDRESS_ID, "
			+ " A.LONGITUDE, A.LATITUDE, A.COMPANY_ID "
			+ " FROM  USER U "
			+ " JOIN COMPANY C "
			+ " ON U.COMPANY_ID = C.ID "
			+ " JOIN ADDRESS A "
			+ " ON A.ID = C.ADDRESS_ID "
			+ " LEFT JOIN USER_ROLE UR "
			+ " ON U.ID = UR.USER_ID "
			+ " WHERE U.USERNAME = ? AND C.`STATUS` = '" + CompanyStatus.VERIFIED.name() +"'";
	
	private static final String UPDATE_COUNT_LOGINS_AND_TIME = " UPDATE USER SET LOGIN_COUNT = LOGIN_COUNT + 1, "
			+ " LAST_LOGIN = CURRENT_TIMESTAMP() WHERE ID = ? ";
	
	@Autowired
	public DataSource datasource;
	
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	

	@Override
	public List<TransUser> getAll() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<TransUser> users = template.query(GET_ALL_USERS, new BeanPropertyRowMapper<TransUser>(TransUser.class));
		return users;
	}


	@Override
	public TransUser getByUsernameWithRoles(String username) throws DaoException {
		TransUser user = new TransUser();
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<TransUser> list = template.query(GET_USER_BY_USERNAME, new RowMapper<TransUser>(){
			@Override
			public TransUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				if (user.getUsername() == null){
					user.setId(rs.getLong("ID"));
					user.setCompanyId(rs.getLong("COMPANY_ID"));
					user.setUsername(rs.getString("USERNAME"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setCompanyId(rs.getLong("COMPANY_ID"));
					
					Address address = new Address();
					address.setAddress(rs.getString("ADDRESS"));
					address.setCity(rs.getString("CITY"));
					address.setCompanyId(rs.getLong("COMPANY_ID"));
					address.setCountry(rs.getString("COUNTRY"));
					address.setId(rs.getLong("ADDRESS_ID"));
					address.setLatitude(rs.getDouble("LATITUDE"));
					address.setLongitude(rs.getDouble("LONGITUDE"));
					
					Company company = new Company();
					company.setAddress(address);
					company.setId(rs.getLong("COMPANY_ID"));
					company.setName(rs.getString("NAME"));
					company.setPhone(rs.getString("PHONE"));
					company.setRegNumber(rs.getString("REG_NUMBER"));
					company.setStatus(CompanyStatus.valueOf(rs.getString("STATUS")));
					
					user.setCompany(company);
				}
				String role = rs.getString("ROLE");
				if(role != null){
					user.addAuthority(new SimpleGrantedAuthority(role));
				}
				return user;
			}
		 }, username.toLowerCase());
		if(list.size() == 0){
			return null;
		}else{
			updateCountAndLastTimeForLogin(user);
			return list.get(0);
		}
	}
	
	private boolean updateCountAndLastTimeForLogin(TransUser user) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		return template.update(UPDATE_COUNT_LOGINS_AND_TIME, user.getId()) == 0;
	}

}
