package com.cleaningsystem.boundary;

import com.cleaningsystem.controller.UserAccountController;
import com.cleaningsystem.controller.UserProfileController;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class Boundary {

    @Autowired
    private UserAccountController userAccountC;

    @Autowired
    private UserProfileController userProfileC;

    @GetMapping("/")
    public String showHomePage(){
        return "home";
    }

    @GetMapping("/Login")
    public String showLoginForm(Model model) {
        List<String> userProfileNames = userProfileC.getProfileNames();
        model.addAttribute("loginForm", new UserAccount());
        model.addAttribute("userProfileNames", userProfileNames);
        return "login";
    }

    @PostMapping("/UserAdminHome")
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, Model model) {
        try {
            UserAccount loggedInUser = userAccountC.login(user.getUsername(), user.getPassword());
            UserProfile userProfile = userProfileC.getProfileById(loggedInUser.getProfileId());
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
            model.addAttribute("userAccountInfo", loggedInUser);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } catch (Exception e) {
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
    public String processCleanerSignUp(@ModelAttribute UserAccount user, Model model) {
        user.setProfileId(4);
        boolean isSuccessful = userAccountC.createUserAccount(user);

        if (isSuccessful) {
            UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
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
    }

    @GetMapping("/UpdateUserAccount")
    public String updateUserAccount(@RequestParam String username, Model model) {
        UserAccount user = userAccountC.getUserByUsername(username);
        System.out.println(user.getProfileId());
        model.addAttribute("updateUserAccountForm", user);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount user, Model model) {
        System.out.println(user.getUid());
        boolean isSuccessful = userAccountC.updateUserAccount(user);

        if (isSuccessful) {
            UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
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
        List<UserAccount> userAccounts = userAccountC.getAllUsers();
        List<String> profileNames = new ArrayList<>();

        for (UserAccount user : userAccounts) {
        UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();
        profileNames.add(profileName);
        }

        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("profileNames", profileNames);
        return "user_account_list";
    }

    @GetMapping("/UserAccount")
    public String showUserAccount(@RequestParam int uid, Model model) {
        UserAccount user = userAccountC.getUserById(uid);
        UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", user);
        model.addAttribute("profileName", profileName);
        return "user_account_info";
    }

    @GetMapping("/searchUserAccount")
    public String searchUserAccounts(@RequestParam String query, Model model) {
    List<UserAccount> userAccounts = userAccountC.searchUsersByUsername(query);
    List<String> profileNames = new ArrayList<>();

    for (UserAccount user : userAccounts) {
    UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
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
        boolean isSuccessful = userProfileC.createUserProfile(userProfile);

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
        int profileId= userProfileC.getProfileIdByName(profileName);
        UserProfile userProfile = userProfileC.getProfileById(profileId);
        model.addAttribute("updateUserProfileForm", userProfile);
        return "user_profile_update";
    }

    @PostMapping("/UpdateUserProfile")
    public String processUpdateUserProfile(@ModelAttribute UserProfile userProfile, Model model) {
        boolean isSuccessful = userProfileC.updateUserProfile(userProfile);

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
        List<UserProfile> userProfiles = userProfileC.getAllProfiles();
        List<Integer> profilesUserCounter = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = userAccountC.searchUsersByProfileId(userProfile.getProfileId());
            profilesUserCounter.add(userAccounts.size());
        }

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", profilesUserCounter);
        return "user_profile_list";
    }

    @GetMapping("/UserProfile")
    public String showUserProfile(@RequestParam int profileId, Model model) {
        UserProfile userProfile = userProfileC.getProfileById(profileId);
    
        model.addAttribute("userProfileInfo", userProfile);
        return "user_profile_info";
    }

    @GetMapping("/searchUserProfile")
    public String searchUserProfile(@RequestParam String query, Model model) {
    List<UserProfile> userProfiles = userProfileC.searchProfilesByName(query);
    List<Integer> profilesUserCounter = new ArrayList<>();

    for (UserProfile userProfile : userProfiles) {
        List<UserAccount> userAccounts = userAccountC.searchUsersByProfileId(userProfile.getProfileId());
        profilesUserCounter.add(userAccounts.size());
    }

    model.addAttribute("userProfiles", userProfiles);
    model.addAttribute("profilesUserCounter", profilesUserCounter);
    return "user_profile_list";
    }
}