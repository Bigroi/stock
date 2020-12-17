package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.dao.LabelDao;
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
import java.util.Locale;

public class LabelDaoImpl implements LabelDao {

    private static final String SELECT = "SELECT ";
    private static final String LANGUAGE = "$LANGUAGE";
    private static final String FROM = " FROM \"LABEL\"";
    private static final String SELECT_ALL_COLUMNS = "SELECT ID, CATEGORY, NAME, EN_US, RU_RU, PL ";

    private static final String GET_LABEL =
            SELECT + LANGUAGE
                    + FROM
                    + " WHERE CATEGORY = ? AND NAME = ?";

    private static final String GET_ALL_LABELS =
            SELECT + LANGUAGE + ", CATEGORY, NAME "
                    + FROM;

    private static final String GET_ALL_LABELS_LIST =
            SELECT_ALL_COLUMNS
                    + FROM;

    private static final String GET_LABEL_BY_ID =
            SELECT_ALL_COLUMNS
                    + FROM
                    + "  WHERE ID = ? ";

    private static final String ADD_LABEL =
            "INSERT INTO LABEL (CATEGORY, NAME,  EN_US, RU_RU, PL) "
                    + " VALUES ( ?, ?, ?, ?, ?) ";

    private static final String UPDATE_LABEL_BY_ID =
            "UPDATE LABEL "
                    + " SET CATEGORY  = ?, NAME = ?, "
                    + " EN_US = ?, RU_RU = ?, PL = ? WHERE ID = ? ";

    private static final String DELETE_LABEL_BY_ID =
            "DELETE "
                    + FROM
                    + " WHERE ID = ? ";

    private final DataSource datasource;

    public LabelDaoImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public String getLabel(String category, String name, Locale language) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<String> list = template.query(
                GET_LABEL.replace(LANGUAGE, language.toString()),
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                category, name);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Label> getAllLabel(Locale language) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.query(
                GET_ALL_LABELS.replace(LANGUAGE, language.toString()),
                new RowMapper<Label>() {
                    @Override
                    public Label mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Label label = new Label();
                        label.setCurrentLanguage(rs.getString(1));
                        label.setCategory(rs.getString("CATEGORY"));
                        label.setName(rs.getString("NAME"));
                        return label;
                    }
                });
    }

    @Override
    public List<Label> getAllLabel() {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.query(GET_ALL_LABELS_LIST, new BeanPropertyRowMapper<Label>(Label.class));
    }

    @Override
    public Label getLabelById(long id) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        List<Label> list = template.query(GET_LABEL_BY_ID,
                new BeanPropertyRowMapper<Label>(Label.class), id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public void add(Label label) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_LABEL, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, label.getCategory());
                ps.setString(2, label.getName());
                ps.setString(3, label.getEnUs());
                ps.setString(4, label.getRuRu());
                ps.setString(5, label.getPl());
                return ps;
            }
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        label.setId(id);
    }

    @Override
    public boolean update(Label label) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_LABEL_BY_ID,
                label.getCategory(), label.getName(), label.getEnUs(),
                label.getRuRu(), label.getPl(), label.getId()) == 1;
    }

    @Override
    public boolean delete(long id) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(DELETE_LABEL_BY_ID, id) == 1;
    }
}
