package com.kevinlorenzo.mobile.app.ws.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevinlorenzo.mobile.app.ws.models.request.UserRequestCreate;
import com.kevinlorenzo.mobile.app.ws.models.request.UserRequestUpdate;
import com.kevinlorenzo.mobile.app.ws.models.response.UserResponse;
import com.kevinlorenzo.mobile.app.ws.services.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	private Map<String, UserResponse> users;

	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<Map<String, UserResponse>> getUsers(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit,
			@RequestParam(value = "sort", required = false) String sort) {
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
		if (users == null) {
			users = new HashMap<>();
		} else if (users.containsKey(userId)) {
			return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequestCreate userRequestCreate) {
		UserResponse userResponse = userService.create(userRequestCreate);

		if (users == null) {
			users = new HashMap<>();
		}

		users.put(userResponse.getUserId(), userResponse);

		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> updateUser(@PathVariable String userId,
			@Valid @RequestBody UserRequestUpdate userRequestUpdate) {
		if (users.containsKey(userId)) {
			UserResponse userResponse = users.get(userId);
			userResponse.setFirstName(userRequestUpdate.getFirstName());
			userResponse.setLastName(userRequestUpdate.getLastName());
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		if (users.containsKey(userId)) {
			users.remove(userId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
