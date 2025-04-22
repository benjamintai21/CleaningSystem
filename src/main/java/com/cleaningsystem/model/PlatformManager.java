package com.cleaningsystem.model;

import java.util.*;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

public class PlatformManager {
    private int uid;
    private String username;
    private UserAccountDAO userAccountDAO;
    private UserProfileDAO userProfileDAO;
    private static Scanner scanner = new Scanner(System.in);

    public PlatformManager(int uid, String username) {
        this.uid = uid;
        this.username = username;
        this.userAccountDAO = new UserAccountDAO();
        this.userProfileDAO = new UserProfileDAO();
    }

    public void showPlatformManagerMenu() {
        while (true) {
            System.out.println("\n===== Platform Manager Menu =====");
            System.out.println("1. View My Profile");
            System.out.println("2. Update My Profile");
            System.out.println("3. View All Jobs");
            System.out.println("4. Manage Cleaners");
            System.out.println("5. View Reports");
            System.out.println("6. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> updateProfile();
                case 3 -> viewAllJobs();
                case 4 -> manageCleaners();
                case 5 -> viewReports();
                case 6 -> {
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

    private void viewProfile() {
        UserAccount account = userAccountDAO.getUserByID(uid);
        if (account != null) {
            System.out.println(account);
        }
    }

    private void updateProfile() {
        UserAccount account = userAccountDAO.getUserByID(uid);
        if (account != null) {
            System.out.println("Leave blank to keep existing value.");
            System.out.print("New Email (" + account.getEmail() + "): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) account.setEmail(email);

            System.out.print("New Address (" + account.getAddress() + "): ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) account.setAddress(address);

            if (userAccountDAO.updateUserAccount(account)) {
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        }
    }

    private void viewAllJobs() {
        // TODO: Implement view all jobs functionality
        System.out.println("View all jobs functionality coming soon...");
    }

    private void manageCleaners() {
        // TODO: Implement cleaner management functionality
        System.out.println("Cleaner management functionality coming soon...");
    }

    private void viewReports() {
        // TODO: Implement reports viewing functionality
        System.out.println("Reports viewing functionality coming soon...");
    }

    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }
} 