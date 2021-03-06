package com.fbm.mgmt.supervisor.service;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.User;

public interface I_UserService {

	FbmResponse<User> userExists(User simpleLoginCredential);
	
	FbmResponse<User> getUserByName(String username);

}
