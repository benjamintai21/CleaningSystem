package com.cleaningsystem.controller.UserAdmin.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserProfile;

@Service
public class ViewUserProfileController {

    @Autowired
    private UserProfile userProfile;
    
	public UserProfile viewUserProfile(int profileId) {
		return userProfile.viewUserProfile(profileId);
	}
}
