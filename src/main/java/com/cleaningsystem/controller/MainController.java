package com.cleaningsystem.controller;

import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.model.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserAccountDAO userAccDAO;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount());
        return "login";
    }
    @PostMapping("/dashboard")
    public String processLogin(@ModelAttribute UserAccount userAccount, Model model) {
        UserAccount userAcc =  userAccDAO.login(userAccount.getUsername(), userAccount.getPassword());
        model.addAttribute("displayForm", userAcc);
        return "dashboard"; 
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpForm", new UserAccount());
        return "signup";
    }
    @PostMapping("/details")
    public String processSignUp(@ModelAttribute UserAccount userAccount, Model model) {
        userAccDAO.insertUserAccount(userAccount);
        UserAccount userAcc = userAccDAO.login(userAccount.getUsername(), userAccount.getPassword());
        model.addAttribute("displayForm", userAcc);
        return "dashboard"; 
    }
}
