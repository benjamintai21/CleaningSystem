package com.cleaningsystem.dto;

public class UserProfileDTO {
	private int profileId;
	private String profileName;
	private String description;
	private boolean suspension;

	// Constructor
	
	public UserProfileDTO(){
		// Default constructor
	}

	public UserProfileDTO(int profileId, String profileName, String description, boolean suspension) {
		this.profileId = profileId;
		this.profileName = profileName;
		this.description = description;
		this.suspension = suspension;
	}

	// Getters and Setters
	public int getProfileId() { return profileId; }
	public void setProfileId(int profileId) { this.profileId = profileId; }

	public String getProfileName() { return profileName; }
	public void setProfileName(String profileName) { this.profileName = profileName; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public boolean isSuspended() { return suspension; }
	public void setSuspended(boolean suspension) { this.suspension = suspension; }
}
