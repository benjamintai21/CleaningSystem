package com.cleaningsystem.boundary;

import com.cleaningsystem.controller.UserAccountController;
import com.cleaningsystem.controller.UserProfileController;
import com.cleaningsystem.entity.Booking;
import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.Report;
import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;
import com.cleaningsystem.controller.ServiceListingController;
import com.cleaningsystem.controller.ShortlistController;
import com.cleaningsystem.controller.ServiceCategoryController;
import com.cleaningsystem.controller.BookingController;
import com.cleaningsystem.controller.ReportController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

    @Autowired
    private BookingController bookingC;

    @Autowired
    private ShortlistController shortlistC;

    @Autowired
    private ReportController reportC;

    @GetMapping("/")
    public String showHomePage(Model model){
        // Cleaners
        List<UserAccount> cleaners = userAccountC.searchUser(4);
        List<Integer> servicesCountList = serviceListingC.getServicesCountList();
        List<ServiceListing> serviceListings = serviceListingC.getAllListings();

        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleaners", cleaners);
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
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserAccount loggedInUser = userAccountC.login(user.getUsername(), user.getPassword(), user.getProfileId());

            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("toastMessage", "Invalid username or password");
                return "redirect:/Login";
            }
            if (loggedInUser.isSuspended()) {
                redirectAttributes.addFlashAttribute("toastMessage", "Your account has been suspended");
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

    // CHECK ACCESSSSSSSSSSSSSSSSSSSS--------------------------------------------------
    public Optional<Integer> checkAccess(HttpSession session, String userProfileName) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return Optional.empty();
        }
        int uid = (int) uidObj;
        String profileName = userAccountC.getProfileNameByUid(uid);
        if (!profileName.equals(userProfileName)){
            return Optional.empty();
        }

        return Optional.of(uid);
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

    //Create User Account
    @GetMapping("/CleanerUserCreation")
    public String showCleanerSignUpForm(Model model) {
        model.addAttribute("CleanerUserCreationForm", new UserAccount()); // Make sure this matches the form data type
        return "cleaner_user_creation"; // Ensure this matches the signup.html template
    }

    @PostMapping("/CleanerUserCreation")
    public String processCleanerSignUp(@ModelAttribute UserAccount user, Model model) {
        user.setProfileId(4);
        boolean isSuccessful = userAccountC.createAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId()); 

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
    @GetMapping("/UserAccount")
    public String showUserAccount(@RequestParam int uid, Model model) {
        UserAccount user = userAccountC.viewUserAccount(uid);
        UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", user);
        model.addAttribute("profileName", profileName);
        return "user_account_info";
    }

    @GetMapping("/ViewAllUserAccounts")
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

    //Update User Account
    @GetMapping("/UpdateUserAccount")
    public String showUpdateUserAccount(@RequestParam String username, Model model) {
        UserAccount user = userAccountC.viewUserAccount(username);
        model.addAttribute("userAccountInfo", user);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount user, Model model) {
        boolean isSuccessful = userAccountC.updateUserAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId(), user.getUid()); 

        if (isSuccessful) {
            UserProfile userProfile = userProfileC.getProfileById(user.getProfileId());
            String profileName = userProfile.getProfileName();

            model.addAttribute("userAccountInfo", user);
            model.addAttribute("profileName", profileName);
            return "redirect:/UserAccount?uid=" + user.getUid();
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            return "user_account_update";
        }
    }

    // Suspend User Account
    @PostMapping("/SetAccountSuspensionStatus")
    public String processAccountSuspensionStatus(@RequestParam boolean suspended, @ModelAttribute UserAccount user, Model model) {
    boolean isSuccessful = userAccountC.setSuspensionStatus(user.getUid(), suspended); 

    if (isSuccessful) {
        UserAccount updatedUser = userAccountC.viewUserAccount(user.getUid());
        UserProfile userProfile = userProfileC.getProfileById(updatedUser.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", updatedUser); // Use the updated user
        model.addAttribute("profileName", profileName);

        if (updatedUser.isSuspended()) {
        model.addAttribute("message", "User suspended successfully");
        } else {
        model.addAttribute("message", "User unsuspended successfully");
        }
    } else {
        if(suspended){
        model.addAttribute("error", "Failed to suspend user");
        } else {
        model.addAttribute("error", "Failed to unsuspend user");
        }
    }

    return "redirect:/UserAccount?uid=" + user.getUid();
    }

    //Search User Account
    @GetMapping("/searchUserAccount")
    public String searchUserAccounts(@RequestParam String query, Model model) {
        List<UserAccount> userAccounts = userAccountC.searchUser(query);
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
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Profile creation failed! Please try again.");
            return "cleaner_user_creation";
        }
    }

    //View User Profile
    @GetMapping("/UserProfile")
    public String showUserProfile(@RequestParam int profileId, Model model) {
        UserProfile userProfile = userProfileC.getProfileById(profileId);
        List<UserAccount> userAccounts = userAccountC.searchUser(userProfile.getProfileId());
        int usersCount = userAccounts.size();
    
        model.addAttribute("userProfileInfo", userProfile);
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("usersCount", usersCount);
        return "user_profile_info";
    }

    @GetMapping("/ViewAllUserProfiles")
    public String showUserProfileList(Model model) {
        List<UserProfile> userProfiles = userProfileC.getAllProfiles();
        List<Integer> profilesUserCounter = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = userAccountC.searchUser(userProfile.getProfileId());
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
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            return "user_profile_update";
        }
    }

    // Suspend User Profile
    @PostMapping("/SetProfileSuspensionStatus")
    public String processProfileSuspensionStatus(@RequestParam boolean suspended, @ModelAttribute UserProfile profile, Model model) {
    boolean isSuccessful = userProfileC.setSuspensionStatus(profile.getProfileId(), suspended); 
    
    if (isSuccessful) {

        if (suspended) {
        model.addAttribute("message", "User suspended successfully");
        } else {
        model.addAttribute("message", "User unsuspended successfully");
        }
    } else {
        if(suspended){
        model.addAttribute("error", "Failed to suspend user");
        } else {
        model.addAttribute("error", "Failed to unsuspend user");
        }
    }

    return "redirect:/UserProfile?profileId=" + profile.getProfileId();
    }

    //Search User Profile
    @GetMapping("/searchUserProfile")
    public String searchUserProfile(@RequestParam String query, Model model) {
        List<UserProfile> userProfiles = userProfileC.searchProfilesByName(query);
        List<Integer> profilesUserCounter = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = userAccountC.searchUser(userProfile.getProfileId());
            profilesUserCounter.add(userAccounts.size());
        }

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", profilesUserCounter);
        return "user_profile_list";
    }

    //Cleaner
    @GetMapping("/CleanerCreateService")
    public String showServiceListing(Model model) {
        List<ServiceCategory> categories = serviceCategoryC.getAllCategories(); // Your service call
        model.addAttribute("serviceListing", new ServiceListing());
        model.addAttribute("serviceCategories", categories);
        model.addAttribute("serviceStatuses", ServiceListing.Status.values()); // Enum values
        return "cleaner_create_service_listing";
    }

    @PostMapping("/CleanerCreateService")
    public String processServiceListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;
        
        boolean isSuccessful = serviceListingC.createServiceListing(serviceListing.getName(), uid , serviceListing.getCategoryId(),
                                                                        serviceListing.getDescription(), serviceListing.getPricePerHour(),
                                                                        serviceListing.getStartDate(), serviceListing.getEndDate(), serviceListing.getStatus());
        if (isSuccessful) {
            ServiceListing latest = serviceListingC.getLastListing();
            model.addAttribute("serviceListingInfo", latest);
            model.addAttribute("serviceCategory", serviceCategoryC.viewServiceCategory(serviceListing.getCategoryId()));
            return "cleaner_view_service_listing";
        } else {
            model.addAttribute("error", "Service Listing creation failed! Please try again.");
            return "cleaner_create_service_listing";
        }
    }

    //View User Account
    @GetMapping("/ServiceListing")
    public String showServiceListing(@RequestParam int serviceId, HttpSession session, Model model) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        ServiceListing listing = serviceListingC.viewServiceListing(serviceId, uid);
        ServiceCategory category = serviceCategoryC.viewServiceCategory(listing.getCategoryId());
        model.addAttribute("serviceListingInfo", listing);
        model.addAttribute("serviceCategory", category);
        return "cleaner_view_service_listing";
    }

    @GetMapping("/ViewAllServiceListings")
    public String showServiceListingList(HttpSession session, Model model) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        List<ServiceListing> servicelistings = serviceListingC.getAllListingsById(uid);
        model.addAttribute("serviceListings", servicelistings);
        return "cleaner_service_list";
    }

    @GetMapping("/CleanerUpdateService")
    public String showUpdateListing(@RequestParam int serviceId, HttpSession session, Model model) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        ServiceListing listing = serviceListingC.viewServiceListing(serviceId, uid);
        model.addAttribute("serviceListingInfo", listing);
        model.addAttribute("serviceCategories", serviceCategoryC.getAllCategories());
        model.addAttribute("serviceStatuses", ServiceListing.Status.values()); 
        return "cleaner_update_service_listing";
    }

    @PostMapping("/CleanerUpdateService")
    public String processUpdateListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {
        boolean isSuccessful = serviceListingC.updateServiceListing(serviceListing.getName(), serviceListing.getCleanerId(),
                                                                    serviceListing.getCategoryId(), serviceListing.getDescription(),
                                                                    serviceListing.getPricePerHour(), serviceListing.getStartDate(),
                                                                    serviceListing.getEndDate(), serviceListing.getStatus(),
                                                                    serviceListing.getServiceId());
        if (isSuccessful) {
            ServiceCategory category = serviceCategoryC.viewServiceCategory(serviceListing.getCategoryId());
            model.addAttribute("serviceListingInfo", serviceListing);
            model.addAttribute("serviceCategory", category);
            model.addAttribute("message", "Successfully updated");
            return "cleaner_view_service_listing";
        } else {
            model.addAttribute("error", "Update failed! Please try again.");
            return "cleaner_update_service_listing";
        }
    }

    @PostMapping("CleanerDeleteService")
    public String processDeleteListing(@ModelAttribute ServiceListing listing, Model model, HttpSession session) {
        boolean isSuccessful = serviceListingC.deleteServiceListing(listing.getServiceId());

        if (isSuccessful) {
            
            model.addAttribute("message", "Profile creation failed! Please try again.");
        } else {
            model.addAttribute("error", "Profile creation failed! Please try again.");
        }
        return "redirect:/ViewAllServiceListings";
    }

    //Search ServiceListing
    @GetMapping("/searchServiceListing")
    public String searchServiceListing(@RequestParam String query, HttpSession session, Model model) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        List<ServiceListing> servicelistings = serviceListingC.searchServiceListing(uid, query);
        model.addAttribute("serviceListings", servicelistings);
        return "cleaner_service_list";
    }

    @GetMapping("CleanerViewRecords")
    public String showPastRecords(HttpSession session, Model model){
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        List<Booking> matches = bookingC.getConfirmedMatches(uid);
        Map<Integer, ServiceListing> serviceListings = new HashMap<>();
        for (Booking booking : matches) {
            int serviceId = booking.getServiceId();
            ServiceListing listing = serviceListingC. viewServiceListing(serviceId, uid);
            serviceListings.put(serviceId, listing);
        }
        model.addAttribute("matches", matches);
        model.addAttribute("serviceListings", serviceListings);
        return "cleaner_record_list";
    }
    
    //Search ServiceListing
    @GetMapping("/searchCleanerRecord")
    public String searchPastRecords(@RequestParam String query, HttpSession session, Model model) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return "redirect:/Login";
        }
        int uid = (int) uidObj;

        List<Booking> matches = bookingC.searchConfirmedMatches(uid, query);
        Map<Integer, ServiceListing> serviceListings = new HashMap<>();
        for (Booking booking : matches) {
            int serviceId = booking.getServiceId();
            ServiceListing listing = serviceListingC. viewServiceListing(serviceId, uid);
            serviceListings.put(serviceId, listing);
        }
        model.addAttribute("matches", matches);
        model.addAttribute("serviceListings", serviceListings);
        return "cleaner_record_list";
    }

    //--Platform Managerrrr
    @GetMapping("/PlatformManagerHome")
    public String showPlatformManagerHome(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }
        model.addAttribute("username", session.getAttribute("username"));
        return "platformmanager_home_page";
    }

    // Create Service Catetory
    @GetMapping("/ServiceCategoryCreation")
    public String showServiceCategoryCreation(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }
        model.addAttribute("serviceCategory", new ServiceCategory());
        return "pm_create_service_category";
    }

    @PostMapping("/CreateServiceCategory")
    public String processServiceCategoryCreation(@ModelAttribute ServiceCategory serviceCategory, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        }
        boolean isSuccessful = serviceCategoryC.createServiceCategory(serviceCategory.getType(), serviceCategory.getName(), serviceCategory.getDescription());
        String profileName = userAccountC.getProfileNameByUid(uid);
        System.out.println(profileName);
        if (!profileName.equals("Platform Manager")){
            return "redirect:/Login";
        }

        if (isSuccessful) {
            ServiceCategory category = serviceCategoryC.viewServiceCategory(serviceCategory.getName());
            return "redirect:/ServiceCategory?categoryId=" + category.getCategoryId();
        } else {
            model.addAttribute("error", "Service Category creation failed! Please try again.");
            return "pm_create_service_category";
        }
    }

    // View Service Category
    @GetMapping("/ServiceCategory")
    public String showServiceCategory(@RequestParam int categoryId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        ServiceCategory serviceCategory = serviceCategoryC.viewServiceCategory(categoryId); 
        model.addAttribute("serviceCategoryInfo", serviceCategory);
        return "pm_service_category_info";
    }

    @GetMapping("/ViewAllServiceCategories")
    public String showServiceCategoryList(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        List<ServiceCategory> serviceCategories = serviceCategoryC.getAllCategories();
        List<Integer> serviceListingsCount = new ArrayList<>();
        
        for(ServiceCategory category : serviceCategories) {  
            List<ServiceListing> serviceListings = serviceListingC.getServiceListingsByCategory(category.getCategoryId());
            int count = (serviceListings != null) ? serviceListings.size() : 0;
            serviceListingsCount.add(count);
        }
        
        model.addAttribute("serviceCategories", serviceCategories);
        model.addAttribute("serviceListingsCount", serviceListingsCount);
        return "pm_search_service_category";
    }

    // Update Service Category
    @GetMapping("/UpdateServiceCategory")
    public String updateServiceCategory(@RequestParam int categoryId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        ServiceCategory serviceCategory = serviceCategoryC.viewServiceCategory(categoryId); 
        model.addAttribute("updateServiceCategoryForm", serviceCategory);
        return "pm_update_service_category";
    }

    @PostMapping("/UpdateServiceCategory")
    public String processUpdateServiceCategory(@ModelAttribute ServiceCategory category, Model model) {
        boolean isSuccessful = serviceCategoryC.updateServiceCategory(category.getType(), category.getName(), category.getDescription(), category.getCategoryId());

        if (isSuccessful) {
            model.addAttribute("serviceCategoryInfo", category);
            return "redirect:/ServiceCategory?categoryId="  + category.getCategoryId();
        } else {
            model.addAttribute("error", "Service category update failed! Please try again.");
            return "redirect:/ServiceCategory?categoryId="  + category.getCategoryId();
        }
    }

    // Delete Service Category
    @PostMapping("/DeleteServiceCategory")
    public String processDeleteServiceCategory(@RequestParam int categoryId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        boolean isSuccessful = serviceCategoryC.deleteServiceCategory(categoryId);

        if (isSuccessful) {
            model.addAttribute("message", "Service category deleted successfully");
        } else {
            model.addAttribute("error", "Failed to delete service category");
        }

        return "redirect:/ViewAllServiceCategories";
    }

    //Search Service Category
    @GetMapping("/searchServiceCategory")
    public String searchServiceCategory(@RequestParam String query, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        List<ServiceCategory> serviceCategories = serviceCategoryC.searchServiceCategory(query);
        List<Integer> serviceListingsCount = new ArrayList<>();
        for(ServiceCategory category : serviceCategories) {  
            List<ServiceListing> serviceListings = serviceListingC.getServiceListingsByCategory(category.getCategoryId());
            int count = (serviceListings != null) ? serviceListings.size() : 0;
            serviceListingsCount.add(count);
        }
        model.addAttribute("serviceCategories", serviceCategories);
        model.addAttribute("serviceListingsCount", serviceListingsCount);
        return "pm_search_service_category";
    }

    //Generate Report
    @GetMapping("/GenerateReport")
    public String showReportPage(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        Report dailyReport = reportC.generateDailyReport();
        Report weeklyReport = reportC.generateWeeklyReport();
        Report monthlyReport = reportC.generateMonthlyReport();

        model.addAttribute("monthlyReport", monthlyReport);
        model.addAttribute("weeklyReport", weeklyReport);
        model.addAttribute("dailyReport", dailyReport);

        return "pm_generate_report";
    }

    //Generate Daily Report
    @GetMapping("/GenerateDaily")
    public String showReportDaily(@RequestParam LocalDate date, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        Report dailyReport = reportC.generateDailyReport();

        model.addAttribute("dailyReport", dailyReport);

        return "pm_generate_report";
    }

    //Generate Daily Report
    @GetMapping("/GenerateWeekly")
    public String showReportWeekly(@RequestParam LocalDate date, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        Report weeklyReport = reportC.generateWeeklyReport();

        model.addAttribute("weeklyReport", weeklyReport);

        if (weeklyReport != null){
            return "pm_generate_report";
        }
        else{
            return "";
        }
        
    }

    //Generate Daily Report
    @GetMapping("/GenerateMonthly")
    public String showReportMonthly(@RequestParam LocalDate date, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }

        Report monthlyReport = reportC.generateMonthlyReport();

        model.addAttribute("monthlyReport", monthlyReport);

        return "pm_generate_report";
    }


    // HomeOwnerrrr
    @GetMapping("/HomeOwnerHome")
    public String showHomeOwnerHome(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
        } else {
            return "redirect:/Login";
        }
        // Cleaners
        List<UserAccount> cleaners = userAccountC.searchUser(4);
        List<Integer> servicesCountList = new ArrayList<>();
        for (UserAccount cleaner : cleaners) {
        List<ServiceListing> serviceListings = serviceListingC.getAllListingsById(cleaner.getUid());
            int servicesCount = serviceListings.size();
            servicesCountList.add(servicesCount);
        }
        List<ServiceListing> serviceListings = serviceListingC.getAllListings();

        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleaners", cleaners);
        model.addAttribute("username", session.getAttribute("username"));
        return "homeowner_home_page";
    }

    // View All Services by Home Owner
    @GetMapping("/ViewAllServices")
    public String viewServices(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
    
        } else {
            return "redirect:/Login";
        }   
        List<ServiceListing> serviceListings = serviceListingC.getAllListings();
        List<String> cleanersName = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            UserAccount user = userAccountC.viewUserAccount(listing.getCleanerId());
            String cleanerName = user.getName();
            cleanersName.add(cleanerName);

            ServiceCategory category = serviceCategoryC.viewServiceCategory(listing.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("cleanersName", cleanersName);
        model.addAttribute("categoriesName", categoriesName);
        return "homeowner_search_services";
    }
    
    // View Services and Cleaners
    @GetMapping("/ViewServiceListing")
    public String showServices(@RequestParam int serviceId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
    
        } else {
            return "redirect:/Login";
        }       
        // this function will auto +1 to view
        ServiceListing listing = serviceListingC.viewSLandUpdateViewsByServiceId(serviceId);
        boolean isInServiceShortlist = shortlistC.isInServiceShortlist(serviceId);

        UserAccount user = userAccountC.viewUserAccount(listing.getCleanerId());
        String cleanerName = user.getName();

        ServiceCategory category = serviceCategoryC.viewServiceCategory(listing.getCategoryId());
        String categoryType = category.getType();
        String categoryName = category.getName();
        String categoryTypeandName = categoryType + "-" + categoryName;

        model.addAttribute("isInServiceShortlist", isInServiceShortlist);
        model.addAttribute("cleanerName", cleanerName);
        model.addAttribute("categoryTypeandName", categoryTypeandName);
        model.addAttribute("serviceListingInfo", listing);
        return "homeowner_view_service_listing";
    }

    @GetMapping("CleanerProfile")
    public String showCleanerProfile(@RequestParam int cleanerId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
    
        } else {
            return "redirect:/Login";
        } 
        UserAccount cleaner = userAccountC.viewUserAccount(cleanerId);
        List<ServiceListing> serviceListings = serviceListingC.getAllListingsById(cleanerId);
        boolean isInCleanerShortlist = shortlistC.isInCleanerShortlist(cleanerId);
        List<String> categoriesName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            ServiceCategory category = serviceCategoryC.viewServiceCategory(listing.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }
        int servicesCount = serviceListings.size();

        model.addAttribute("isInCleanerShortlist", isInCleanerShortlist);
        model.addAttribute("servicesCount", servicesCount);
        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("categoriesName", categoriesName);
        model.addAttribute("cleanerInfo", cleaner);
        return "homeowner_view_cleaner_profile";
    }

    // Search Services by Home Owner
    @GetMapping("/searchServices")
    public String searchServices(@RequestParam String query, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
    
        } else {
            return "redirect:/Login";
        }         
        List<ServiceListing> serviceListings = serviceListingC.searchListingsByService(query);

        if (serviceListings == null || serviceListings.isEmpty()) {
            serviceListings = serviceListingC.searchListingsByCleaner(query);
        }
        
        List<String> cleanersName = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            UserAccount user = userAccountC.viewUserAccount(listing.getCleanerId());
            String cleanerName = user.getName();
            cleanersName.add(cleanerName);

            ServiceCategory category = serviceCategoryC.viewServiceCategory(listing.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("cleanersName", cleanersName);
        model.addAttribute("categoriesName", categoriesName);
        return "homeowner_search_services";
    }

    // Add to Service Shortlist
    @PostMapping("/AddToServiceShortlist")
    public String addToServiceShortlist(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        shortlistC.shortlistService(uid, serviceId);

        return "redirect:/ServiceShortlist";
    }

    // Remove from Service Shortlist
    @PostMapping("/RemoveFromServiceShortlist")
    public String removeFromServiceShortlist(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        }
        shortlistC.deleteShortlistedServices(uid, serviceId);

        return "redirect:/ServiceShortlist";
    } 

    // Service Shortlist
    @GetMapping("/ServiceShortlist")
    public String showServiceShortlist(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<ServiceShortlist> shortlists = shortlistC.getAllShortlistedServices(uid);
        List<ServiceListing> serviceShortList = new ArrayList<>();
        List<String> cleanersName = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();

        for (ServiceShortlist shortlist : shortlists) {
            ServiceListing service = serviceListingC.viewServiceListingByServiceId(shortlist.getServiceId());
            serviceShortList.add(service);

            UserAccount user = userAccountC.viewUserAccount(service.getCleanerId());
            String cleanerName = user.getName();
            cleanersName.add(cleanerName);

            ServiceCategory category = serviceCategoryC.viewServiceCategory(service.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        model.addAttribute("cleanersName", cleanersName);
        model.addAttribute("categoriesName", categoriesName);
        model.addAttribute("serviceShortList", serviceShortList);
        return "homeowner_service_shortlist";
    }

    // Search Service Shortlist
    @GetMapping("/searchShortlistedServices")
    public String searchShortlistedServices(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<ServiceShortlist> shortlists = shortlistC.searchShortlistedServices(uid, query);
        List<ServiceListing> serviceShortList = new ArrayList<>();
        List<String> cleanersName = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();

        for (ServiceShortlist shortlist : shortlists) {
            ServiceListing service = serviceListingC.viewServiceListingByServiceId(shortlist.getServiceId());
            serviceShortList.add(service);

            UserAccount user = userAccountC.viewUserAccount(service.getCleanerId());
            String cleanerName = user.getName();
            cleanersName.add(cleanerName);

            ServiceCategory category = serviceCategoryC.viewServiceCategory(service.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        model.addAttribute("cleanersName", cleanersName);
        model.addAttribute("categoriesName", categoriesName);
        model.addAttribute("serviceShortList", serviceShortList);
        return "homeowner_service_shortlist";
    }

    // Add to Cleaner Shortlist
    @PostMapping("/AddToCleanerShortlist")
    public String addToCleanerShortlist(@RequestParam int cleanerId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        shortlistC.shortlistCleaner(uid, cleanerId);

        return "redirect:/CleanerShortlist";
    }

    // Remove from Service Shortlist
    @PostMapping("/RemoveFromCleanerShortlist")
    public String removeFromCleanerShortlist(@RequestParam int cleanerId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        shortlistC.deleteShortlistedCleaners(uid, cleanerId);

        return "redirect:/CleanerShortlist";
    }

    // Cleaner Shortlist
    @GetMapping("/CleanerShortlist")
    public String showCleanerShortlist(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<CleanerShortlist> shortlists = shortlistC.getAllShortlistedCleaners(uid);
        List<UserAccount> cleanersShortlist = new ArrayList<>();
        List<Integer> servicesCountList = new ArrayList<>();
        

        for (CleanerShortlist shortlist : shortlists) {
            UserAccount cleaner = userAccountC.viewUserAccount(shortlist.getCleanerId());
            cleanersShortlist.add(cleaner);
            List<ServiceListing> serviceListings = serviceListingC.getAllListingsById(shortlist.getCleanerId());
            int servicesCount = serviceListings.size();
            servicesCountList.add(servicesCount);
        }

        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleanersShortlist", cleanersShortlist);
        return "homeowner_cleaner_shortlist";
    }

    // Search Cleaner Shortlist
    @GetMapping("/searchShortlistedCleaners")
    public String searchShortlistedCleaners(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<CleanerShortlist> shortlists = shortlistC.searchShortlistedCleaners(uid, query);
        List<UserAccount> cleanersShortlist = new ArrayList<>();
        List<Integer> servicesCountList = new ArrayList<>();
        

        for (CleanerShortlist shortlist : shortlists) {
            UserAccount cleaner = userAccountC.viewUserAccount(shortlist.getCleanerId());
            cleanersShortlist.add(cleaner);
            List<ServiceListing> serviceListings = serviceListingC.getAllListingsById(shortlist.getCleanerId());
            int servicesCount = serviceListings.size();
            servicesCountList.add(servicesCount);
        }

        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleanersShortlist", cleanersShortlist);
        return "homeowner_cleaner_shortlist";
    }

    // My Booking
    @PostMapping("/AddToMyBooking")
    public String showMyBooking(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        boolean isSuccessful = bookingC.addBooking(serviceId, uid, "confirmed");
        if (isSuccessful) {
            return "redirect:/MyBooking";
        } else {
            return "redirect:/ViewServiceListing?serviceId=" + serviceId;
        }
    }

    @GetMapping("/MyBooking")
    public String showMyBooking(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<Booking> bookings = bookingC.getAllBookingsByHomeOwner(uid);
        List<ServiceListing> serviceListings = new ArrayList<>();
        List<UserAccount> cleaners = new ArrayList<>();
        
        for (Booking booking : bookings) {
            ServiceListing service = serviceListingC.viewServiceListingByServiceId(booking.getServiceId());
            serviceListings.add(service);
            UserAccount cleaner = userAccountC.viewUserAccount(service.getCleanerId());
            cleaners.add(cleaner);
        }
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("cleaners", cleaners);
        return "homeowner_my_booking";
    }

    // Search My Booking
    @GetMapping("/searchMyBooking")
    public String searchMyBooking(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            uid = result.get();
        } else {
            return "redirect:/Login";
        } 
        List<Booking> bookings = bookingC.searchHomeOwnerBookings(uid, query);
        List<ServiceListing> serviceListings = new ArrayList<>();
        List<UserAccount> cleaners = new ArrayList<>();
        
        for (Booking booking : bookings) {
            ServiceListing service = serviceListingC.viewServiceListingByServiceId(booking.getServiceId());
            serviceListings.add(service);
            UserAccount cleaner = userAccountC.viewUserAccount(service.getCleanerId());
            cleaners.add(cleaner);
        }
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("cleaners", cleaners);
        return "homeowner_my_booking";
    }
}
//testing