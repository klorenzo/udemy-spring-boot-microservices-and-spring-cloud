package com.kevinlorenzo.api.users.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinlorenzo.api.users.dto.UserDto;
import com.kevinlorenzo.api.users.model.UserCreateRequestModel;
import com.kevinlorenzo.api.users.model.UserCreateResponseModel;
import com.kevinlorenzo.api.users.model.UserGetResponseModel;
import com.kevinlorenzo.api.users.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;

	@Autowired
	private UsersService usersService;

	@GetMapping("/status/check")
	public String status() {
		return "Users Microservice running on Port " + env.getProperty("local.server.port") + " and Gateway IP = "
				+ env.getProperty("gateway.ip");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserCreateResponseModel> create(
			@Valid @RequestBody UserCreateRequestModel userCreateRequestModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = modelMapper.map(userCreateRequestModel, UserDto.class);
		UserDto createdUser = usersService.create(userDto);
		UserCreateResponseModel userCreateResponseModel = modelMapper.map(createdUser, UserCreateResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(userCreateResponseModel);
	}

	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserGetResponseModel> getUserById(@PathVariable("userId") String userId) {
		UserDto userDto = usersService.getUserDetailsById(userId);

		return ResponseEntity.status(HttpStatus.OK).body(new ModelMapper().map(userDto, UserGetResponseModel.class));
	}

}
