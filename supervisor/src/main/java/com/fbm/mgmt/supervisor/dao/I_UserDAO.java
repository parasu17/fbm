package com.fbm.mgmt.supervisor.dao;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.User;

public interface I_UserDAO {

	FbmResponse<User> userExists(User simpleLoginCredential);
	
	FbmResponse<User> getUserByName(String username);

}
