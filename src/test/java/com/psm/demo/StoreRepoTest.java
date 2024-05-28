package com.psm.demo;

import com.psm.demo.Model.Store;
import com.psm.demo.Repository.StoreRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class StoreRepoTest {

    @Autowired
    private StoreRepo storeRepo;

    private Store store;

    @BeforeEach
    public void setUp() {
        storeRepo.deleteAll(); // Clear the database before each test
        store = new Store();
        Store.CompositeKey compositeKey = new Store.CompositeKey(1, 1);
        store.setId(compositeKey);
        store.setWebsiteName("Test Website");
        store.setWebsiteLink("http://test.com");
        store.setUsername("testuser");
        store.setPassword("testpassword");
        storeRepo.save(store);
    }

    @Test
    public void testFindByCompositeKey() {
        Optional<Store> foundStore = storeRepo.findByCompositeKey(1, 3);
        assertTrue(foundStore.isPresent());
        assertEquals("Test Website", foundStore.get().getWebsiteName());
    }

    @Test
    public void testDeleteByCompositeKey() {
        storeRepo.deleteByCompositeKey(1, 1);
        Optional<Store> foundStore = storeRepo.findByCompositeKey(1, 1);
        assertFalse(foundStore.isPresent());
    }
}
