package com.psm.demo.Model;

import org.springframework.data.mongodb.core.mapping.Document;

//import java.beans.Transient;

import org.springframework.data.annotation.Id;

@Document
public class User {
	
//	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	

	@Id
	private long uid;
	private String email;
	
	
	// Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(long uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    // Getter for uid
    public long getUid() {
        return uid;
    }

    // Setter for uid
    public void setUid(long uid) {
        this.uid = uid;
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
