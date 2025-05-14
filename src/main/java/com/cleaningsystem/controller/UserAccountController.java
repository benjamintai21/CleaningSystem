package com.cleaningsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

@Service
public class UserAccountController {
    @Autowired
	private UserAccount userAccount;

    public String getProfileNameByUid(int uid) {
        return userAccount.getProfileNameByUid(uid);
    }

    public List<Integer> getUsersPerProfileCount(List<UserProfile> userProfiles){
		return userAccount.getUsersPerProfileCount(userProfiles);
	}

	public List<String> getAllCleanerNamesByServiceListings(List<ServiceListing> serviceListings) {
		return userAccount.getAllCleanerNamesByServiceListings(serviceListings);
	}

	public List<UserAccount> getCleanerAccountsFromShortlist(List<CleanerShortlist> shortlists) {
        return userAccount.getCleanerAccountsFromShortlist(shortlists);
	}
}
