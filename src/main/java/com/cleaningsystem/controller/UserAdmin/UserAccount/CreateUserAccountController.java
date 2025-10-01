package com.cleaningsystem.controller.UserAdmin.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

@Service
public class CreateUserAccountController {
    
    @Autowired
	private UserAccount userAccount;

    public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
        return userAccount.createAccount(name, age, dob, gender, address, email, username, password, profileId);
    }
}
