package com.fbm.mgmt.supervisor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbm.mgmt.supervisor.dao.I_UserDAO;
import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.User;
import com.fbm.mgmt.supervisor.service.I_UserService;

@Service
public class UserService implements I_UserService {

	@Autowired
	private I_UserDAO userDao;

	@Override
	public FbmResponse<User> userExists(User simpleLoginCredential) {
		return userDao.userExists(simpleLoginCredential);
	}

	@Override
	public FbmResponse<User> getUserByName(String username) {
		return userDao.getUserByName(username);
	}

}
