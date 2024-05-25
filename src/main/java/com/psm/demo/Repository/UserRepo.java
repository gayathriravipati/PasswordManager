package com.psm.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.psm.demo.Model.User;

public interface UserRepo extends MongoRepository<User, Integer>{

}
