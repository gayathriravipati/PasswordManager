package com.psm.demo.Repository;

import com.psm.demo.Model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface StoreRepo extends MongoRepository<Store, Store.CompositeKey> {
    
    @Query("{ '_id.passwordId': ?0, '_id.userId': ?1 }")
    Optional<Store> findByCompositeKey(long passwordId, int userId);

    @Query(value = "{ '_id.passwordId': ?0, '_id.userId': ?1 }", delete = true)
    void deleteByCompositeKey(long passwordId, int userId);
}
