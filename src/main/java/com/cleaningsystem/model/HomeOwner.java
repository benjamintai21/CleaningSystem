package com.cleaningsystem.model;

import java.util.*;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

public class HomeOwner {
    private int uid;
    private String username;
    private UserAccountDAO userAccountDAO;
    private UserProfileDAO userProfileDAO;
    private static Scanner scanner = new Scanner(System.in);

    public HomeOwner(int uid, String username) {
        this.uid = uid;
        this.username = username;
        this.userAccountDAO = new UserAccountDAO();
        this.userProfileDAO = new UserProfileDAO();
    }

    public void showHomeOwnerMenu() {
        while (true) {
            System.out.println("\n===== Home Owner Menu =====");
            System.out.println("1. View My Profile");
            System.out.println("2. Update My Profile");
            System.out.println("3. Post Cleaning Job");
            System.out.println("4. View My Posted Jobs");
            System.out.println("5. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> updateProfile();
                case 3 -> postCleaningJob();
                case 4 -> viewMyPostedJobs();
                case 5 -> {
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
        UserAccount account = userAccountDAO.getUserById(uid);
        if (account != null) {
            System.out.println(account);
        }
    }

    private void updateProfile() {
        UserAccount account = userAccountDAO.getUserById(uid);
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

    private void postCleaningJob() {
        // TODO: Implement job posting functionality
        System.out.println("Job posting functionality coming soon...");
    }

    private void viewMyPostedJobs() {
        // TODO: Implement my posted jobs functionality
        System.out.println("My posted jobs functionality coming soon...");
    }

    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }  
}
