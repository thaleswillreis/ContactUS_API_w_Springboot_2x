package com.will.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.will.domain.User;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {

    private final MongoOperations mongoOperations;

    public UserRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public boolean existsByEmail(String email) {
        return mongoOperations.exists(Query.query(Criteria.where("email").is(email)), User.class);
    }
}