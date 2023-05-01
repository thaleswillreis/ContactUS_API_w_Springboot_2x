package com.will.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.will.domain.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {

}
