package com.bigroi.stock.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bigroi.stock.bean.db.Proposition;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.PropositionDao;

@Repository
public class PropositionDaoImpl implements PropositionDao {
	
	private static final String GET_LIST_PROPOSITIONS = "SELECT ID, DEAL_ID, COMPANY_ID, PRICE FROM TRANSPORT_PROPOSITION";
	
	@Autowired
	private DataSource dataSource;

	@Override
	public List<Proposition> getListPropositions() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		List<Proposition> list = template.query(GET_LIST_PROPOSITIONS,
				new BeanPropertyRowMapper<Proposition>(Proposition.class));
		return list;
	}
}
