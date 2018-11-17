package com.bigroi.stock.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqlUpdater {

	private static final String MAIN_SQL_FILE_NAME = "Main.sql";
	private static final String UPDATE_SQL_FILE_NAME = "Update.xml";
	private static final String LABELS_SQL_FILE_NAME = "labels.sql";
	private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("spring.xml");
	private static final String UPDATE_SIZE = "UPDATE SQL_UPDATE SET LAST_UPDATE = ?";
	private static final String CHECK_BD = "USE `@schema@`; SELECT LAST_UPDATE FROM SQL_UPDATE";
	private static final String GET_SIZE = "SELECT LAST_UPDATE FROM SQL_UPDATE";
	
	private static final Logger logger = Logger.getLogger(SqlUpdater.class);
	
	public static void main(String[] args) throws SQLException, IOException, JAXBException {
		String schema;
		if (args.length == 0){
			schema = "stock";
		} else {
			schema = args[0];
		}
		new SqlUpdater().updateDataBase(schema);
	}
	
	public void updateDataBase(String schema) throws SQLException, IOException, JAXBException{
		DataSource dataSource = CONTEXT.getBean(DataSource.class);
		Connection connection = null;
		try{
			logger.info("Sql updater: start");
			String url = ((DriverManagerDataSource)dataSource).getUrl();
			((DriverManagerDataSource)dataSource).setUrl(url.replaceAll(schema, "") + "&allowMultiQueries=true");
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			int lastUpdate = 0;
			if (!doesDatabaseExists(connection, schema)){
				logger.info("Sql updater: create new schema " + schema);
				executeMainScript(connection, schema);
			} else {
				lastUpdate = getLastUpdate(connection);
			}
			logger.info("Sql updater: get queries");
			List<String> queries = getUpdateQueries();
			
			for (String query : queries.subList(lastUpdate, queries.size())){
				executeQuery(connection, query);
			}
			logger.info("Sql updater: update sql_update");
			updateLastUpdate(connection, queries.size());
			
			logger.info("Sql updater: update labels");
			updateLabels(connection);
			connection.commit();
			logger.info("Sql updater: finish");
		}catch (SQLException e) {
			if (connection != null){
				try {
					connection.rollback();
				} catch (SQLException e1) {	
					logger.warn("", e1);
				}
			}
			throw e;
		}finally {
			if (connection != null){
				try {
					connection.close();
				} catch (SQLException e) {	
					logger.warn("", e);
				}
			}
		}
	}

	private void updateLabels(Connection connection) throws IOException, SQLException {
		try(BufferedReader reader = openReader(LABELS_SQL_FILE_NAME);
			PreparedStatement statement = connection.prepareStatement("DELETE FROM `LABEL`");
		){
			statement.execute();
			StringBuilder builder = new StringBuilder();
			while (reader.ready()){
				builder.append(reader.readLine()).append("\n");
			}
			executeQuery(connection, builder.toString());
		}
	}

	private void updateLastUpdate(Connection connection, int size) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(UPDATE_SIZE)){
			statement.setInt(1, size);
			statement.executeUpdate();
		}
	}

	private int getLastUpdate(Connection connection) throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement(GET_SIZE);
			ResultSet set = statement.executeQuery()
			){
			set.next();
			return set.getInt("LAST_UPDATE");
		}
	}

	private List<String> getUpdateQueries() throws IOException, JAXBException {
		try(BufferedReader reader = openReader(UPDATE_SQL_FILE_NAME)){
			JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Root root = (Root)unmarshaller.unmarshal(reader);
			
			return root.updates.stream().map(r -> r.sql).collect(Collectors.toList());
		}
	}

	private boolean doesDatabaseExists(Connection connection, String schema){
		try(PreparedStatement statement = connection.prepareStatement(CHECK_BD.replaceAll("@schema@", schema))){
			statement.executeQuery();
			return true;
		}catch (SQLException e) {
			return false;
		}
	}
	
	private void executeMainScript(Connection connection, String schema) throws SQLException, IOException{
		try(BufferedReader reader = openReader(MAIN_SQL_FILE_NAME)){
			StringBuilder builder = new StringBuilder();
			while (reader.ready()){
				builder.append(reader.readLine()).append("\n");
			}
			
			executeQuery(connection, builder.toString().replaceAll("@schema@", schema));
			
		}
	}
	
	private void executeQuery(Connection connection, String query) throws SQLException {
		logger.info("Sql updater: execute udate query: " + query);
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.execute();
		}
	}

	private BufferedReader openReader(String fileName){
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
	}
	
	@XmlRootElement(name="root")
	private static class Root{
		@XmlElement(name="update")
		List<Update> updates = new ArrayList<>();
	}
	
	@XmlRootElement(name="update")
	private static class Update{
		@XmlAttribute
		String author;
		@XmlAttribute
		String date;
		@XmlValue
		String sql;
	}
}
