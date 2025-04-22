package com.cleaningsystem.model;

public class UserProfile {
	private int profileID;
	private String profileName;
	private String description;
	private boolean suspended;
	
	// No-args constructor
	public UserProfile() {}
	
	// Constructor with all fields except ID
	public UserProfile(String profileName, String description, boolean suspended) {
		this.profileName = profileName;
		this.description = description;
		this.suspended = suspended;
	}
	
	// Constructor with all fields including ID
	public UserProfile(int profileID, String profileName, String description, boolean suspended) {
		this(profileName, description, suspended);
		this.profileID = profileID;
	}
	
	//Getters
	public int getProfileID() {return profileID;}
	public String getProfileName() {return profileName;}
	public String getDescription() {return description;}
	public boolean isSuspended() {return suspended;}
	
	//Setters
	public void setProfileID(int profileID) {this.profileID = profileID;}
	public void setProfileName(String profileName) {this.profileName = profileName;}
	public void setDescription(String description) {this.description = description;}
	public void setSuspended(boolean suspended) {this.suspended = suspended;}
	
	@Override
	public String toString() {
		return "UserProfile{" +
				"profileID=" + profileID +
				", profileName='" + profileName + '\'' +
				", description='" + description + '\'' +
				", suspended=" + suspended +
				'}';
	}
}
