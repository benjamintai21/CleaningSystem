package com.cleaningsystem.controller;

import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dto.UserAccountDTO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.service.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount()); // DTO for form
        return "login";
    }

    @PostMapping("/dashboard")
    public String processLogin(@ModelAttribute("loginForm") UserAccount userAccount, Model model) {
        UserAccountDTO loggedInUser = userAccountService.login(userAccount, model);
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
        model.addAttribute("signUpForm", new UserAccountDTO()); // Make sure this matches the form data type
        return "signup"; // Ensure this matches the signup.html template
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute UserAccountDTO dto, @RequestParam("password") String password, Model model) {
        // Handle signup logic
        UserAccountDTO createdUser = userAccountService.signup(dto, password);

        if (createdUser != null) {
            model.addAttribute("displayForm", createdUser);
            System.out.println("Signup successful");
            return "dashboard";
        } else {
            model.addAttribute("error", "Signup failed! Please try again.");
            System.out.println("Signup failed");
            return "signup";
        }
    }
}