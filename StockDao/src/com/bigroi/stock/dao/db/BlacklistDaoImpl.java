package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.db.Blacklist;
import com.bigroi.stock.dao.BlacklistDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlacklistDaoImpl implements BlacklistDao {

    private static final String ADD_BLACKLIST = "INSERT INTO \"BLACK_LIST\" "
            + " (TENDER_ID, LOT_ID) VALUES (?, ?)";

    private final DataSource datasource;

    public BlacklistDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void add(Blacklist blacklist) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_BLACKLIST, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setLong(1, blacklist.getTenderId());
                ps.setLong(2, blacklist.getLotId());
                return ps;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        blacklist.setId(id);
    }

}
