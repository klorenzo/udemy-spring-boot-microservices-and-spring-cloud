package com.kevinlorenzo.mobile.app.ws.models.request;

import javax.validation.constraints.NotNull;

public class UserRequestUpdate {

	@NotNull(message = "firstName cannot be null")
	private String firstName;

	@NotNull(message = "lastName cannot be null")
	private String lastName;

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
