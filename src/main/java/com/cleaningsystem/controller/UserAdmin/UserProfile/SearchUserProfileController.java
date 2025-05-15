package com.cleaningsystem.controller.UserAdmin.UserProfile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

@Service
public class SearchUserProfileController {
    
    @Autowired
    private UserProfile userProfile;

    public List<UserProfile> searchUserProfile() {
		return userProfile.searchUserProfile();
	}
    
	public List<UserProfile> searchUserProfile(String keyword) {
		return userProfile.searchUserProfile(keyword);
	}

	public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> userAccounts) {
		return userProfile.searchUserProfileNamesForUserAccounts(userAccounts);
	}

}
