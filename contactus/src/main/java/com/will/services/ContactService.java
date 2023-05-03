package com.will.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.will.domain.Contact;
import com.will.repository.ContactRepository;
import com.will.services.exception.ObjectNotFoundException;

@Service
public class ContactService {

	@Autowired
	private ContactRepository repo;

	public List<Contact> findAll() {
		return repo.findAll();
	}
	
	public Contact findById(String id) {
		Optional<Contact> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Contact> findBySubject(String text) {
		return repo.findBySubject(text);
	}
	
	public List<Contact> searchTextInContact(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.searchTextInContactByDate(text, minDate, maxDate);
	}
}
