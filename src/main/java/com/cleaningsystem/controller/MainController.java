package com.cleaningsystem.controller;

import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Autowired
    private UserProfileDAO userProfileDAO;

    @GetMapping("/Login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount());
        return "login";
    }

    @PostMapping("/Home")
    public String processLogin(@ModelAttribute("loginForm") UserAccount userAccount, Model model) {
        UserAccount loggedInUser = userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());
        UserProfile userProfile = userProfileDAO.getProfileById(userAccount.getProfileId());
        String profileName = userProfile.getProfileName();
        // int profileId = loggedInUser.getProfileId();
        // switch (profileId) {
        //     case 1:
        //         // return "PM_Home";
        //         break;
        //     case 2:
        //         // return "User_Admin_Home";
        //         break;
        //     case 3:
        //         // return "Home_Owner_Home";
        //         break;
        //     case 4:
        //         // return "Cleaner_Home";
        //         break;
        // }
        if (loggedInUser != null) {
            model.addAttribute("userAccountInfo", loggedInUser);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/CleanerUserCreation")
    public String showCleanerSignUpForm(Model model) {
        model.addAttribute("CleanerUserCreationForm", new UserAccount()); // Make sure this matches the form data type
        return "cleaner_user_creation"; // Ensure this matches the signup.html template
    }

    @PostMapping("/CleanerUserCreation")
    public String processCleanerSignUp(@ModelAttribute UserAccount userAccount, Model model) {
        userAccount.setProfileId(4);
        boolean isSuccessful = userAccountDAO.insertUserAccount(userAccount);

        if (isSuccessful) {
            UserProfile userProfile = userProfileDAO.getProfileById(userAccount.getProfileId());
            String profileName = userProfile.getProfileName();  
            
            model.addAttribute("userAccountInfo", userAccount);
            model.addAttribute("profileName", profileName);
            System.out.println("Signup successful");
            return "user_account_info";
        } else {
            model.addAttribute("error", "Signup failed! Please try again.");
            System.out.println("Signup failed");
            return "cleaner_user_creation";
        }
    }

    @GetMapping("/UpdateUserAccount")
    public String updateUserAccount(@RequestParam String username, Model model) {
        UserAccount userAccount = userAccountDAO.getUserByUsername(username);
        System.out.println(userAccount.getProfileId());
        model.addAttribute("updateUserAccountForm", userAccount);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount userAccount, Model model) {
        System.out.println(userAccount.getUid());
        boolean isSuccessful = userAccountDAO.updateUserAccount(userAccount);

        if (isSuccessful) {
            UserProfile userProfile = userProfileDAO.getProfileById(userAccount.getProfileId());
            String profileName = userProfile.getProfileName();

            model.addAttribute("userAccountInfo", userAccount);
            model.addAttribute("profileName", profileName);
            System.out.println("Signup successful");
            return "user_account_info";
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            System.out.println("Profile update failed");
            return "user_account_update";
        }
    }

    @GetMapping("/CreateUserProfile")
    public String createUserProfile(Model model) {
        model.addAttribute("userProfileForm", new UserProfile());
        return "user_profile_creation";
    }

    @PostMapping("/CreateUserProfile")
    public String processUserProfile(@ModelAttribute UserProfile userProfile, Model model) {
        System.out.println(userProfile.isSuspended());
        boolean isSuccessful = userProfileDAO.insertUserProfile(userProfile);

        if (isSuccessful) {
            model.addAttribute("userProfileInfo", userProfile);
            System.out.println("Signup successful");
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Profile creation failed! Please try again.");
            System.out.println("Profile creation failed");
            return "cleaner_user_creation";
        }
    }
    
    @GetMapping("/UpdateUserProfile")
    public String updateUserProfile(@RequestParam String profileName, Model model) {
        int profileId= userProfileDAO.getProfileIdByName(profileName);
        UserProfile userProfile = userProfileDAO.getProfileById(profileId);
        model.addAttribute("updateUserProfileForm", userProfile);
        return "user_profile_update";
    }

    @PostMapping("/UpdateUserProfile")
    public String processUpdateUserProfile(@ModelAttribute UserProfile userProfile, Model model) {
        boolean isSuccessful = userProfileDAO.updateUserProfile(userProfile);

        if (isSuccessful) {
            model.addAttribute("userProfileInfo", userProfile);
            System.out.println("Signup successful");
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            System.out.println("Profile update failed");
            return "user_profile_update";
        }
    }
}