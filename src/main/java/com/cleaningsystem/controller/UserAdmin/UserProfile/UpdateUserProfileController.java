package com.cleaningsystem.controller.UserAdmin.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserProfile;

@Service
public class UpdateUserProfileController {

    @Autowired
    private UserProfile userProfile;

	public boolean updateUserProfile(String name, String description, boolean suspended, int profileId) {
		return userProfile.updateUserProfile(name, description, suspended, profileId);
	}
}
