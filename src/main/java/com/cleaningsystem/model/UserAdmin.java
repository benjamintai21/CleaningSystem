package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class UserAdmin {
    private int uid;
    private String username;
    
    // No-args constructor for Spring
    public UserAdmin() {}

    // Constructor
    public UserAdmin(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }
}

