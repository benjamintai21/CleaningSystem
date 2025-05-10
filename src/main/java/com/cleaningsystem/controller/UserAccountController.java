package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.model.UserAccount;

@Service
public class UserAccountController {
    
    @Autowired
	private UserAccountDAO userAccountDAO;

    public UserAccount login(String username, String password, int profileId) {
        return userAccountDAO.login(username, password, profileId);	
    }

    public boolean CreateAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
        return userAccountDAO.insertUserAccount(name, age, dob, gender, address, email, username, password, profileId);
    }

    public UserAccount ViewUserAccount(int uid) {
        return userAccountDAO.getUserById(uid);
    }

    public UserAccount ViewUserAccount(String username) {
        return userAccountDAO.getUserByUsername(username);
    }

    public boolean UpdateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int UId) {
        return userAccountDAO.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, UId);
    }

    public boolean setSuspensionStatus(int uid, boolean suspended) {
        return userAccountDAO.setSuspensionStatus(uid, suspended);
    }

    public List<UserAccount> SearchUser(String keyword) {
        return userAccountDAO.searchUsersByUsername(keyword);
    }

    public List<UserAccount> SearchUser(int profileId) {
        return userAccountDAO.searchUsersByProfileId(profileId);
    }

    public List<UserAccount> getAllUsers() {
        return userAccountDAO.getAllUsers();
    }
}
