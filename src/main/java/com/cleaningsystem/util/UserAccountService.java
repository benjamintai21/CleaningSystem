package com.cleaningsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.dao.UserAccountDAO;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountDAO userAccountDAO;
    
    public void login(UserAccount userAccount, Model model) {
    
        UserAccount loggedInUser = userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());
      
        model.addAttribute("displayForm", loggedInUser);
    }
}
