package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class HomeOwner {
    private int uid;
    private String username;
	
    // Constructors
    public HomeOwner() {}

    public HomeOwner(int uid, String username) {
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
