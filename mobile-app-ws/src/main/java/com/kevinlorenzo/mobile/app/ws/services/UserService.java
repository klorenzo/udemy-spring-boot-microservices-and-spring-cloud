package com.kevinlorenzo.mobile.app.ws.services;

import com.kevinlorenzo.mobile.app.ws.models.request.UserRequestCreate;
import com.kevinlorenzo.mobile.app.ws.models.response.UserResponse;

public interface UserService {

	UserResponse create(UserRequestCreate userRequestCreate);

}
