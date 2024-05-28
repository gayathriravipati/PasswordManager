package com.psm.demo.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

@Document
public class Store {

    @Transient
    public static final String SEQUENCE_NAME = "stores_sequence";

    @Id
    private CompositeKey id;
    private String websiteName;
    private String websiteLink;
    private String username;
    private String password;

    // Default constructor
//    @IdClass(Store.CompositeKey.class)
    public Store() {
    }

    // Parameterized constructor
    public Store(int passwordId, int userId, String websiteName, String websiteLink, String username, String password) {
        this.id = new CompositeKey(passwordId, userId);
        this.websiteName = websiteName;
        this.websiteLink = websiteLink;
        this.username = username;
        this.password = password;
    }

    // Getter and Setter for id
    public CompositeKey getId() {
        return id;
    }

    public void setId(CompositeKey id) {
        this.id = id;
    }

    // Getter and Setter for websiteName
    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    // Getter and Setter for websiteLink
    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void save(Store store) {
        // TODO Auto-generated method stub
    }

    // CompositeKey class for composite primary key
    public static class CompositeKey {
        private long passwordId;
        private int userId;

        // Default constructor
        public CompositeKey() {
        }

        // Parameterized constructor
        public CompositeKey(long l, int userId) {
            this.passwordId = l;
            this.userId = userId;
        }

        // Getter and Setter for passwordId
        public long getPasswordId() {
            return passwordId;
        }

        public void setPasswordId(int passwordId) {
            this.passwordId = passwordId;
        }

        // Getter and Setter for userId
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        // Override equals and hashCode for composite key
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CompositeKey that = (CompositeKey) o;

            if (passwordId != that.passwordId) return false;
            return userId == that.userId;
        }

        @Override
        public int hashCode() {
            long result = passwordId;
            result = 31 * result + userId;
            return (int) result;
        }
    }
}
