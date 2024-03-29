package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.common.CompanyStatus;
import com.bigroi.stock.bean.db.Company;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.dao.CompanyDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    private static final String FROM = " FROM \"COMPANY\" ";
    private static final String SELECT_ALL_COLUMNS =
            " SELECT ID, NAME, PHONE, REG_NUMBER, STATUS, LANGUAGE ";

    private static final String GET_COMPANY_BY_ID =
            " SELECT C.ID, C.NAME, C.PHONE, C.REG_NUMBER, C.STATUS, C.LANGUAGE, "
                    + " A.CITY, A.COUNTRY, A.ADDRESS, A.ID ADDRESS_ID, "
                    + " A.LONGITUDE, A.LATITUDE, A.COMPANY_ID, "
                    + " U.USERNAME, C.TYPE "
                    + " FROM \"COMPANY\" C "
                    + " JOIN \"ADDRESS\" A "
                    + " ON C.ADDRESS_ID = A.ID "
                    + " JOIN USER U"
                    + " ON U.COMPANY_ID = C.ID"
                    + " WHERE C.ID = ? ";

    private static final String ADD_COMPANY =
            "INSERT INTO \"COMPANY\" "
                    + " (NAME, PHONE, REG_NUMBER, STATUS, TYPE, LANGUAGE) "
                    + " VALUES(?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_COMPANY_BY_ID =
            " UPDATE \"COMPANY\" SET NAME = ?, ADDRESS_ID = ?, "
                    + " PHONE = ?, REG_NUMBER = ?, STATUS = ?, LANGUAGE = ? "
                    + " WHERE ID = ? ";

    private static final String GET_ALL_COMPANIES =
            SELECT_ALL_COLUMNS
                    + FROM;

    private static final String GET_COMPANY_BY_NAME =
            SELECT_ALL_COLUMNS
                    + FROM
                    + " WHERE NAME = ?";

    private static final String GET_COMPANY_BY_REG_NUMBER =
            SELECT_ALL_COLUMNS
                    + FROM
                    + " WHERE REG_NUMBER = ?";

    private static final String SET_STATUS_BY_ID =
            "UPDATE \"COMPANY\" SET "
                    + "STATUS = ? "
                    + "WHERE ID = ?";

    private final DataSource datasource;

    public CompanyDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Company getById(long id) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<Company> list = template.query(
                GET_COMPANY_BY_ID,
                new CompanyRowMapper(),
                id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void add(Company company) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_COMPANY, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, company.getName());
                ps.setString(2, company.getPhone());
                ps.setString(3, company.getRegNumber());
                ps.setString(4, company.getStatus().name().toUpperCase());
                ps.setString(5, company.getType());
                ps.setString(6, company.getLanguage());
                return ps;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        company.setId(id);
    }

    @Override
    public boolean update(Company company) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_COMPANY_BY_ID,
                company.getName(),
                company.getAddressId(),
                company.getPhone(),
                company.getRegNumber(),
                company.getStatus().name().toUpperCase(),
                company.getLanguage(),
                company.getId()) == 1;
    }

    @Override
    public List<Company> getAllCompany() {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.query(GET_ALL_COMPANIES, new BeanPropertyRowMapper<Company>(Company.class));
    }

    @Override
    public void setStatus(long id, CompanyStatus status) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(SET_STATUS_BY_ID, status.name(), id);
    }

    @Override
    public Company getByRegNumber(String regNumber) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<Company> list = template.query(GET_COMPANY_BY_REG_NUMBER, new BeanPropertyRowMapper<Company>(Company.class), regNumber);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public Company getByName(String name) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<Company> list = template.query(GET_COMPANY_BY_NAME, new BeanPropertyRowMapper<Company>(Company.class), name);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    private static class CompanyRowMapper implements RowMapper<Company> {
        @Override
        public Company mapRow(ResultSet rs, int arg1) throws SQLException {
            Company company = new Company();
            company.setId(rs.getInt("ID"));
            company.setName(rs.getString("NAME"));
            company.setPhone(rs.getString("PHONE"));
            company.setRegNumber(rs.getString("REG_NUMBER"));
            company.setStatus(CompanyStatus.valueOf(rs.getString("STATUS")));
            company.setEmail(rs.getString("USERNAME"));
            company.setType(rs.getString("TYPE"));
            company.setLanguage(rs.getString("LANGUAGE"));

            CompanyAddress address = new CompanyAddress();
            address.setAddress(rs.getString("ADDRESS"));
            address.setCity(rs.getString("CITY"));
            address.setCountry(rs.getString("COUNTRY"));
            address.setCompanyId(rs.getLong("COMPANY_ID"));
            address.setId(rs.getLong("ADDRESS_ID"));
            address.setLatitude(rs.getDouble("LATITUDE"));
            address.setLongitude(rs.getDouble("LONGITUDE"));

            company.setCompanyAddress(address);

            return company;
        }
    }
}
