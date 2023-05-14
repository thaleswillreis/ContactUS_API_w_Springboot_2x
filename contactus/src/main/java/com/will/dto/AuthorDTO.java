package com.will.dto;

import java.io.Serializable;

import com.will.domain.User;

public class AuthorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String firstName;
	private String lastName;

	public AuthorDTO() {
	}

	public AuthorDTO(User obj) {
		id = obj.getId();
		firstName = obj.getFirstName();
		lastName = obj.getLastName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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