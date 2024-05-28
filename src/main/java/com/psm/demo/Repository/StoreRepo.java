package com.psm.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.psm.demo.Model.Store;

public interface StoreRepo extends MongoRepository<Store, Integer> {

}
