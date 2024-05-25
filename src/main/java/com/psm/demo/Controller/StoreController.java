package com.psm.demo.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import java.util.Objects;

import com.psm.demo.Model.DatabaseSequence;
import com.psm.demo.Model.User;
import com.psm.demo.Repository.UserRepo;


@RestController
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreController {
	
	@Autowired
	private MongoOperations mongoOperations;
	
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    
    @Autowired
    UserRepo userrepo;
    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
    	User userTemp = new User();
    	userTemp.setEmail(user.getEmail());
    	userTemp.setUid(generateSequence(User.SEQUENCE_NAME));
    	userrepo.save(userTemp);
    }
    
    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
          new Update().inc("seq",1), options().returnNew(true).upsert(true),
          DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    } 
}
