package com.kevinlorenzo.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kevinlorenzo.api.users.dto.UserDto;

public interface UsersService extends UserDetailsService {

	UserDto create(UserDto userDto);

	UserDto getUserDetailsByEmail(String email);

}
