package com.cleaningsystem.controller.UserAdmin.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserProfile;

@Service
public class CreateUserProfileController {
  
    @Autowired
    private UserProfile userProfile;
    
    public boolean createUserProfile(String name, String description, boolean suspended) {
		return userProfile.createUserProfile(name, description, suspended);
	}
}
