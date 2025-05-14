package com.cleaningsystem.controller.UserAdmin.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

@Service
public class UpdateUserAccountController {

    @Autowired
	private UserAccount userAccount;

    public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int UId) {
        return userAccount.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, UId);
    }
}
