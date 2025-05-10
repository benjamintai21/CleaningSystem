package com.cleaningsystem.model;

import java.util.*;
import jakarta.persistence.Entity;

@Entity
public class Cleaner {
	private ArrayList<ServiceListing> serviceListings;
	private int uid;
	private String username;
	
	public Cleaner() {}

	public Cleaner(int uid, String username) {
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
