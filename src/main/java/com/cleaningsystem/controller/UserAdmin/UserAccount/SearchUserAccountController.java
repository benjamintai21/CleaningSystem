package com.cleaningsystem.controller.UserAdmin.UserAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

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

}
