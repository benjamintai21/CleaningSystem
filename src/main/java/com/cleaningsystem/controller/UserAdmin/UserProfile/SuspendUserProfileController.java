package com.cleaningsystem.controller.UserAdmin.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserProfile;

@Service
public class SuspendUserProfileController {
    
    @Autowired
    private UserProfile userProfile;

	public boolean suspendUserProfile(int profileId, boolean suspension) {
		return userProfile.suspendUserProfile(profileId, suspension);
	}

}
