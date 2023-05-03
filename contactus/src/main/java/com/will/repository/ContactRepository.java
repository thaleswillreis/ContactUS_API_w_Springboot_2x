package com.will.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.will.domain.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {
	
	@Query("{ 'subject': { $regex: ?0, $options: 'i' } }")
	List<Contact> findBySubject(String text);
	
	@Query("{ $and: [ { date: { $gte: ?1 } }, { date: { $lte: ?2 } }, { $or: [ { 'subject': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Contact> searchTextInContactByDate(String text, Date minDate, Date maxDate);

}
