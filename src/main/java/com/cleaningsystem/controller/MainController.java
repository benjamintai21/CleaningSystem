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
    private UserAccountDAO userAccountDAO;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount()); // DTO for form
        return "login";
    }

    @PostMapping("/dashboard")
    public String processLogin(@ModelAttribute("loginForm") UserAccount userAccount, Model model) {
        UserAccount loggedInUser =  userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());
        model.addAttribute("displayForm", userAccount);
        if (loggedInUser != null) {
            model.addAttribute("displayForm", loggedInUser);
            return "dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpForm", new UserAccount()); // Make sure this matches the form data type
        return "signup"; // Ensure this matches the signup.html template
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute UserAccount userAccount, Model model) {
        // Handle signup logic
        userAccountDAO.insertUserAccount(userAccount);
        UserAccount userAcc = userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());
        model.addAttribute("displayForm", userAcc);
        return "dashboard"; 
    }
}