package com.kevinlorenzo.mobile.app.ws.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestCreate {
	
	private String userId;
	
	@NotNull(message = "email cannot be null")
	@Email
	@Size(min = 8, max = 16, message = "Length of Email must be between 8 and 16.")
	private String email;
	
	@NotNull(message = "firstName cannot be null")
	private String firstName;
	
	@NotNull(message = "lastName cannot be null")
	private String lastName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
