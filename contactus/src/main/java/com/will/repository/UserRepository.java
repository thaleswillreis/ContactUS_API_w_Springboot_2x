package com.will.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.will.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	boolean existsByEmail(String email);

}