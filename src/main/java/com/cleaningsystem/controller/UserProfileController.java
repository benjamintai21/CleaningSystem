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
    
	public Integer getProfileIdByName(String profileName) {
		return userProfile.getProfileIdByName(profileName);
	}

    public List<String> getProfileNames() {
        return userProfile.getProfileNames();
	}

	public List<String> getAllProfileNamesForUserAccounts(List<UserAccount> userAccounts) {
		return userProfile.getAllProfileNamesForUserAccounts(userAccounts);
	}


}
