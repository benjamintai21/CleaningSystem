package com.cleaningsystem.model;

public class UserProfile {
	private int profileId;
	private String profileName;
	private String description;
	private boolean suspended;
	
	// No-args constructor
	public UserProfile() {}
	
	// Constructor with all fields except Id
	public UserProfile(String profileName, String description, boolean suspended) {
		this.profileName = profileName;
		this.description = description;
		this.suspended = suspended;
	}
	
	// Constructor with all fields including Id
	public UserProfile(int profileId, String profileName, String description, boolean suspended) {
		this(profileName, description, suspended);
		this.profileId = profileId;
	}
	
	//Getters
	public int getProfileId() {return profileId;}
	public String getProfileName() {return profileName;}
	public String getDescription() {return description;}
	public boolean isSuspended() {return suspended;}
	
	//Setters
	public void setProfileId(int profileId) {this.profileId = profileId;}
	public void setProfileName(String profileName) {this.profileName = profileName;}
	public void setDescription(String description) {this.description = description;}
	public void setSuspended(boolean suspended) {this.suspended = suspended;}
	
	@Override
	public String toString() {
		return "UserProfile{" +
				"profileId=" + profileId +
				", profileName='" + profileName + '\'' +
				", description='" + description + '\'' +
				", suspended=" + suspended +
				'}';
	}
}
