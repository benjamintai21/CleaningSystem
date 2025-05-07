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

import jakarta.servlet.http.HttpSession;


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

    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clears the session
        return "redirect:/Login"; // Redirect to login page
    }

    @PostMapping("/UserAdminHome")
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, Model model) {
        try {
            UserAccount loggedInUser = userAccountC.login(user.getUsername(), user.getPassword());

            if (loggedInUser == null) {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }

            UserProfile userProfile = userProfileC.getProfileById(loggedInUser.getProfileId());
            String profileName = userProfile.getProfileName();

            model.addAttribute("userAccountInfo", loggedInUser);
            model.addAttribute("profileName", profileName);

            return "user_account_info";

        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred");
            return "login";
        }
    }

    //Create User aAccount
    @GetMapping("/CleanerUserCreation")
    public String showCleanerSignUpForm(Model model) {
        model.addAttribute("CleanerUserCreationForm", new UserAccount()); // Make sure this matches the form data type
        return "cleaner_user_creation"; // Ensure this matches the signup.html template
    }

    @PostMapping("/CleanerUserCreation")
    public String processCleanerSignUp(@ModelAttribute UserAccount user, Model model) {
        user.setProfileId(4);
        boolean isSuccessful = userAccountC.CreateAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId()); 

        if (isSuccessful) {
            UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
            String profileName = userProfile.getProfileName();
            
            model.addAttribute("userAccountInfo", user);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } else {
            model.addAttribute("error", "Signup failed! Please try again.");
            return "cleaner_user_creation";
        }
    }

    //View User Account
    @GetMapping("/ViewUserAccount")
    public String showUserAccount(@RequestParam int uid, Model model) {
        UserAccount user = userAccountC.ViewUserAccount(uid);
        UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", user);
        model.addAttribute("profileName", profileName);
        return "user_account_info";
    }

    @GetMapping("/ViewAllUserAccounts")
    public String showUserAccountList(Model model) {
        List<UserAccount> userAccounts = userAccountC.GetAllUsers();
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

    //Update User Account
    @GetMapping("/UpdateUserAccount")
    public String updateUserAccount(@RequestParam String username, Model model) {
        UserAccount user = userAccountC.ViewUserAccount(username);
        model.addAttribute("updateUserAccountForm", user);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount user, Model model) {
        boolean isSuccessful = userAccountC.UpdateUserAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId(), user.getUid()); 

        if (isSuccessful) {
            UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
            String profileName = userProfile.getProfileName();

            model.addAttribute("userAccountInfo", user);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            return "user_account_update";
        }
    }

    //Suspend User Accounts
    @PostMapping("/SuspendUserAccount")
    public String suspendUserAccounts(@ModelAttribute UserAccount user, Model model) {
        return "user_account_suspend";
    }

    //Search User Account
    @GetMapping("/searchUserAccount")
    public String searchUserAccounts(@RequestParam String query, Model model) {
    List<UserAccount> userAccounts = userAccountC.SearchUser(query);
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

    //Create User Profile
    @GetMapping("/CreateUserProfile")
    public String createUserProfile(Model model) {
        model.addAttribute("userProfileForm", new UserProfile());
        return "user_profile_creation";
    }

    @PostMapping("/CreateUserProfile")
    public String processUserProfile(@ModelAttribute UserProfile userProfile, Model model) {
        //System.out.println(userProfile.isSuspended());
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

    //View User Profile
    @GetMapping("/UserProfile")
    public String showUserProfile(@RequestParam int profileId, Model model) {
        UserProfile userProfile = userProfileC.getProfileById(profileId);
    
        model.addAttribute("userProfileInfo", userProfile);
        return "user_profile_info";
    }

    @GetMapping("/UserProfileList")
    public String showUserProfileList(Model model) {
        List<UserProfile> userProfiles = userProfileC.getAllProfiles();
        List<Integer> profilesUserCounter = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = userAccountC.SearchUser(userProfile.getProfileId());
            profilesUserCounter.add(userAccounts.size());
        }

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", profilesUserCounter);
        return "user_profile_list";
    }
    
    //Update User Profile
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

    //Search User Profile
    @GetMapping("/searchUserProfile")
    public String searchUserProfile(@RequestParam String query, Model model) {
    List<UserProfile> userProfiles = userProfileC.searchProfilesByName(query);
    List<Integer> profilesUserCounter = new ArrayList<>();

    for (UserProfile userProfile : userProfiles) {
        List<UserAccount> userAccounts = userAccountC.SearchUser(userProfile.getProfileId());
        profilesUserCounter.add(userAccounts.size());
    }

    model.addAttribute("userProfiles", userProfiles);
    model.addAttribute("profilesUserCounter", profilesUserCounter);
    return "user_profile_list";
    }
}