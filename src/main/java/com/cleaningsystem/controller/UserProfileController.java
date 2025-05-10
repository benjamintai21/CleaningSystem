package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserProfile;

@Service
public class UserProfileController {

    @Autowired
    private UserProfileDAO userProfileDAO;
    
    public boolean createUserProfile(UserProfile profile) {
		return userProfileDAO.insertUserProfile(profile);
	}

	public UserProfile getProfileById(int profileId) {
		return userProfileDAO.getProfileById(profileId);
	}

	public Integer getProfileIdByName(String profileName) {
		return userProfileDAO.getProfileIdByName(profileName);
	}

    public List<String> getProfileNames() {
        return userProfileDAO.getProfileNames();
	}

	public boolean updateUserProfile(UserProfile profile) {
		return userProfileDAO.updateUserProfile(profile);
	}

	public boolean setSuspensionStatus(int profileId, boolean suspension) {
		return userProfileDAO.setSuspensionStatusForAllAccounts(profileId, suspension);
	}

	public List<UserProfile> searchProfilesByName(String keyword) {
		return userProfileDAO.searchProfilesByName(keyword);
	}

	public List<UserProfile> getAllProfiles() {
		return userProfileDAO.getAllProfiles();
	}
}
