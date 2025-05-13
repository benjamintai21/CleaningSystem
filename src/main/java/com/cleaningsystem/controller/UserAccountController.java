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

    public UserAccount login(String username, String password, int profileId) {
        return userAccount.login(username, password, profileId);	
    }

    public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
        return userAccount.insertUserAccount(name, age, dob, gender, address, email, username, password, profileId);
    }

    public UserAccount viewUserAccount(int uid) {
        return userAccount.getUserById(uid);
    }

    public UserAccount viewUserAccount(String username) {
        return userAccount.getUserByUsername(username);
    }

    public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int UId) {
        return userAccount.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, UId);
    }

    public boolean setSuspensionStatus(int uid, boolean suspended) {
        return userAccount.setSuspensionStatus(uid, suspended);
    }

    public List<UserAccount> searchUser(String keyword) {
        return userAccount.searchUsersByUsername(keyword);
    }

    public List<UserAccount> searchUser(int profileId) {
        return userAccount.searchUsersByProfileId(profileId);
    }

    public List<UserAccount> getAllUsers() {
        return userAccount.getAllUsers();
    }

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
