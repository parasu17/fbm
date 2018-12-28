package com.fbm.mgmt.supervisor.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fbm.mgmt.supervisor.dataobjects.FbmResponse;
import com.fbm.mgmt.supervisor.dataobjects.User;
import com.fbm.mgmt.supervisor.service.I_UserService;

@RestController
@RequestMapping("/services/user/")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private I_UserService userService;

	@PostMapping("/getLoggedInUser")
	@ResponseBody
	public FbmResponse<User> getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FbmResponse<User> res = userService.getUserByName(currentPrincipalName);
		return res;
	}

}
