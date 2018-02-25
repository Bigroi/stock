package com.bigroi.stock.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.InviteUserDao;

public class InviteUserDaoImpl implements InviteUserDao {
	
	private static final String ADD_INVITE_USER = "INSERT INTO invite_user "
			+ " (invite_email, generated_key, company_Id) VALUES (?, ?, ?) ";
	
	private static final String GET_ALL_INVITE_USER = " SELECT id, invite_email, generated_key, company_id FROM invite_user ";
	
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void add(InviteUser inviteUser) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(ADD_INVITE_USER, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, inviteUser.getInviteEmail());
				ps.setString(2, inviteUser.getGeneratedKey());
				ps.setLong(3, inviteUser.getCompanyId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		inviteUser.setId(id);
		
	}

	@Override
	public List<InviteUser> getAllInviteUser() throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<InviteUser> list = template.query(GET_ALL_INVITE_USER, new BeanPropertyRowMapper<InviteUser>(InviteUser.class));
		return list;
	}
}
