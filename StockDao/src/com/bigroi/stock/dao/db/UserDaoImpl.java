package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final String UPDATE_USER = "UPDATE USER ";

    private static final String ADD_USER =
            "INSERT INTO USER (USERNAME, PASSWORD, COMPANY_ID) "
                    + " VALUES (?, ?, ?) ";

    private static final String UPDATE_USER_BY_ID =
            UPDATE_USER
                    + "SET USERNAME = ?, PASSWORD = ?, COMPANY_ID = ? "
                    + "WHERE ID = ? ";

    private static final String GET_USER_BY_USERNAME =
            "SELECT U.ID, U.USERNAME, U.PASSWORD, UR.ROLE, "
                    + " C.NAME, C.PHONE, C.REG_NUMBER, C.LANGUAGE, "
                    + " C.STATUS, A.CITY, A.COUNTRY, A.ADDRESS, C.ADDRESS_ID, "
                    + " A.LONGITUDE, A.LATITUDE, A.COMPANY_ID "
                    + " FROM  USER U "
                    + " JOIN COMPANY C "
                    + " ON U.COMPANY_ID = C.ID "
                    + " JOIN ADDRESS A "
                    + " ON A.ID = C.ADDRESS_ID "
                    + " LEFT JOIN USER_ROLE UR "
                    + " ON U.ID = UR.USER_ID "
                    + " WHERE U.USERNAME = ? AND "
                    + " (C.`STATUS` = '" + CompanyStatus.VERIFIED.name() + "' OR "
                    + " C.`STATUS` = '" + CompanyStatus.NOT_VERIFIED.name() + "') ";

    private static final String UPDATE_PW_BY_ID =
            UPDATE_USER
                    + " SET PASSWORD = ?, KEY_ID = NULL "
                    + " WHERE ID = ? ";

    private static final String UPDATE_KEY_BY_ID =
            UPDATE_USER
                    + " SET KEY_ID = ? "
                    + " WHERE ID = ? ";

    private static final String UPDATE_COUNT_LOGINS_AND_TIME = " UPDATE USER SET LOGIN_COUNT = LOGIN_COUNT + 1, "
            + " LAST_LOGIN = CURRENT_TIMESTAMP() WHERE ID = ? ";

    private final DataSource datasource;

    public UserDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void add(StockUser user) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setLong(3, user.getCompanyId());
                return ps;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        user.setId(id);
    }

    @Override
    public boolean update(StockUser user) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_USER_BY_ID,
                user.getUsername(),
                user.getPassword(),
                user.getCompanyId(),
                user.getId()) == 1;
    }

    @Override
    public StockUser getByUsernameWithRoles(String username) {
        StockUser user = new StockUser();
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<StockUser> list = template.query(GET_USER_BY_USERNAME, new RowMapper<StockUser>() {
            @Override
            public StockUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (user.getUsername() == null) {
                    user.setId(rs.getLong("ID"));
                    user.setCompanyId(rs.getLong("COMPANY_ID"));
                    user.setUsername(rs.getString("USERNAME"));
                    user.setPassword(rs.getString("PASSWORD"));

                    CompanyAddress address = new CompanyAddress();
                    address.setAddress(rs.getString("ADDRESS"));
                    address.setCity(rs.getString("CITY"));
                    address.setCompanyId(user.getCompanyId());
                    address.setCountry(rs.getString("COUNTRY"));
                    address.setId(rs.getLong("ADDRESS_ID"));
                    address.setLatitude(rs.getDouble("LATITUDE"));
                    address.setLongitude(rs.getDouble("LONGITUDE"));

                    Company company = new Company();
                    company.setCompanyAddress(address);
                    company.setAddressId(address.getId());
                    company.setId(user.getCompanyId());
                    company.setName(rs.getString("NAME"));
                    company.setPhone(rs.getString("PHONE"));
                    company.setRegNumber(rs.getString("REG_NUMBER"));
                    company.setLanguage(rs.getString("LANGUAGE"));
                    company.setStatus(CompanyStatus.valueOf(rs.getString("STATUS")));

                    user.setCompany(company);
                }
                String role = rs.getString("ROLE");
                if (role != null) {
                    user.addAuthority(new SimpleGrantedAuthority(role));
                }
                return user;
            }
        }, username.toLowerCase());
        if (list.isEmpty()) {
            return null;
        } else {
            updateCountAndLastTimeForLogin(user);
            return list.get(0);
        }
    }

    @Override
    public boolean updatePassword(StockUser user) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_PW_BY_ID, user.getPassword(), user.getId()) == 1;
    }

    @Override
    public boolean updateKeyById(StockUser user) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_KEY_BY_ID, user.getKeyId(), user.getId()) == 1;
    }

    private boolean updateCountAndLastTimeForLogin(StockUser user) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_COUNT_LOGINS_AND_TIME, user.getId()) == 0;
    }
}
