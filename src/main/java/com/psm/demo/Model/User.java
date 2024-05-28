package com.psm.demo.Model;

import org.springframework.data.mongodb.core.mapping.Document;

//import java.beans.Transient;

import org.springframework.data.annotation.Id;

@Document
public class User {
	
//	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	

	@Id
	private long userId;
	private String email;
	
	
	// Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // Getter for userId
    public long getuserId() {
        return userId;
    }

    // Setter for uid
    public void setUid(long userId) {
        this.userId = userId;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

	public void save(User user) {
		// TODO Auto-generated method stub
		
	}
}
