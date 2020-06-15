package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.db.TempKey;
import com.bigroi.stock.dao.GenerateKeyDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class GenerateKeyDaoImpl implements GenerateKeyDao {

    private static final String GET_GENERATED_KEY =
            "SELECT U.USERNAME "
                    + " FROM USER U "
                    + " JOIN GENERATED_KEY K "
                    + " ON U.KEY_ID = K.ID "
                    + " WHERE U.USERNAME = ? AND K.GENERATED_KEY = ? ";

    private static final String ADD_GENERATED_KEY =
            "INSERT INTO GENERATED_KEY ( GENERATED_KEY, EXPIRATION_TIME) "
                    + " VALUES (?, ?) ";

    public static final String DELETE_ALL_BY_DATE =
            " DELETE "
                    + " FROM GENERATED_KEY "
                    + " WHERE EXPIRATION_TIME < ? ";

    public static final String DELETE_ALL_BY_CODE =
            " DELETE "
                    + " FROM GENERATED_KEY "
                    + " WHERE GENERATED_KEY = ? ";

    private final DataSource datasource;

    public GenerateKeyDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public boolean checkResetKey(String email, String code) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<StockUser> list = template.query(GET_GENERATED_KEY,
                new BeanPropertyRowMapper<StockUser>(StockUser.class), email, code);
        return !list.isEmpty();
    }

    @Override
    public TempKey generateKey() {
        JdbcTemplate template = new JdbcTemplate(datasource);
        TempKey key = new TempKey();
        key.generateKey();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_GENERATED_KEY, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, key.getGeneratedKey());
                ps.setDate(2, new Date(key.getExpirationDate().getTime()));
                return ps;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        key.setId(id);
        return key;
    }

    @Override
    public void deleteGenerateKeysByDate() {
        JdbcTemplate template = new JdbcTemplate(datasource);
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        template.update(DELETE_ALL_BY_DATE, calendar);
    }

    @Override
    public void deleteGenerateKey(String code) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(DELETE_ALL_BY_CODE, code);
    }

}
