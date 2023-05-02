package com.will.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.will.domain.Contact;
import com.will.services.ContactService;

@RestController
@RequestMapping({"/contacts", "/contacts/"})
public class ContactResource {
	
	@Autowired
	private ContactService service;
	
	@GetMapping
	public ResponseEntity<List<Contact>> findAll() {
		List<Contact> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping({"/{id}", "/{id}/"})
	public ResponseEntity<Contact> findById(@PathVariable String id) {
		Contact obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}