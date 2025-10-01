package com.cleaningsystem.controller.UserAdmin.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.UserAccount;

@Service
public class SuspendUserAccountController {

    @Autowired
	private UserAccount userAccount;

    public boolean suspendUserAccount(int uid, boolean suspended) {
        return userAccount.suspendUserAccount(uid, suspended);
    }
}
