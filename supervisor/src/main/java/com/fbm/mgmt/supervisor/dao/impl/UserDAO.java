package com.fbm.mgmt.supervisor.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fbm.mgmt.supervisor.dao.I_UserDAO;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.User;
import com.fbm.mgmt.supervisor.util.FbmUtil;

@Transactional
@Repository
public class UserDAO implements I_UserDAO {

	private static final String SELECT_USER = "select * from users where username=? and password=?";
	
	private static final String SELECT_USER_BY_NAME = "select * from users where username=?";

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public FbmResponse<User> userExists(User simpleLoginCredential) {
		List<User> users = jdbcTemplate.query(SELECT_USER, new Object[] { simpleLoginCredential.getUsername(), simpleLoginCredential.getPassword() }, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, User.class));
		boolean result = users != null && users.size() == 1;
		String msg = null;
		User user = null;
		if(result) {
			user = users.get(0);
			user.setPassword(null);
		} else {
			msg = "The username or password may be wrong";
		}
		return new FbmResponse<User>(result, msg, user);
	}

	@Override
	public FbmResponse<User> getUserByName(String userName) {
		List<User> users = jdbcTemplate.query(SELECT_USER_BY_NAME, new Object[] { userName }, (rs,num) -> FbmUtil.getObjectFromResultSet(rs, User.class));
		boolean result = users != null && users.size() == 1;
		String msg = null;
		User user = null;
		if(result) {
			user = users.get(0);
			user.setPassword(null);
		} else {
			msg = "The username or password may be wrong";
		}
		return new FbmResponse<User>(result, msg, user);
	}

}
