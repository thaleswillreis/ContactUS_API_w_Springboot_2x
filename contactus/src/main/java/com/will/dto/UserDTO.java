package com.will.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.will.domain.User;
import com.will.services.valadation.UniqueEmail;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	@NotNull
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 2, max = 60, message = "Mínimo de 2 e máximo de 60 caracteres.")
	private String firstName;
	
	@NotNull
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 2, max = 60, message = "Mínimo de 2 e máximo de 60 caracteres.")
	private String lastName;
	
	@NotNull
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Insira um endereço de e-mail válido")
	@UniqueEmail(message = "O e-mail já está sendo usado por outro usuário.")
	private String email;
	
	private String phone;
	
	public UserDTO() {
	}
	public UserDTO(User obj) {
		id = obj.getId();
		firstName = obj.getFirstName();
		lastName = obj.getLastName();
		email = obj.getEmail();
		phone = obj.getPhone();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
