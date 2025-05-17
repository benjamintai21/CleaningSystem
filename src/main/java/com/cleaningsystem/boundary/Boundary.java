package com.cleaningsystem.boundary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cleaningsystem.controller.Booking.AddtoBookingController;
import com.cleaningsystem.controller.Booking.SearchBookingHistoryController;
import com.cleaningsystem.controller.Booking.SearchCompletedBookingController;
import com.cleaningsystem.controller.Booking.ViewBookingHistoryController;
import com.cleaningsystem.controller.Booking.ViewCompletedBookingController;
import com.cleaningsystem.controller.Login.LoginController;
import com.cleaningsystem.controller.Report.GenerateDailyReportC;
import com.cleaningsystem.controller.Report.GenerateMonthlyReportC;
import com.cleaningsystem.controller.Report.GenerateWeeklyReportC;
import com.cleaningsystem.controller.ServiceCategory.CreateServiceCategoryController;
import com.cleaningsystem.controller.ServiceCategory.DeleteServiceCategoryController;
import com.cleaningsystem.controller.ServiceCategory.SearchServiceCategoryController;
import com.cleaningsystem.controller.ServiceCategory.SearchServiceCategoryNamesController;
import com.cleaningsystem.controller.ServiceCategory.UpdateServiceCategoryController;
import com.cleaningsystem.controller.ServiceCategory.ViewServiceCategoryController;
import com.cleaningsystem.controller.ServiceListing.CreateServiceListingController;
import com.cleaningsystem.controller.ServiceListing.DeleteServiceListingController;
import com.cleaningsystem.controller.ServiceListing.OthersServiceListingController;
import com.cleaningsystem.controller.ServiceListing.SearchServiceListingController;
import com.cleaningsystem.controller.ServiceListing.UpdateServiceListingController;
import com.cleaningsystem.controller.ServiceListing.ViewServiceListingController;
import com.cleaningsystem.controller.Shortlist.AddToShortlistController;
import com.cleaningsystem.controller.Shortlist.DeleteFromShortlistController;
import com.cleaningsystem.controller.Shortlist.InShortlistController;
import com.cleaningsystem.controller.Shortlist.SearchShortlistedCleanerController;
import com.cleaningsystem.controller.Shortlist.SearchShortlistedServiceController;
import com.cleaningsystem.controller.Shortlist.ViewShortlistedCleanerController;
import com.cleaningsystem.controller.Shortlist.ViewShortlistedServiceController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.CreateUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.SearchUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.SuspendUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.UpdateUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.ViewUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.CreateUserProfileController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.SearchUserProfileController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.SuspendUserProfileController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.UpdateUserProfileController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.ViewUserProfileController;
import com.cleaningsystem.entity.Booking;
import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.Report;
import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class Boundary {

    @Autowired
    private LoginController LoginC;

    // User Admin
    // User Account --------------------------------------------
    @Autowired
    private CreateUserAccountController createUserAccountC;

    @Autowired
    private ViewUserAccountController viewUserAccountC;

    @Autowired
    private UpdateUserAccountController updateUserAccountC;

    @Autowired
    private SuspendUserAccountController suspendUserAccountC;

    @Autowired
    private SearchUserAccountController searchUserAccountC;

    // User Profile --------------------------------------------
    @Autowired
    private CreateUserProfileController createUserProfileC;

    @Autowired
    private ViewUserProfileController viewUserProfileC;

    @Autowired
    private UpdateUserProfileController updateUserProfileC;

    @Autowired
    private SuspendUserProfileController suspendUserProfileC;

    @Autowired
    private SearchUserProfileController searchUserProfileC;

    // Service Listing ------------------------------------------
    @Autowired
    private CreateServiceListingController createServiceListingC;

    @Autowired
    private ViewServiceListingController viewServiceListingC;

    @Autowired
    private UpdateServiceListingController updateServiceListingC;

    @Autowired
    private DeleteServiceListingController deleteServiceListingC;

    @Autowired
    private SearchServiceListingController searchServiceListingC;

    @Autowired 
    private OthersServiceListingController othersServiceListingC;

    // Booking ----------------------------------------------------------
    @Autowired
    private AddtoBookingController addtoBookingC;

    @Autowired
    private ViewCompletedBookingController viewCompletedBookingC;

    @Autowired
    private SearchCompletedBookingController searchCompletedBookingC;

    @Autowired
    private ViewBookingHistoryController viewBookingHistoryC;

    @Autowired
    private SearchBookingHistoryController searchBookingHistoryC;

    // Shortlist ---------------------------------------------------------
    @Autowired
    private AddToShortlistController addToShortlistC;

    @Autowired
    private DeleteFromShortlistController deleteFromShortlistC;

    @Autowired
    private InShortlistController inShortlistC;  

    @Autowired
    private ViewShortlistedCleanerController viewShortlistedCleanerC;

    @Autowired
    private SearchShortlistedCleanerController searchShortlistedCleanerC;

    @Autowired
    private ViewShortlistedServiceController viewShortlistedServiceC;

    @Autowired
    private SearchShortlistedServiceController searchShortlistedServiceC;

    // Report --------------------------------------------------------------
    @Autowired
    private GenerateDailyReportC generateDailyReportC;

    @Autowired
    private GenerateWeeklyReportC generateWeeklyReportC;

    @Autowired
    private GenerateMonthlyReportC generateMonthlyReportC;

    // Service Category ------------------------------------------------------
    @Autowired
    private CreateServiceCategoryController createServiceCategoryC;

    @Autowired
    private ViewServiceCategoryController viewServiceCategoryC;

    @Autowired
    private UpdateServiceCategoryController updateServiceCategoryC;

    @Autowired
    private DeleteServiceCategoryController deleteServiceCategoryC;

    @Autowired
    private SearchServiceCategoryController searchServiceCategoryC;

    @Autowired
    private SearchServiceCategoryNamesController searchServiceCategoryNamesC;

    // CHECK ACCESSSSSSSSSSSSSSSSSSSS--------------------------------------------------
    public Optional<Integer> checkAccess(HttpSession session, String userProfileName) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            // Redirect to login or error page if user not logged in
            return Optional.empty();
        }
        int uid = (int) uidObj;
        UserAccount userAccount = viewUserAccountC.viewUserAccount(uid);
        UserProfile userProfile = viewUserProfileC.viewUserProfile(userAccount.getProfileId());
        String profileName = userProfile.getProfileName();
        
        if (!profileName.equals(userProfileName)){
            return Optional.empty();
        }

        return Optional.of(uid);
    }

    @GetMapping("/")
    public String showHomePage(Model model){
        // Cleaners
        List<UserAccount> cleaners = searchUserAccountC.searchUserAccount(4);
        List<Integer> servicesCountList = othersServiceListingC.getServicesCountList();
        List<ServiceListing> serviceListings = searchServiceListingC.searchServiceListing();

        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleaners", cleaners);
        return "home";
    }

    @GetMapping("/Login")
    public String showLoginForm(Model model) {
        List<UserProfile> userProfiles = searchUserProfileC.searchUserProfile();
        model.addAttribute("loginForm", new UserAccount());
        model.addAttribute("userProfiles", userProfiles);
        return "login";
    }

    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/Login";
    }

    @PostMapping("/RedirectToPage")
    public String processLogin(@ModelAttribute("loginForm") UserAccount user, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserAccount loggedInUser = LoginC.login(user.getUsername(), user.getPassword(), user.getProfileId());

            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("toastMessage", "Invalid username or password");
                return "redirect:/Login";
            }
            if (loggedInUser.isSuspended()) {
                redirectAttributes.addFlashAttribute("toastMessage", "Your account has been suspended");
                return "redirect:/Login";
            }
            UserProfile userProfile = viewUserProfileC.viewUserProfile(loggedInUser.getProfileId());
            String profileName = userProfile.getProfileName();

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
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        model.addAttribute("username", session.getAttribute("username"));
        return "useradmin_home_page";
    }

    @GetMapping("/CleanerHome")
    public String showCleanerHome(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        model.addAttribute("username", session.getAttribute("username"));
        return "cleaner_home_page";
    }

    //Create User Account
    @GetMapping("/CleanerUserCreation")
    public String showCleanerSignUpForm(HttpSession session, Model model) {

        model.addAttribute("CleanerUserCreationForm", new UserAccount()); // Make sure this matches the form data type
        return "cleaner_user_creation"; // Ensure this matches the signup.html template
    }

    @PostMapping("/CleanerUserCreation")
    public String processCleanerSignUp(@ModelAttribute UserAccount user, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");

        user.setProfileId(4);
        boolean isSuccessful = createUserAccountC.createAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId()); 

        if (isSuccessful) {
            if (result.isPresent()) { } else { return "redirect:/Login"; }
            UserAccount newUser = viewUserAccountC.viewUserAccount(user.getUsername());
            UserProfile userProfile = viewUserProfileC.viewUserProfile(user.getProfileId());
            String profileName = userProfile.getProfileName();
            
            model.addAttribute("userAccountInfo", newUser);
            model.addAttribute("profileName", profileName);
            return "user_account_info";
        } else {
            model.addAttribute("error", "Signup failed! Please try again.");
            return "cleaner_user_creation";
        }
    }

    //View User Account
    @GetMapping("/UserAccount")
    public String showUserAccount(@RequestParam int uid, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        UserAccount user = viewUserAccountC.viewUserAccount(uid);
        UserProfile userProfile = viewUserProfileC.viewUserProfile(user.getProfileId());
        String profileName = userProfile.getProfileName();

        model.addAttribute("userAccountInfo", user);
        model.addAttribute("profileName", profileName);
        return "user_account_info";
    }

    @GetMapping("/ViewAllUserAccounts")
    public String showUserAccountList(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<UserAccount> userAccounts = searchUserAccountC.searchUserAccount();
        List<String> profileNames = searchUserProfileC.searchUserProfileNamesForUserAccounts(userAccounts);

        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("profileNames", profileNames);
        return "user_account_list";
    }

    //Update User Account
    @GetMapping("/UpdateUserAccount")
    public String showUpdateUserAccount(@RequestParam int uid, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        UserAccount user = viewUserAccountC.viewUserAccount(uid);
        model.addAttribute("userAccountInfo", user);
        return "user_account_update";
    }

    @PostMapping("/UpdateUserAccount")
    public String processUpdateUserAccount(@ModelAttribute UserAccount user, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = updateUserAccountC.updateUserAccount(user.getName(), user.getAge(), user.getDob(), user.getGender(), user.getAddress(), user.getEmail(), user.getUsername(),user.getPassword(),user.getProfileId(), user.getUid()); 

        if (isSuccessful) {
            return "redirect:/UserAccount?uid=" + user.getUid();
        } else {
            model.addAttribute("error", "Profile update failed! Please try again.");
            return "user_account_update";
        }
    }

    // Suspend User Account
    @PostMapping("/SetAccountSuspensionStatus")
    public String processAccountSuspensionStatus(@RequestParam boolean suspended, @ModelAttribute UserAccount user, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = suspendUserAccountC.suspendUserAccount(user.getUid(), suspended); 
        if (isSuccessful) {
            UserAccount updatedUser = viewUserAccountC.viewUserAccount(user.getUid());
            UserProfile userProfile = viewUserProfileC.viewUserProfile(updatedUser.getProfileId());
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
    public String searchUserAccounts(@RequestParam String query, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<UserAccount> userAccounts = searchUserAccountC.searchUserAccount(query);
        List<String> profileNames = searchUserProfileC.searchUserProfileNamesForUserAccounts(userAccounts);

        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("profileNames", profileNames);
        return "user_account_list";
    }

    //Create User Profile
    @GetMapping("/CreateUserProfile")
    public String createUserProfile(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        model.addAttribute("userProfileForm", new UserProfile());
        return "user_profile_creation";
    }

    @PostMapping("/CreateUserProfile")
    public String processUserProfile(@ModelAttribute UserProfile userProfile, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = createUserProfileC.createUserProfile(userProfile.getProfileName(), userProfile.getDescription(), userProfile.isSuspended());

        if (isSuccessful) {
            UserProfile newProfile = viewUserProfileC.viewUserProfile();
            model.addAttribute("userProfileInfo", newProfile);
            return "user_profile_info";
        } else {
            model.addAttribute("error", "Profile creation failed! Please try again.");
            return "cleaner_user_creation";
        }
    }

    //View User Profile
    @GetMapping("/UserProfile")
    public String showUserProfile(@RequestParam int profileId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        UserProfile userProfile = viewUserProfileC.viewUserProfile(profileId);
        List<UserAccount> userAccounts = searchUserAccountC.searchUserAccount(userProfile.getProfileId());
        int usersCount = userAccounts.size();
    
        model.addAttribute("userProfileInfo", userProfile);
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("usersCount", usersCount);
        return "user_profile_info";
    }

    @GetMapping("/ViewAllUserProfiles")
    public String showUserProfileList(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<UserProfile> userProfiles = searchUserProfileC.searchUserProfile();
        List<Integer> usersPerProfileCount = searchUserAccountC.searchUserAccountPerProfileCount(userProfiles);

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", usersPerProfileCount);
        return "user_profile_list";
    }
    
    //Update User Profile
    @GetMapping("/UpdateUserProfile")
    public String updateUserProfile(@RequestParam int profileId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        UserProfile userProfile = viewUserProfileC.viewUserProfile(profileId);
        model.addAttribute("userProfileInfo", userProfile);
        return "user_profile_update";
    }

    @PostMapping("/UpdateUserProfile")
    public String processUpdateUserProfile(@ModelAttribute UserProfile userProfile, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = updateUserProfileC.updateUserProfile(userProfile.getProfileName(), userProfile.getDescription(), userProfile.isSuspended(), userProfile.getProfileId());

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
    public String processProfileSuspensionStatus(@RequestParam boolean suspended, @ModelAttribute UserProfile profile, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = suspendUserProfileC.suspendUserProfile(profile.getProfileId(), suspended); 
    
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
    public String searchUserProfile(@RequestParam String query, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "User Admin");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<UserProfile> userProfiles = searchUserProfileC.searchUserProfile(query);
        List<Integer> usersPerProfileCount = searchUserAccountC.searchUserAccountPerProfileCount(userProfiles);

        model.addAttribute("userProfiles", userProfiles);
        model.addAttribute("profilesUserCounter", usersPerProfileCount);
        return "user_profile_list";
    }

    //Cleaner
    @GetMapping("/CleanerCreateService")
    public String showServiceListing(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<ServiceCategory> categories = searchServiceCategoryC.searchServiceCategory();

        model.addAttribute("serviceListing", new ServiceListing());
        model.addAttribute("serviceCategories", categories);
        model.addAttribute("serviceStatuses", ServiceListing.Status.values());
        return "cleaner_create_service_listing";
    }

    @PostMapping("/CleanerCreateService")
    public String processServiceListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }
        
        boolean isSuccessful = createServiceListingC.createServiceListing(serviceListing.getName(), uid , serviceListing.getCategoryId(),
                                                                        serviceListing.getDescription(), serviceListing.getPricePerHour(),
                                                                        serviceListing.getStartDate(), serviceListing.getEndDate(), serviceListing.getStatus());
        if (isSuccessful) {
            ServiceListing latest = viewServiceListingC.viewServiceListing();
            model.addAttribute("serviceListingInfo", latest);
            model.addAttribute("serviceCategory", viewServiceCategoryC.viewServiceCategory(serviceListing.getCategoryId()));
            return "cleaner_view_service_listing";
        } else {
            model.addAttribute("error", "Service Listing creation failed! Please try again.");
            return "cleaner_create_service_listing";
        }
    }

    //View User Account
    @GetMapping("/ServiceListing")
    public String showServiceListing(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        ServiceListing listing = viewServiceListingC.viewServiceListing(serviceId, uid);
        ServiceCategory category = viewServiceCategoryC.viewServiceCategory(listing.getCategoryId());
        model.addAttribute("serviceListingInfo", listing);
        model.addAttribute("serviceCategory", category);
        return "cleaner_view_service_listing";
    }

    @GetMapping("/ViewAllServiceListings")
    public String showServiceListingList(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        String query = "";
        List<ServiceListing> servicelistings = searchServiceListingC.searchServiceListing(uid, query);
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

        ServiceListing listing = viewServiceListingC.viewServiceListing(serviceId, uid);
        model.addAttribute("serviceListingInfo", listing);
        model.addAttribute("serviceCategories", searchServiceCategoryC.searchServiceCategory());
        model.addAttribute("serviceStatuses", ServiceListing.Status.values()); 
        return "cleaner_update_service_listing";
    }

    @PostMapping("/CleanerUpdateService")
    public String processUpdateListing(@ModelAttribute ServiceListing serviceListing, Model model, HttpSession session) {
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { } else { return "redirect:/Login"; }
        boolean isSuccessful = updateServiceListingC.updateServiceListing(serviceListing.getName(), serviceListing.getCleanerId(),
                                                                    serviceListing.getCategoryId(), serviceListing.getDescription(),
                                                                    serviceListing.getPricePerHour(), serviceListing.getStartDate(),
                                                                    serviceListing.getEndDate(), serviceListing.getStatus(),
                                                                    serviceListing.getServiceId());
        if (isSuccessful) {
            ServiceCategory category = viewServiceCategoryC.viewServiceCategory(serviceListing.getCategoryId());
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
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { } else { return "redirect:/Login"; }
        boolean isSuccessful = deleteServiceListingC.deleteServiceListing(listing.getServiceId());

        if (isSuccessful) {
            
            model.addAttribute("message", "Delete listing succesfully");
        } else {
            model.addAttribute("error", "Delete listing failed! Please try again.");
        }
        return "redirect:/ViewAllServiceListings";
    }

    //Search ServiceListing
    @GetMapping("/searchServiceListing")
    public String searchServiceListing(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<ServiceListing> servicelistings = searchServiceListingC.searchServiceListing(uid, query);
        model.addAttribute("serviceListings", servicelistings);
        return "cleaner_service_list";
    }

    @GetMapping("CleanerViewRecords")
    public String showPastRecords(HttpSession session, Model model){
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<Booking> matches = viewCompletedBookingC.viewCompletedBooking(uid);
        List<ServiceListing> serviceListings = othersServiceListingC.getServiceListingsByBookings(uid);

        model.addAttribute("matches", matches);
        model.addAttribute("serviceListings", serviceListings);
        return "cleaner_record_list";
    }
    
    //Search ServiceListing
    @GetMapping("/searchCleanerRecord")
    public String searchPastRecords(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Cleaner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<Booking> matches = searchCompletedBookingC.searchCompletedBooking(uid, query);
        List<ServiceListing> serviceListings = othersServiceListingC.getServiceListingsByBookings(uid);
        
        model.addAttribute("matches", matches);
        model.addAttribute("serviceListings", serviceListings);
        return "cleaner_record_list";
    }

    //--Platform Managerrrr
    @GetMapping("/PlatformManagerHome")
    public String showPlatformManagerHome(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
            Integer id = result.get();
         } else { return "redirect:/Login"; }

        model.addAttribute("username", session.getAttribute("username"));
        return "platformmanager_home_page";
    }

    // Create Service Catetory
    @GetMapping("/ServiceCategoryCreation")
    public String showServiceCategoryCreation(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        model.addAttribute("serviceCategory", new ServiceCategory());
        return "pm_create_service_category";
    }

    @PostMapping("/CreateServiceCategory")
    public String processServiceCategoryCreation(@ModelAttribute ServiceCategory serviceCategory, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = createServiceCategoryC.createServiceCategory(serviceCategory.getType(), serviceCategory.getName(), serviceCategory.getDescription());
        if (isSuccessful) {
            ServiceCategory category = viewServiceCategoryC.viewServiceCategory(serviceCategory.getName());
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
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        ServiceCategory serviceCategory = viewServiceCategoryC.viewServiceCategory(categoryId); 

        model.addAttribute("serviceCategoryInfo", serviceCategory);
        return "pm_service_category_info";
    }

    @GetMapping("/ViewAllServiceCategories")
    public String showServiceCategoryList(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<ServiceCategory> serviceCategories = searchServiceCategoryC.searchServiceCategory();
        List<Integer> serviceListingsCount = othersServiceListingC.getServiceListingsCount(serviceCategories);
        
        model.addAttribute("serviceCategories", serviceCategories);
        model.addAttribute("serviceListingsCount", serviceListingsCount);
        return "pm_search_service_category";
    }

    // Update Service Category
    @GetMapping("/UpdateServiceCategory")
    public String updateServiceCategory(@RequestParam int categoryId, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        ServiceCategory serviceCategory = viewServiceCategoryC.viewServiceCategory(categoryId);

        model.addAttribute("updateServiceCategoryForm", serviceCategory);
        return "pm_update_service_category";
    }

    @PostMapping("/UpdateServiceCategory")
    public String processUpdateServiceCategory(@ModelAttribute ServiceCategory category, HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = updateServiceCategoryC.updateServiceCategory(category.getType(), category.getName(), category.getDescription(), category.getCategoryId());

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
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        boolean isSuccessful = deleteServiceCategoryC.deleteServiceCategory(categoryId);

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
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<ServiceCategory> serviceCategories = searchServiceCategoryC.searchServiceCategory(query);
        List<Integer> serviceListingsCount = othersServiceListingC.getServiceListingsCount(serviceCategories);

        model.addAttribute("serviceCategories", serviceCategories);
        model.addAttribute("serviceListingsCount", serviceListingsCount);
        return "pm_search_service_category";
    }

    @GetMapping("/GenerateReport")
    public String showGeneratedReport(@RequestParam(required = false) String type,
                                    @RequestParam(required = false) String date,
                                    HttpSession session,
                                    Model model) {
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isEmpty()) return "redirect:/Login";

        if (type != null && date != null) {
            model.addAttribute("type", type);
            model.addAttribute("date", date); // <--- Add this line

            switch (type) {
                case "daily" -> model.addAttribute("dailyReport", generateDailyReportC.generateDailyReport(date));
                case "weekly" -> model.addAttribute("weeklyReport", generateWeeklyReportC.generateWeeklyReport(date));
                case "monthly" -> model.addAttribute("monthlyReport", generateMonthlyReportC.generateMonthlyReport(date));
            }
        }

        return "pm_generate_report"; // always return the same view
    }

    @GetMapping("/DownloadDailyReport")
    public void downloadDailyReport(@RequestParam String date, HttpServletResponse response) throws Exception {
        Report report = generateDailyReportC.generateDailyReport(date);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=daily_report_" + date + ".pdf");
    
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
    
        document.open();
        document.add(new Paragraph("Daily Report for " + date));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("New Home Owners: " + report.getNewHomeOwners()));
        document.add(new Paragraph("New Cleaners: " + report.getNewCleaners()));
        document.add(new Paragraph("Shortlists: " + report.getNoOfShortlists()));
        document.add(new Paragraph("Total Home Owners: " + report.getTotalHomeOwners()));
        document.add(new Paragraph("Total Cleaners: " + report.getTotalCleaners()));
        document.add(new Paragraph("Bookings: " + report.getNoOfBookings()));
        document.add(new Paragraph("Generated on: " + LocalDate.now()));
        document.close();
    }
    
    @GetMapping("/DownloadWeeklyReport")
    public void downloadWeeklyReport(@RequestParam String date, HttpServletResponse response) throws Exception {
        Report report = generateWeeklyReportC.generateWeeklyReport(date);
    
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=weekly_report_" + date + ".pdf");
    
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
    
        document.open();
        document.add(new Paragraph("Weekly Report for " + date));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("New Home Owners: " + report.getNewHomeOwners()));
        document.add(new Paragraph("New Cleaners: " + report.getNewCleaners()));
        document.add(new Paragraph("Shortlists: " + report.getNoOfShortlists()));
        document.add(new Paragraph("Total Home Owners: " + report.getTotalHomeOwners()));
        document.add(new Paragraph("Total Cleaners: " + report.getTotalCleaners()));
        document.add(new Paragraph("Bookings: " + report.getNoOfBookings()));
        document.add(new Paragraph("Generated on: " + LocalDate.now()));
        document.close();
    }

    @GetMapping("/DownloadMonthlyReport")
    public void downloadMonthlyReport(@RequestParam String date, HttpServletResponse response) throws Exception {
        Report report = generateMonthlyReportC.generateMonthlyReport(date);
    
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=monthly_report_" + date + ".pdf");
    
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
    
        document.open();
        document.add(new Paragraph("Monthly Report for " + date));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("New Home Owners: " + report.getNewHomeOwners()));
        document.add(new Paragraph("New Cleaners: " + report.getNewCleaners()));
        document.add(new Paragraph("Shortlists: " + report.getNoOfShortlists()));
        document.add(new Paragraph("Total Home Owners: " + report.getTotalHomeOwners()));
        document.add(new Paragraph("Total Cleaners: " + report.getTotalCleaners()));
        document.add(new Paragraph("Bookings: " + report.getNoOfBookings()));
        document.add(new Paragraph("Generated on: " + LocalDate.now()));
        document.close();
    }

    // Home Owner
    @GetMapping("/HomeOwnerHome")
    public String showHomeOwnerHome(HttpSession session, Model model) {
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) {
            Integer id = result.get();
         } else { return "redirect:/Login"; }

        List<UserAccount> cleaners = searchUserAccountC.searchUserAccount(4);
        List<Integer> servicesCountList = othersServiceListingC.getServicesCountList();
        List<ServiceListing> serviceListings = searchServiceListingC.searchServiceListing();

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
        if (result.isPresent()) { } else { return "redirect:/Login"; }
         
        List<ServiceListing> serviceListings = searchServiceListingC.searchServiceListing();
        List<String> cleanersName = searchUserAccountC.searchUserAccountNamesByServiceListings(serviceListings);
        List<String> categoriesName = searchServiceCategoryNamesC.searchServiceCategoryNamesByServiceListings(serviceListings);

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
            
         } else { return "redirect:/Login"; }
         Object uidObj = session.getAttribute("uid");

        int uid = (int) uidObj;
        // this function will auto +1 to view
        ServiceListing listing = viewServiceListingC.viewServiceListingAsHomeOwner(serviceId);
        boolean isInServiceShortlist = inShortlistC.isInServiceShortlist(serviceId, uid);

        UserAccount user = viewUserAccountC.viewUserAccount(listing.getCleanerId());
        String cleanerName = user.getName();

        ServiceCategory category = viewServiceCategoryC.viewServiceCategory(listing.getCategoryId());
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
        Optional<Integer> result = checkAccess(session, "Platform Manager");
        if (result.isPresent()) {
            Integer id = result.get();
         } else { return "redirect:/Login"; }

        UserAccount cleaner = viewUserAccountC.viewUserAccount(cleanerId);
        Object uidObj = session.getAttribute("uid");

        int uid = (int) uidObj;
        String query = "";
        List<ServiceListing> serviceListings = searchServiceListingC.searchServiceListing(cleanerId, query);
        boolean isInCleanerShortlist = inShortlistC.isInCleanerShortlist(cleanerId,uid);
        
        List<String> categoriesName = searchServiceCategoryNamesC.searchAllServiceCategoryNames(serviceListings);
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
        if (result.isPresent()) { } else { return "redirect:/Login"; }

        List<ServiceListing> serviceListings = searchServiceListingC.searchServiceListing(query );
        
        List<String> cleanersName = searchUserAccountC.searchUserAccountNamesByServiceListings(serviceListings);
        List<String> categoriesName = searchServiceCategoryNamesC.searchServiceCategoryNamesByServiceListings(serviceListings);

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
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        addToShortlistC.shortlistService(uid, serviceId);

        return "redirect:/ServiceShortlist";
    }

    // Remove from Service Shortlist
    @PostMapping("/RemoveFromServiceShortlist")
    public String removeFromServiceShortlist(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        deleteFromShortlistC.deleteShortlistedServices(uid, serviceId);

        return "redirect:/ServiceShortlist";
    } 

    // Service Shortlist
    @GetMapping("/ServiceShortlist")
    public String showServiceShortlist(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<ServiceShortlist> shortlists = viewShortlistedServiceC.viewShortlistedService(uid);
        List<ServiceListing> serviceShortList = othersServiceListingC.getAllServiceListingsFromShortlist(shortlists);
        List<String> cleanersName = searchUserAccountC.searchUserAccountNamesByServiceListings(serviceShortList);
        List<String> categoriesName = searchServiceCategoryNamesC.searchServiceCategoryNamesByServiceListings(serviceShortList);

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
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<ServiceShortlist> shortlists = searchShortlistedServiceC.searchShortlistedService(uid, query);
        List<ServiceListing> serviceShortList = othersServiceListingC.getAllServiceListingsFromShortlist(shortlists);
        List<String> cleanersName = searchUserAccountC.searchUserAccountNamesByServiceListings(serviceShortList);
        List<String> categoriesName = searchServiceCategoryNamesC.searchServiceCategoryNamesByServiceListings(serviceShortList);

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
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        addToShortlistC.shortlistCleaner(uid, cleanerId);

        return "redirect:/CleanerShortlist";
    }

    // Remove from Service Shortlist
    @PostMapping("/RemoveFromCleanerShortlist")
    public String removeFromCleanerShortlist(@RequestParam int cleanerId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        deleteFromShortlistC.deleteShortlistedCleaners(uid, cleanerId);

        return "redirect:/CleanerShortlist";
    }

    // Cleaner Shortlist
    @GetMapping("/CleanerShortlist")
    public String showCleanerShortlist(HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<CleanerShortlist> shortlists = viewShortlistedCleanerC.viewShortlistedCleaner(uid);
        List<UserAccount> cleanersShortlist = searchUserAccountC.searchUserAccountFromShortlist(shortlists);
        List<Integer> servicesCountList = othersServiceListingC.getServiceListingsCountFromShortlist(shortlists);
        
        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleanersShortlist", cleanersShortlist);
        return "homeowner_cleaner_shortlist";
    }

    // Search Cleaner Shortlist
    @GetMapping("/searchShortlistedCleaners")
    public String searchShortlistedCleaners(@RequestParam String query, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<CleanerShortlist> shortlists = searchShortlistedCleanerC.searchShortlistedCleaner(uid, query);
        List<UserAccount> cleanersShortlist = searchUserAccountC.searchUserAccountFromShortlist(shortlists);
        List<Integer> servicesCountList = othersServiceListingC.getServiceListingsCountFromShortlist(shortlists);
        
        model.addAttribute("servicesCountList", servicesCountList);
        model.addAttribute("cleanersShortlist", cleanersShortlist);
        return "homeowner_cleaner_shortlist";
    }

    // My Booking
    @PostMapping("/AddToMyBooking")
    public String showMyBooking(@RequestParam int serviceId, HttpSession session, Model model) {
        int uid;
        Optional<Integer> result = checkAccess(session, "Home Owner");
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        boolean isSuccessful = addtoBookingC.addBooking(serviceId, uid, "confirmed");
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
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<Booking> bookings = viewBookingHistoryC.viewBookingHistory(uid);
        List<ServiceListing> serviceListings = othersServiceListingC.getServiceListingsFromBookings(bookings);
        List<UserAccount> cleaners = othersServiceListingC.getCleanerAccountsFromBookings(bookings);
        
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
        if (result.isPresent()) { uid = result.get(); } else { return "redirect:/Login"; }

        List<Booking> bookings = searchBookingHistoryC.searchBookingHistory(uid, query);
        List<ServiceListing> serviceListings = othersServiceListingC.getServiceListingsFromBookings(bookings);
        List<UserAccount> cleaners = othersServiceListingC.getCleanerAccountsFromBookings(bookings);
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("serviceListings", serviceListings);
        model.addAttribute("cleaners", cleaners);
        return "homeowner_my_booking";
    }
}
//testing