package com.cleaningsystem.controller.UserAdmin.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

@Service
public class ViewUserAccountController {
    
    @Autowired
    private UserAccount userAccount;

    public UserAccount viewUserAccount(int uid) {
        return userAccount.viewUserAccount(uid);
    }
}
