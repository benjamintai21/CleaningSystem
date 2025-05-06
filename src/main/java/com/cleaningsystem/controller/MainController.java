package com.cleaningsystem.controller;

import com.cleaningsystem.dao.UserAccountDAO;
<<<<<<< HEAD
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
=======
import com.cleaningsystem.model.UserAccount;
>>>>>>> 6bd535b67ce027f050dc555ce4d701b7d1b2e079

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
<<<<<<< HEAD
    private UserAccount userAccount;
=======
    private UserAccountDAO userAccountDAO;
>>>>>>> 6bd535b67ce027f050dc555ce4d701b7d1b2e079

    @Autowired
    private UserProfileDAO userProfileDAO;

    @GetMapping("/Login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new UserAccount());
        return "login";
    }

<<<<<<< HEAD
    @PostMapping("/Home")
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, Model model) {
        UserAccount loggedInUser = userAccount.login(user.getUsername(), user.getPassword());
        UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
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
=======
    @PostMapping("/dashboard")
    public String processLogin(@ModelAttribute("loginForm") UserAccount userAccount, Model model) {
        UserAccount loggedInUser =  userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());
        model.addAttribute("displayForm", userAccount);
>>>>>>> 6bd535b67ce027f050dc555ce4d701b7d1b2e079
        if (loggedInUser != null) {
            model.addAttribute("userAccountInfo", loggedInUser);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

<<<<<<< HEAD
    @GetMapping("/CleanerUserCreation")
    public String showCleanerSignUpForm(Model model) {
        model.addAttribute("CleanerUserCreationForm", new UserAccount()); // Make sure this matches the form data type
        return "cleaner_user_creation"; // Ensure this matches the signup.html template
    }

    @PostMapping("/CleanerUserCreation")
    public String processCleanerSignUp(@ModelAttribute UserAccount user, Model model) {
        user.setProfileId(4);
        boolean isSuccessful = userAccount.insertUserAccount(user);

        if (isSuccessful) {
            UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
            String profileName = userProfile.getProfileName();  
            
            model.addAttribute("userAccountInfo", user);
            model.addAttribute("profileName", profileName);
            System.out.println("Signup successful");
            return "user_account_info";
        } else {
            model.addAttribute("error", "Signup failed! Please try again.");
            System.out.println("Signup failed");
            return "cleaner_user_creation";
        }
=======
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
>>>>>>> 6bd535b67ce027f050dc555ce4d701b7d1b2e079
    }

    @GetMapping("/UpdateUserAccount")
    public String updateUserAccount(@RequestParam String username, Model model) {
        UserAccount user = userAccount.getUserByUsername(username);
        System.out.println(user.getProfileId());
        model.addAttribute("updateUserAccountForm", user);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount user, Model model) {
        System.out.println(user.getUid());
        boolean isSuccessful = userAccount.updateUserAccount(user);

        if (isSuccessful) {
            UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
            String profileName = userProfile.getProfileName();

            model.addAttribute("userAccountInfo", user);
            model.addAttribute("profileName", profileName);
            System.out.println("Signup successful");
            return "user_account_info";
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            System.out.println("Profile update failed");
            return "user_account_update";
        }
    }

    @GetMapping("/UserAccountList")
    public String showUserAccountList(Model model) {
        List<UserAccount> userAccounts = userAccount.getAllUsers();
        List<String> profileNames = new ArrayList<>();

        for (UserAccount user : userAccounts) {
        UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();
        profileNames.add(profileName);
        }

        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("profileNames", profileNames);
        return "user_account_list";
    }

    @GetMapping("/UserAccount")
    public String showUserAccount(@RequestParam int uid, Model model) {
        UserAccount user = userAccount.getUserById(uid);
        UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", user);
        model.addAttribute("profileName", profileName);
        return "user_account_info";
    }

    @GetMapping("/searchUserAccount")
    public String searchUserAccounts(@RequestParam String query, Model model) {
    List<UserAccount> userAccounts = userAccount.searchUsersByUsername(query);
    List<String> profileNames = new ArrayList<>();

    for (UserAccount user : userAccounts) {
    UserProfile userProfile = userProfileDAO.getProfileById(user.getProfileId());
    String profileName = userProfile.getProfileName();
    profileNames.add(profileName);
    }

    model.addAttribute("userAccounts", userAccounts);
    model.addAttribute("profileNames", profileNames);
    return "user_account_list";
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

    @GetMapping("/UserProfileList")
    public String showUserProfileList(Model model) {
        List<UserProfile> userProfiles = userProfileDAO.getAllProfiles();
        List<Integer> profilesUserCounter = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = userAccount.searchUsersByProfileId(userProfile.getProfileId());
            profilesUserCounter.add(userAccounts.size());
        }

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", profilesUserCounter);
        return "user_profile_list";
    }

    @GetMapping("/UserProfile")
    public String showUserProfile(@RequestParam int profileId, Model model) {
        UserProfile userProfile = userProfileDAO.getProfileById(profileId);
    
        model.addAttribute("userProfileInfo", userProfile);
        return "user_profile_info";
    }

    @GetMapping("/searchUserProfile")
    public String searchUserProfile(@RequestParam String query, Model model) {
    List<UserProfile> userProfiles = userProfileDAO.searchProfilesByName(query);
    List<Integer> profilesUserCounter = new ArrayList<>();

    for (UserProfile userProfile : userProfiles) {
        List<UserAccount> userAccounts = userAccount.searchUsersByProfileId(userProfile.getProfileId());
        profilesUserCounter.add(userAccounts.size());
    }

    model.addAttribute("userProfiles", userProfiles);
    model.addAttribute("profilesUserCounter", profilesUserCounter);
    return "user_profile_list";
    }
}