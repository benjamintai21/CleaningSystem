package com.cleaningsystem.boundary;

import com.cleaningsystem.controller.UserAccountController;
import com.cleaningsystem.controller.UserProfileController;
import com.cleaningsystem.controller.ServiceListingController;
import com.cleaningsystem.controller.ServiceCategoryController;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.model.ServiceListing;
import com.cleaningsystem.model.ServiceCategory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class Boundary {

    @Autowired
    private UserAccountController userAccountC;

    @Autowired
    private UserProfileController userProfileC;

    @Autowired 
    private ServiceListingController serviceListingC;

    @Autowired
    private ServiceCategoryController serviceCategoryC;

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

    @PostMapping("/RedirectToPage")
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            UserAccount loggedInUser = userAccountC.login(user.getUsername(), user.getPassword(), user.getProfileId());

            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("toastMessage", "Invalid username or password");
                return "redirect:/Login";
            }
            UserProfile userProfile = userProfileC.getProfileById(loggedInUser.getProfileId());
            String profileName = userProfile.getProfileName();
            System.out.println(loggedInUser.getProfileId());

            session.setAttribute("uid", loggedInUser.getUid());
            session.setAttribute("username", loggedInUser.getUsername());
            session.setAttribute("profileId", loggedInUser.getProfileId());

            model.addAttribute("userAccountInfo", loggedInUser);
            model.addAttribute("profileName", profileName);
            // model.addAttribute("username", loggedInUser.getUsername());  

            return "redirect:/" + profileName.trim().replaceAll(" ", "") + "Home";
            //return "user_account_info";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("toastMessage", "Invalid username or password");
            return "redirect:/Login";
        }
    }

    //--AdminPage
    @GetMapping("/UserAdminHome")
    public String showUserAdminHome(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        return "useradmin_home_page";
    }

    @GetMapping("/CleanerHome")
    public String showCleanerHome(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        return "cleaner_home_page";
    }

    @GetMapping("/HomeOwnerHome")
    public String showHomeOwnerHome(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        return "homeowner_home_page";
    }

    @GetMapping("/PlatformManagerHome")
    public String showPlatformManagerHome(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        return "platformmanager_home_page";
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
    public String showUpdateUserAccount(@RequestParam String username, Model model) {
        UserAccount user = userAccountC.ViewUserAccount(username);
        model.addAttribute("userAccountInfo", user);
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

    // //Suspend User Accounts
    // @GetMapping("/SuspendUserAccount")
    // public String showSuspendUserAccount(@RequestParam int uid, Model model) {
    //     UserAccount user = userAccountC.ViewUserAccount(uid);
    //     model.addAttribute("userAccountInfo", user); // Make sure this matches the form data type
    //     return "user_account_info";
    // }

    @PostMapping("/SuspendUserAccount")
    public String processSuspendUserAccount(@ModelAttribute UserAccount user, Model model) {
    boolean isSuccessful = userAccountC.SuspendUserAccount(user.getUid()); 

    if (isSuccessful) {
        UserAccount updatedUser = userAccountC.ViewUserAccount(user.getUid());
        System.out.println(updatedUser.getSuspended()); // <-- re-fetch after update
        UserProfile userProfile = userProfileC.getProfileById(updatedUser.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", updatedUser); // Use the updated user
        model.addAttribute("profileName", profileName);
        model.addAttribute("message", "User suspended successfully");
    } else {
        model.addAttribute("error", "Failed to suspend user");
    }

    return "redirect:/ViewUserAccount?uid=" + user.getUid();
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

    @GetMapping("/ViewAllUserProfiles")
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

    //Cleaner
    @GetMapping("/CleanerCreateService")
    public String showServiceListing(Model model) {
        List<ServiceCategory> categories = serviceCategoryC.GetAllCategories(); // Your service call
        model.addAttribute("serviceListing", new ServiceListing());
        model.addAttribute("serviceCategory", categories);
        return "cleaner_create_service_listing";
    }

    @PostMapping("/CleanerCreateService")
    public String processServiceListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        boolean isSuccessful = serviceListingC.CreateServiceListing(serviceListing.getName(), uid, serviceListing.getServiceCategory().getCategoryId(),
                                                                        serviceListing.getDescription(), serviceListing.getPricePerHour(), serviceListing.getStatus(),                                                                  serviceListing.getStartDate(), serviceListing.getEndDate());
        if (isSuccessful) {
            model.addAttribute("serrviceListingInfo", serviceListing);
            System.out.println("Service Listing creation successful");
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Service Listing creation failed! Please try again.");
            System.out.println("Service Listing creation failed");
            return "cleaner_create_service_listing";
        }
    }

    @GetMapping("/CleanerUpdateService")
    public String showUpdateListing(Model model) {
        model.addAttribute("serviceListing", new ServiceListing());
        return "cleaner_update_service_listing";
    }

    // @PostMapping("/CleanerUpdateService")
    // public String processUpdateListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {

    // public String createService(@ModelAttribute UserProfile userProfile, Model model) {
    //     boolean isSuccessful = serviceListingC.(userProfile);

    //     if (isSuccessful) {
    //         model.addAttribute("userProfileInfo", userProfile);
    //         System.out.println("Signup successful");
    //         return "user_profile_info";
    //     } else {
    //         model.addAttribute("error", "Profile creation failed! Please try again.");
    //         System.out.println("Profile creation failed");
    //         return "cleaner_user_creation";
    //     }
        
    // }
}