package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.*;
import com.bigroi.stock.dao.LotDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class LotDaoImpl extends BidBaseDao<Lot, TradeLot> implements LotDao {

    private static final String ADD_LOT =
            "INSERT INTO \"LOT\" "
                    + " (DESCRIPTION, PRODUCT_ID, CATEGORY_ID, PRICE, MIN_VOLUME, "
                    + " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
                    + " ADDRESS_ID, FOTO, DISTANCE, ALERT) "
                    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'N') ";

    private static final String UPDATE_LOT_BY_ID_AND_SELLER =
            "UPDATE \"LOT\" SET "
                    + " DESCRIPTION = ?, PRODUCT_ID = ?, CATEGORY_ID = ?, PRICE = ?, MIN_VOLUME = ?, "
                    + " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
                    + " ADDRESS_ID = ?, FOTO = ?, DISTANCE = ? "
                    + " WHERE ID = ? AND COMPANY_ID = ?";

    public LotDaoImpl(DataSource datasource) {
        super(datasource);
    }

    @Override
    protected String selectAllColumns() {
        return LotRowMapper.SELECT_ALL_COLUMNS;
    }

    @Override
    protected String from() {
        return LotRowMapper.FROM;
    }

    @Override
    protected String getTableName() {
        return " LOT ";
    }

    @Override
    protected PreparedStatementCreator getPreparedStatementCreatorForAdding(Lot lot) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_LOT, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, lot.getDescription());
                ps.setLong(2, lot.getProductId());
                ps.setLong(3, lot.getCategoryId());
                ps.setDouble(4, lot.getPrice());
                ps.setInt(5, lot.getMinVolume());
                ps.setInt(6, lot.getMaxVolume());
                ps.setLong(7, lot.getCompanyId());
                ps.setString(8, lot.getStatus().name().toUpperCase());
                ps.setDate(9, new Date(lot.getCreationDate().getTime()));
                ps.setDate(10, new Date(lot.getExparationDate().getTime()));
                ps.setLong(11, lot.getAddressId());
                ps.setString(12, lot.getFoto());
                ps.setInt(13, lot.getDistance());
                return ps;
            }
        };
    }

    @Override
    public boolean update(Lot lot, long companyId) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_LOT_BY_ID_AND_SELLER,
                lot.getDescription(),
                lot.getProductId(),
                lot.getCategoryId(),
                lot.getPrice(),
                lot.getMinVolume(),
                lot.getMaxVolume(),
                lot.getStatus().name(),
                lot.getCreationDate(),
                lot.getExparationDate(),
                lot.getAddressId(),
                lot.getFoto(),
                lot.getDistance(),
                lot.getId(),
                companyId) == 1;
    }

    @Override
    protected RowMapper<Lot> getRowMapper() {
        return new LotRowMapper();
    }

    @Override
    protected String getTableAlias() {
        return "L";
    }

    private static class LotRowMapper implements RowMapper<Lot> {

        public static final String SELECT_ALL_COLUMNS =
                "SELECT "
                        + " L.ID, L.DESCRIPTION, L.PRODUCT_ID, L.CATEGORY_ID, L.PRICE, L.MIN_VOLUME, "
                        + " L.MAX_VOLUME, L.COMPANY_ID, L.`STATUS`, L.CREATION_DATE, "
                        + " L.EXPARATION_DATE, L.ADDRESS_ID, L.FOTO, L.DISTANCE, L.ALERT, "
                        + " P.NAME PRODUCT_NAME,"
                        + " A.LONGITUDE LONGITUDE, A.LATITUDE LATITUDE, "
                        + " C.LANGUAGE ";

        public static final String FROM =
                " FROM LOT L "
                        + " JOIN PRODUCT P "
                        + " ON L.PRODUCT_ID = P.ID "
                        + " JOIN ADDRESS A "
                        + " ON L.ADDRESS_ID = A.ID "
                        + " JOIN COMPANY C "
                        + " ON L.COMPANY_ID = C.ID ";

        @Override
        public Lot mapRow(ResultSet rs, int rowNum) throws SQLException {
            Lot lot = new Lot();
            lot.setAddressId(rs.getLong("ADDRESS_ID"));
            lot.setCompanyId(rs.getLong("COMPANY_ID"));
            lot.setCreationDate(rs.getTimestamp("CREATION_DATE"));
            lot.setDescription(rs.getString("DESCRIPTION"));
            lot.setExparationDate(rs.getTimestamp("EXPARATION_DATE"));
            lot.setFoto(rs.getString("FOTO"));
            lot.setId(rs.getLong("ID"));
            lot.setMaxVolume(rs.getInt("MAX_VOLUME"));
            lot.setMinVolume(rs.getInt("MIN_VOLUME"));
            lot.setPrice(rs.getDouble("PRICE"));
            lot.setProductId(rs.getLong("PRODUCT_ID"));
            lot.setStatus(BidStatus.valueOf(rs.getString("STATUS")));
            lot.setDistance(rs.getInt("DISTANCE"));
            lot.setCategoryId(rs.getLong("CATEGORY_ID"));
            lot.setAlert(rs.getString("ALERT"));

            Product product = new Product();
            product.setName(rs.getString("PRODUCT_NAME"));
            product.setId(rs.getLong("PRODUCT_ID"));
            lot.setProduct(product);

            CompanyAddress address = new CompanyAddress();
            address.setLatitude(rs.getDouble("LATITUDE"));
            address.setLongitude(rs.getDouble("LONGITUDE"));
            lot.setCompanyAddress(address);

            Company company = new Company();
            company.setLanguage(rs.getString("LANGUAGE"));
            address.setCompany(company);

            return lot;
        }
    }

}
