package com.cleaningsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

@Service
public class UserProfileController {

    @Autowired
    private UserProfile userProfile;
    
    public boolean createUserProfile(UserProfile profile) {
		return userProfile.insertUserProfile(profile);
	}

	public UserProfile getProfileById(int profileId) {
		return userProfile.getProfileById(profileId);
	}

	public Integer getProfileIdByName(String profileName) {
		return userProfile.getProfileIdByName(profileName);
	}

    public List<String> getProfileNames() {
        return userProfile.getProfileNames();
	}

	public boolean updateUserProfile(UserProfile profile) {
		return userProfile.updateUserProfile(profile);
	}

	public boolean setSuspensionStatus(int profileId, boolean suspension) {
		return userProfile.setSuspensionStatusForAllAccounts(profileId, suspension);
	}

	public List<UserProfile> searchProfilesByName(String keyword) {
		return userProfile.searchProfilesByName(keyword);
	}

	public List<UserProfile> getAllProfiles() {
		return userProfile.getAllProfiles();
	}

	public List<String> getAllProfileNamesForUserAccounts(List<UserAccount> userAccounts) {
		return userProfile.getAllProfileNamesForUserAccounts(userAccounts);
	}


}
