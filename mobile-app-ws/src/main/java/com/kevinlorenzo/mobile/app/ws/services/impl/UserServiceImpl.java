package com.kevinlorenzo.mobile.app.ws.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinlorenzo.mobile.app.ws.models.request.UserRequestCreate;
import com.kevinlorenzo.mobile.app.ws.models.response.UserResponse;
import com.kevinlorenzo.mobile.app.ws.services.UserService;
import com.kevinlorenzo.mobile.app.ws.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	private Utils utils;

	public UserServiceImpl() {

	}

	@Autowired
	public UserServiceImpl(Utils utils) {
		this.utils = utils;
	}

	public UserResponse create(UserRequestCreate userRequestCreate) {
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(utils.generateUserId());
		userResponse.setEmail(userRequestCreate.getEmail());
		userResponse.setFirstName(userRequestCreate.getFirstName());
		userResponse.setLastName(userRequestCreate.getLastName());
		return userResponse;
	}

}
