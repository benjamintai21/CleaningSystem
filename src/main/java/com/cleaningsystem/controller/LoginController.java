package com.cleaningsystem.controller;

import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.util.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount());
        return "login";
    }

    @PostMapping("/dashboard")
    public String processLogin(@ModelAttribute UserAccount userAccount, Model model) {
        userAccountService.login(userAccount, model);
    // Simulate simple login c
  
    return "dashboard"; 

    }

    // @GetMapping("/signup")
    // public String showSignUpForm(Model model) {
    //     model.addAttribute("signUpForm", new UserAccount());
    //     return "signup";
    // }
}
