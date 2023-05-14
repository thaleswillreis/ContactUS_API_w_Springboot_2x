package com.will.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.will.domain.Contact;

public class ContactDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private Date date;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 2, max = 60, message = "Mínimo de 2 e máximo de 60 caracteres.")
	private String subject;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 2, max = 500, message = "Mínimo de 2 e máximo de 500 caracteres.")
	private String body;
	
	private AuthorDTO author;
	
	public ContactDTO() {
	}

	public ContactDTO(Contact obj) {
		id = obj.getId();
		date = obj.getDate();
		subject = obj.getSubject();
		body = obj.getBody();
		author = obj.getAuthor();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
}
