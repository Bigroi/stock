package com.bigroi.stock.dao.db;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.*;
import com.bigroi.stock.dao.TenderDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;

public class TenderDaoImpl extends BidBaseDao<Tender, TradeTender> implements TenderDao {

    private static final String ADD_TENDER =
            " INSERT INTO TENDER "
                    + " (DESCRIPTION, PRODUCT_ID, CATEGORY_ID, PRICE, MIN_VOLUME, "
                    + " MAX_VOLUME, COMPANY_ID, `STATUS`, CREATION_DATE, EXPARATION_DATE, "
                    + " ADDRESS_ID, DISTANCE, PACKAGING, PROCESSING, ALERT) "
                    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'N') ";

    private static final String UPDATE_TENDER_BY_ID_AND_COMPANY =
            " UPDATE TENDER "
                    + " SET DESCRIPTION = ?, PRODUCT_ID = ?, CATEGORY_ID = ?, PRICE = ?, MIN_VOLUME = ?, "
                    + " MAX_VOLUME = ?, `STATUS` = ?, CREATION_DATE = ?, EXPARATION_DATE = ?, "
                    + " ADDRESS_ID = ?, DISTANCE = ?, PACKAGING = ?, PROCESSING = ?"
                    + " WHERE ID = ? AND COMPANY_ID = ? ";

    public TenderDaoImpl(DataSource datasource) {
        super(datasource);
    }

    @Override
    protected PreparedStatementCreator getPreparedStatementCreatorForAdding(Tender tender) {
        return new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(ADD_TENDER, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, tender.getDescription());
                ps.setLong(2, tender.getProductId());
                if (tender.getCategoryId() != null) {
                    ps.setLong(3, tender.getCategoryId());
                } else {
                    ps.setNull(3, Types.BIGINT);
                }
                ps.setDouble(4, tender.getPrice());
                ps.setInt(5, tender.getMinVolume());
                ps.setInt(6, tender.getMaxVolume());
                ps.setLong(7, tender.getCompanyId());
                ps.setString(8, tender.getStatus().name().toUpperCase());
                ps.setDate(9, new Date(tender.getCreationDate().getTime()));
                ps.setDate(10, new Date(tender.getExparationDate().getTime()));
                ps.setLong(11, tender.getAddressId());
                ps.setInt(12, tender.getDistance());
                ps.setString(13, tender.getPackaging());
                ps.setString(14, tender.getProcessing());

                return ps;
            }
        };
    }

    @Override
    public boolean update(Tender tender, long companyId) {
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.update(UPDATE_TENDER_BY_ID_AND_COMPANY,
                tender.getDescription(),
                tender.getProductId(),
                tender.getCategoryId(),
                tender.getPrice(),
                tender.getMinVolume(),
                tender.getMaxVolume(),
                tender.getStatus().name(),
                tender.getCreationDate(),
                tender.getExparationDate(),
                tender.getAddressId(),
                tender.getDistance(),
                tender.getPackaging(),
                tender.getProcessing(),
                tender.getId(),
                companyId) == 1;
    }

    @Override
    protected RowMapper<Tender> getRowMapper() {
        return new TenderRowMapper();
    }

    @Override
    protected String selectAllColumns() {
        return TenderRowMapper.SELECT_ALL_COLUMNS;
    }

    @Override
    protected String from() {
        return TenderRowMapper.FROM;
    }

    @Override
    protected String getTableName() {
        return " TENDER ";
    }

    @Override
    protected String getTableAlias() {
        return "T";
    }

    private static class TenderRowMapper implements RowMapper<Tender> {

        public static final String SELECT_ALL_COLUMNS =
                "SELECT "
                        + " T.ID, T.DESCRIPTION, T.PRODUCT_ID, T.PRICE, T.MIN_VOLUME, "
                        + " T.MAX_VOLUME, T.COMPANY_ID, T.`STATUS`, T.CREATION_DATE, "
                        + " T.EXPARATION_DATE, T.ADDRESS_ID, T.DISTANCE, T.PACKAGING, T.PROCESSING, T.ALERT, "
                        + " P.NAME PRODUCT_NAME, T.CATEGORY_ID, "
                        + " A.LONGITUDE LONGITUDE, A.LATITUDE LATITUDE,"
                        + " C.LANGUAGE ";

        public static final String FROM =
                " FROM TENDER T "
                        + " JOIN PRODUCT P "
                        + " ON T.PRODUCT_ID = P.ID "
                        + " JOIN ADDRESS A "
                        + " ON T.ADDRESS_ID = A.ID "
                        + " JOIN COMPANY C "
                        + " ON T.COMPANY_ID = C.ID ";

        @Override
        public Tender mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tender tender = new Tender();
            tender.setAddressId(rs.getLong("ADDRESS_ID"));
            tender.setCompanyId(rs.getLong("COMPANY_ID"));
            tender.setCreationDate(rs.getTimestamp("CREATION_DATE"));
            tender.setDescription(rs.getString("DESCRIPTION"));
            tender.setExparationDate(rs.getTimestamp("EXPARATION_DATE"));
            tender.setId(rs.getLong("ID"));
            tender.setMaxVolume(rs.getInt("MAX_VOLUME"));
            tender.setMinVolume(rs.getInt("MIN_VOLUME"));
            tender.setPrice(rs.getDouble("PRICE"));
            tender.setProductId(rs.getLong("PRODUCT_ID"));
            tender.setStatus(BidStatus.valueOf(rs.getString("STATUS")));
            tender.setDistance(rs.getInt("DISTANCE"));
            tender.setPackaging(rs.getString("PACKAGING"));
            tender.setProcessing(rs.getString("PROCESSING"));
            tender.setCategoryId(rs.getLong("CATEGORY_ID"));
            tender.setAlert(rs.getString("ALERT"));

            Product product = new Product();
            product.setName(rs.getString("PRODUCT_NAME"));
            product.setId(rs.getLong("PRODUCT_ID"));
            tender.setProduct(product);

            CompanyAddress address = new CompanyAddress();
            address.setLatitude(rs.getDouble("LATITUDE"));
            address.setLongitude(rs.getDouble("LONGITUDE"));
            tender.setCompanyAddress(address);

            Company company = new Company();
            company.setLanguage(rs.getString("LANGUAGE"));
            address.setCompany(company);

            return tender;
        }
    }

}
