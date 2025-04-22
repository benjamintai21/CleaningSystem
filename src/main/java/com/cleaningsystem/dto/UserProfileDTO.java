package com.cleaningsystem.dto;

public class UserProfileDTO {
	private int profileID;
	private String profileName;
	private String description;
	private boolean suspension;

	// Constructor
	public UserProfileDTO(int profileID, String profileName, String description, boolean suspension) {
		this.profileID = profileID;
		this.profileName = profileName;
		this.description = description;
		this.suspension = suspension;
	}

	// Getters and Setters
	public int getProfileID() { return profileID; }
	public void setProfileID(int profileID) { this.profileID = profileID; }

	public String getProfileName() { return profileName; }
	public void setProfileName(String profileName) { this.profileName = profileName; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public boolean isSuspended() { return suspension; }
	public void setSuspended(boolean suspension) { this.suspension = suspension; }
}
