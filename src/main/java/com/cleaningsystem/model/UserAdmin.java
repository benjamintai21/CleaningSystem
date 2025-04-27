package com.cleaningsystem.model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.*;

@Component
public class UserAdmin {
    private int uid;
    private String username;
    
    @Autowired
    private UserAccountDAO userAccountDAO;
    
    @Autowired
    private UserProfileDAO userProfileDAO;
    
    private static Scanner scanner = new Scanner(System.in);

    // No-args constructor for Spring
    public UserAdmin() {}

    // Constructor
    public UserAdmin(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n===== User Admin Menu =====");
            System.out.println("1. Manage User Accounts");
            System.out.println("2. Manage User Profiles");
            System.out.println("3. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> showAccountMenu();
                case 2 -> showProfileMenu();
                case 3 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void showAccountMenu() {
        while (true) {
            System.out.println("\n===== MANAGE USER ACCOUNT =====");
            System.out.println("1. Create User Account");
            System.out.println("2. View User Account");
            System.out.println("3. Update User Account");
            System.out.println("4. Delete User Account");
            System.out.println("5. Search User Account by Name");
            System.out.println("6. View All User Accounts");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUserAccount();
                case 2 -> viewUserAccount();
                case 3 -> updateUserAccount();
                case 4 -> deleteUserAccount();
                case 5 -> searchUserAccounts();
                case 6 -> viewAllUserAccounts();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void showProfileMenu() {
        while (true) {
            System.out.println("\n===== MANAGE USER PROFILES =====");
            System.out.println("1. Create User Profile");
            System.out.println("2. View User Profile");
            System.out.println("3. Update User Profile");
            System.out.println("4. Suspend/Unsuspend User Profile");
            System.out.println("5. Search User Profile by Name");
            System.out.println("6. View All User Profiles");
            System.out.println("0. Back to Admin Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUserProfile();
                case 2 -> viewUserProfile();
                case 3 -> updateUserProfile();
                case 4 -> suspendUserProfile();
                case 5 -> searchUserProfile();
                case 6 -> viewAllUserProfiles();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createUserAccount() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String n_username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Profile Name (e.g. Cleaner, Home Owner): ");
        String profileName = scanner.nextLine();

        int profileId = userProfileDAO.getProfileIdByName(profileName);
        if (profileId == -1) {
            System.out.println("Profile name not found. Moved to unassigned profile");
        }

        UserAccount account = new UserAccount(name, age, dob, gender, address, email,
                n_username, password, profileId);

        if (userAccountDAO.insertUserAccount(account)) {
            System.out.println("User account created successfully.");
        } else {
            System.out.println("Failed to create user account.");
        }
    }

    private void viewUserAccount() {
        List<UserAccount> accounts = userAccountDAO.getAllUsers();

        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            System.out.println("Available Accounts:");
            for (UserAccount acc : accounts) {
                System.out.println(acc.getUid() + ". " + acc.getUsername());
            }
            System.out.print("Enter Account Id to view details: ");
            int selectedId = scanner.nextInt();
            scanner.nextLine();
            UserAccount selectedAccount = userAccountDAO.getUserById(selectedId);
            if (selectedAccount != null) {
                System.out.println(selectedAccount);
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    private void updateUserAccount() {
        System.out.print("Enter user Id to update: ");
        int n_uid = scanner.nextInt();
        scanner.nextLine();
        UserAccount acc = userAccountDAO.getUserById(n_uid);
        if (acc == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.println("Leave blank to keep existing value.");
        System.out.print("New Name (" + acc.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) acc.setName(name);

        System.out.print("New Email (" + acc.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) acc.setEmail(email);

        System.out.print("New Address (" + acc.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) acc.setAddress(address);

        if (userAccountDAO.updateUserAccount(acc)) {
            System.out.println("User account updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void deleteUserAccount() {
        System.out.print("Enter user Id to delete: ");
        int n_uid = scanner.nextInt();
        scanner.nextLine();
        if (userAccountDAO.deleteUserAccount(n_uid)) {
            System.out.println("User account deleted.");
        } else {
            System.out.println("Delete failed.");
        }
    }

    private void searchUserAccounts() {
        System.out.print("Enter keyword to search usernames: ");
        String keyword = scanner.nextLine();
        List<UserAccount> results = userAccountDAO.searchUsersByUsername(keyword);
        if (results.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (UserAccount acc : results) {
                System.out.println(acc.getUid() + ". " + acc.getUsername());
            }
        }
    }

    private void viewAllUserAccounts() {
        System.out.println("\n--- All User Accounts ---");
        List<UserAccount> accounts = userAccountDAO.getAllUsers();
        for (UserAccount acc : accounts) {
            System.out.println(acc.getUid() + ". " + acc.getUsername());
        }
    }

    private void createUserProfile() {
        System.out.print("Profile Name: ");
        String name = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        UserProfile profile = new UserProfile(name, description, false);

        if (userProfileDAO.insertUserProfile(profile)) {
            System.out.println("User profile created successfully.");
        } else {
            System.out.println("Failed to create user profile.");
        }
    }

    private void viewUserProfile() {
        List<UserProfile> profiles = userProfileDAO.getAllProfiles();

        if (profiles.isEmpty()) {
            System.out.println("No profiles available.");
        } else {
            System.out.println("Available Profiles:");
            for (UserProfile pro : profiles) {
                System.out.println(pro.getProfileId() + ". " + pro.getProfileName());
            }
            System.out.print("Enter Profile Id to view details: ");
            int selectedId = scanner.nextInt();
            scanner.nextLine();
            UserProfile selectedProfile = userProfileDAO.getProfileById(selectedId);
            if (selectedProfile != null) {
                System.out.println(selectedProfile);
            } else {
                System.out.println("Profile not found.");
            }
        }
    }

    private void updateUserProfile() {
        System.out.print("Enter Profile Id to update: ");
        int n_uid = scanner.nextInt();
        scanner.nextLine();
        UserProfile pro = userProfileDAO.getProfileById(n_uid);
        if (pro == null) {
            System.out.println("Profile not found.");
            return;
        }
        System.out.println("Leave blank to keep existing value.");
        System.out.print("New Name (" + pro.getProfileName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) pro.setProfileName(name);

        System.out.print("New Description (" + pro.getDescription() + "): ");
        String description = scanner.nextLine();
        if (!description.isEmpty()) pro.setDescription(description);

        if (userProfileDAO.updateUserProfile(pro)) {
            System.out.println("User profile updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void suspendUserProfile() {
        System.out.print("Enter Profile Id to suspend/unsuspend: ");
        int n_uid = scanner.nextInt();
        scanner.nextLine();
        UserProfile pro = userProfileDAO.getProfileById(n_uid);
        if (pro == null) {
            System.out.println("Profile not found.");
            return;
        }
        if (pro.isSuspended()) {
            System.out.print(pro.getProfileName() + " Profile currently suspended. Unsuspend Profile? ('yes' to confirm): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                if(userProfileDAO.setSuspension(pro.getProfileId(), false)) {
                    System.out.println("User Profile unsuspended.");
                } else {
                    System.out.println("Unsuspension failed.");
                }
            }
        } else {
            System.out.print("Suspend (" + pro.getProfileName() + ") Profile? ('yes' to confirm): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("yes")) {
                if (userProfileDAO.setSuspension(pro.getProfileId(), true)) {
                    System.out.println("User Profile suspended.");
                } else {
                    System.out.println("Suspension failed.");
                }
            }
        }
    }

    private void searchUserProfile() {
        System.out.print("Enter keyword to search User Profile: ");
        String keyword = scanner.nextLine();
        List<UserProfile> results = userProfileDAO.searchProfilesByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No profiles found.");
        } else {
            for (UserProfile pro : results) {
                System.out.println(pro.getProfileId() + ". " + pro.getProfileName());
            }
        }
    }

    private void viewAllUserProfiles() {
        System.out.println("\n--- All User Profiles ---");
        List<UserProfile> profiles = userProfileDAO.getAllProfiles();
        for (UserProfile pro : profiles) {
            System.out.println(pro.getProfileId() + ". " + pro.getProfileName());
        }
    }

    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }
}

