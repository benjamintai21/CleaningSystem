package com.cleaningsystem.controller.UserAdmin.UserAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

@Service
public class SearchUserAccountController {

    @Autowired
	private UserAccount userAccount;

        public List<UserAccount> searchUserAccount() {
        return userAccount.searchUserAccount();
    }

    public List<UserAccount> searchUserAccount(int profileId) {
        return userAccount.searchUserAccount(profileId);
    }

    public List<UserAccount> searchUserAccount(String keyword) {
        return userAccount.searchUserAccount(keyword);
    }

    public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> userProfiles){
		return userAccount.searchUserAccountPerProfileCount(userProfiles);
	}

	public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> serviceListings) {
		return userAccount.searchUserAccountNamesByServiceListings(serviceListings);
	}

	public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> shortlists) {
        return userAccount.searchUserAccountFromShortlist(shortlists);
	}
}
