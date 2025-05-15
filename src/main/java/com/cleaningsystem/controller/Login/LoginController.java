package com.cleaningsystem.controller.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

@Service
public class LoginController {

    @Autowired
	private UserAccount userAccount;

    public UserAccount login(String username, String password, int profileId) {
        return userAccount.login(username, password, profileId);	
    }
}
