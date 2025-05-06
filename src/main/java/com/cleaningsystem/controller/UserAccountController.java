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

    public UserAccount login(String username, String password) {
        return userAccountDAO.login(username, password);	
    }

    public boolean createUserAccount(UserAccount user) {
        return userAccountDAO.insertUserAccount(user);
    }

    public UserAccount getUserById(int uid) {
        return userAccountDAO.getUserById(uid);
    }

    public UserAccount getUserByUsername(String username) {
        return userAccountDAO.getUserByUsername(username);
    }

    public boolean updateUserAccount(UserAccount user) {
        return userAccountDAO.updateUserAccount(user);
    }

    public boolean deleteUserAccount(int uid) {
        return userAccountDAO.deleteUserAccount(uid);
    }

    public List<UserAccount> searchUsersByUsername(String keyword) {
        return userAccountDAO.searchUsersByUsername(keyword);
    }

    public List<UserAccount> searchUsersByProfileId(int profileId) {
        return userAccountDAO.searchUsersByProfileId(profileId);
    }

    public List<UserAccount> getAllUsers() {
        return userAccountDAO.getAllUsers();
    }
}
