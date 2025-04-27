package com.cleaningsystem.model;

import java.util.*;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.dao.ServiceListingDAO;
public class HomeOwner {
    private int uid;
    private String username;
    private UserAccountDAO userAccountDAO;
    private UserProfileDAO userProfileDAO;
    private static Scanner scanner = new Scanner(System.in);
    private ServiceListingDAO serviceListingDAO;

    public HomeOwner(int uid, String username) {
        this.uid = uid;
        this.username = username;
        this.userAccountDAO = new UserAccountDAO();
        this.userProfileDAO = new UserProfileDAO();
    }

    public void showHomeOwnerMenu() {
        while (true) {
            System.out.println("\n===== Home Owner Menu =====");
            System.out.println("1. Search a Service Listing");
            System.out.println("2. View a Service Listing");
            System.out.println("3. Save a Service Listing");
            System.out.println("4. My Shortlists");
            System.out.println("5. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> searchServiceListing();
                case 2 -> viewServiceListing();
                case 3 -> saveServiceListing();
                case 4 -> myShortlists();
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

    private void searchServiceListing() {}

    private void viewServiceListing() {}

    private void saveServiceListing() {}    

    private void myShortlists() {}

    
    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }  
}
