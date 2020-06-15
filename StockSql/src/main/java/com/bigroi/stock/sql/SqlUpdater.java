package com.bigroi.stock.sql;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ComponentScan("com.bigroi.stock/**")
@PropertySource("ds.properties")
public class SqlUpdater {

    private static final String MAIN_SQL_FILE_NAME = "Main.sql";
    private static final String UPDATE_SQL_FILE_NAME = "StockSql/sql/Update.xml";
    private static final String LABELS_SQL_FILE_NAME = "labels.sql";
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(SqlUpdater.class);
    private static final String UPDATE_SIZE = "UPDATE SQL_UPDATE SET LAST_UPDATE = ?";
    private static final String CHECK_BD = "USE `@schema@`; SELECT LAST_UPDATE FROM SQL_UPDATE";
    private static final String GET_SIZE = "SELECT LAST_UPDATE FROM SQL_UPDATE";

    private static final Logger logger = Logger.getLogger(SqlUpdater.class);

    public static void main(String[] args) throws Exception {
        new SqlUpdater().updateDataBase("stock");
    }

    public void updateDataBase(String schema) throws Exception {
        var dataSource = CONTEXT.getBean(DataSource.class);
        logger.info("Sql updater: start");
        var url = ((DriverManagerDataSource) dataSource).getUrl();
        ((DriverManagerDataSource) dataSource).setUrl(url + "&allowMultiQueries=true");
        try (var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            var lastUpdate = 0;
            if (!doesDatabaseExists(connection, schema)) {
                logger.info("Sql updater: create new schema " + schema);
                executeMainScript(connection, schema);
            } else {
                lastUpdate = getLastUpdate(connection);
            }
            logger.info("Sql updater: get queries");
            var queries = getUpdateQueries();

            for (String query : queries.subList(lastUpdate, queries.size())) {
                if (!"".equals(query.trim())) {
                    executeQuery(connection, query);
                }
            }
            logger.info("Sql updater: update sql_update");
            updateLastUpdate(connection, queries.size());

            logger.info("Sql updater: update labels");
            //updateLabels(connection);
            connection.commit();
            logger.info("Sql updater: finish");
        }
    }

    private void updateLabels(Connection connection) throws IOException, SQLException {
        try (var reader = openReader(LABELS_SQL_FILE_NAME);
             var statement = connection.prepareStatement("DELETE FROM `LABEL`")
        ) {
            statement.execute();
            var builder = new StringBuilder();
            while (reader.ready()) {
                builder.append(reader.readLine()).append("\n");
            }
            executeQuery(connection, builder.toString());
        }
    }

    private void updateLastUpdate(Connection connection, int size) throws SQLException {
        try (var statement = connection.prepareStatement(UPDATE_SIZE)) {
            statement.setInt(1, size);
            statement.executeUpdate();
        }
    }

    private int getLastUpdate(Connection connection) throws SQLException {
        try (var statement = connection.prepareStatement(GET_SIZE)) {
            var set = statement.executeQuery();
            set.next();
            return set.getInt("LAST_UPDATE");
        }
    }

    private List<String> getUpdateQueries() throws IOException, ParserConfigurationException, SAXException {
        var domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        var builder = domFactory.newDocumentBuilder();
        var xml = builder.parse(UPDATE_SQL_FILE_NAME);

        var list = xml.getElementsByTagName("update");

        var result = new ArrayList<String>();

        for (var i = 0; i < list.getLength(); i++) {
            result.add(list.item(i).getTextContent().trim());
        }
        return result;
    }

    private boolean doesDatabaseExists(Connection connection, String schema) {
        try (var statement = connection.prepareStatement(CHECK_BD.replaceAll("@schema@", schema))) {
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void executeMainScript(Connection connection, String schema) throws SQLException, IOException {
        try (var reader = openReader(MAIN_SQL_FILE_NAME)) {
            var builder = new StringBuilder();
            while (reader.ready()) {
                builder.append(reader.readLine()).append("\n");
            }

            executeQuery(connection, builder.toString().replaceAll("@schema@", schema));

        }
    }

    private void executeQuery(Connection connection, String query) throws SQLException {
        logger.info("Sql updater: execute udate query: " + query);
        try (var statement = connection.prepareStatement(query)) {
            statement.execute();
        }
    }

    private BufferedReader openReader(String fileName) {
        var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }
}
