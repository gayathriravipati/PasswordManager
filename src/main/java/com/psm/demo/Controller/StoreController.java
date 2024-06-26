package com.psm.demo.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import java.util.Objects;
import java.util.Optional;

import com.psm.demo.Model.DatabaseSequence;
import com.psm.demo.Model.Store;
import com.psm.demo.Model.User;
import com.psm.demo.Repository.UserRepo;
import com.psm.demo.util.AESUtil;
import com.psm.demo.Repository.StoreRepo;


@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	@Autowired
	private MongoOperations mongoOperations;
	
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
    	logger.info("Redirecting to Swagger UI");
        response.sendRedirect("/swagger-ui.html");
    }
    
    //Called when user creates an account to the website
    @Autowired
    UserRepo userrepo;
    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
    	User userTemp = new User();
    	userTemp.setEmail(user.getEmail());
    	userTemp.setUid(generateSequence(User.SEQUENCE_NAME));
    	userrepo.save(userTemp);
    }
    
    //Stores the password details to every user
    @Autowired
    StoreRepo storeRepo;
    @PostMapping("/storePassword/{userId}")
    public void addStore(@PathVariable int userId, @RequestBody Store store) {
        try {
            Store storeTemp = new Store();
            storeTemp.setId(new Store.CompositeKey(generateSequence(Store.SEQUENCE_NAME + "_" + userId), userId));
            storeTemp.setWebsiteName(store.getWebsiteName());
            storeTemp.setWebsiteLink(store.getWebsiteLink());
            String encryptedUsername = AESUtil.encrypt(store.getUsername());
            String encryptedPassword = AESUtil.encrypt(store.getPassword());

            // Debugging statements
            logger.debug("Original Username: {}", store.getUsername());
            logger.debug("Encrypted Username: {}", encryptedUsername);
            logger.debug("Original Password: {}", store.getPassword());
            logger.debug("Encrypted Password: {}", encryptedPassword);

            storeTemp.setUsername(encryptedUsername);
            storeTemp.setPassword(encryptedPassword);
            storeRepo.save(storeTemp);
            logger.info("User added successfully with UID");
        } catch (RuntimeException e) {
        	logger.error("Error storing password details for userId: {}", userId, e);
            e.printStackTrace();
        }
    }
    
    @DeleteMapping("/deleteStore/{userId}/{passwordId}")
    public String deleteStore(@PathVariable int userId, @PathVariable long passwordId) {
        logger.info("Attempting to delete store entry with userId: {} and passwordId: {}", userId, passwordId);
        try {
            Optional<Store> storeOptional = storeRepo.findByCompositeKey(passwordId, userId);
            if (storeOptional.isPresent()) {
                storeRepo.deleteByCompositeKey(passwordId, userId);
                logger.info("Store entry deleted successfully for userId: {} and passwordId: {}", userId, passwordId);
                return "Store entry deleted successfully";
            }
            logger.warn("Store entry not found for userId: {} and passwordId: {}", userId, passwordId);
            return "Store entry not found";
        } catch (RuntimeException e) {
            logger.error("Error deleting store entry for userId: {} and passwordId: {}", userId, passwordId, e);
            return "Error deleting store entry";
        }
    }
    
    @PatchMapping("/modifyStore/{userId}/{passwordId}")
    public String modifyStore(@PathVariable int userId, @PathVariable long passwordId,
                              @RequestBody Store updatedStore) {
        try {
            Optional<Store> storeOptional = storeRepo.findByCompositeKey(passwordId, userId);
            if (storeOptional.isPresent()) {
                Store store = storeOptional.get();
                if (updatedStore.getWebsiteName() != null) {
                    store.setWebsiteName(updatedStore.getWebsiteName());
                }
                if (updatedStore.getWebsiteLink() != null) {
                    store.setWebsiteLink(updatedStore.getWebsiteLink());
                }
                if (updatedStore.getUsername() != null) {
                    store.setUsername(AESUtil.encrypt(updatedStore.getUsername()));
                }
                if (updatedStore.getPassword() != null) {
                    store.setPassword(AESUtil.encrypt(updatedStore.getPassword()));
                }
                storeRepo.save(store);
                return "Store entry updated successfully";
            }
            return "Store entry not found";
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "Error updating store entry";
        }
    }
    
    
    public long generateSequence(String seqName) {
    	logger.info("Generating sequence for: {}", seqName);
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
          new Update().inc("seq",1), options().returnNew(true).upsert(true),
          DatabaseSequence.class);
        logger.info("Generated sequence value");
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
    
//    @GetMapping("/decryptPassword/{userId}/{pId}")
//    public String decryptPassword(@PathVariable int userId, @PathVariable long pId) {
//        try {
//            Store store = storeRepo.findAllById(new Store.CompositeKey(pId, userId)).orElse(null);
//            if (store != null) {
//                return AESUtil.decrypt(store.getPassword());
//            }
//            return "Store not found";
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            return "Error decrypting password";
//        }
//    }
}