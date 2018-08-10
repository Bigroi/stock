package com.bigroi.stock.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Label;
import com.bigroi.stock.dao.LabelDao;

@Repository
public class LabelDaoImpl implements LabelDao {

	private static final String LANGUAGE = "LANGUAGE";
	
	private static final String GET_LABEL = 
			  "SELECT " + LANGUAGE 
			+ " FROM LABEL "
			+ " WHERE CATEGORY = ? AND NAME = ?";
	
	private static final String GET_ALL_LABELS = 
			  "SELECT " + LANGUAGE + ", CATEGORY, NAME "
			+ " FROM LABEL ";
	
	@Autowired
	private DataSource datasource;
	
	@Override
	public String getLabel(String category, String name, Locale language) {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<String> list = template.query(
				GET_LABEL.replace(LANGUAGE, language.toString()), 
				new RowMapper<String>(){
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString(1);
					}
				},
				category, name);
		if (list.isEmpty()){
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
				new RowMapper<Label>(){
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
}
