package com.will.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.will.domain.Contact;
import com.will.dto.ContactDTO;
import com.will.repository.ContactRepository;
import com.will.services.exception.ObjectNotFoundException;

@Service
public class ContactService {

	@Autowired
	private ContactRepository repo;

	public Page<Contact> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	  	public List<Contact> findAllWithoutPagination() {
		return repo.findAll();
	}

	public Contact findById(String id) {
		Optional<Contact> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Contact insert(Contact obj) {
		obj.setId(null);
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Contact update(Contact obj) {
		Contact newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public List<Contact> findBySubject(String text) {
		return repo.findBySubject(text);
	}

	public List<Contact> searchTextInContactOrByDate(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.searchTextInContactByDate(text, minDate, maxDate);
	}
	
	public Page<Contact> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	private void updateData(Contact newObj, Contact obj) {
		newObj.setDate(obj.getDate());
		newObj.setSubject(obj.getSubject());
		newObj.setBody(obj.getBody());
		newObj.setAuthor(obj.getAuthor());
	}
	
	public Contact fromDTO(ContactDTO objDto) {
		return new Contact(
				objDto.getId(), 
				new Date(),
				objDto.getSubject(), 
				objDto.getBody(), 
				objDto.getAuthor());
	}
}
