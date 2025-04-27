package com.cleaningsystem.model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.dao.ServiceListingDAO;
import com.cleaningsystem.dao.BookingHistoryDao;

@Component
public class HomeOwner {
    private int uid;
    private String username;
    
    @Autowired
	private UserAccountDAO userAccountDAO;
	
	@Autowired
	private UserProfileDAO userProfileDAO;
	
	@Autowired
	private ServiceListingDAO serviceListingDAO;
	
	@Autowired
	private BookingHistoryDao bookingHistoryDAO;
	
	private static Scanner scanner = new Scanner(System.in);

    // No-args constructor for Spring
    public HomeOwner() {}

    public HomeOwner(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public void showHomeOwnerMenu() {
        while (true) {
            System.out.println("\n===== Home Owner Menu =====");
            System.out.println("1. Service Listing Menu");
            System.out.println("2. My Shortlists");
            System.out.println("3. My Past Bookings");
            System.out.println("4. Logout");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> showServiceListingMenu();
                case 2 -> showShortListMenu();
                case 3 -> showPastBookingsMenu();
                case 4 -> {
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

    public void showServiceListingMenu() {
        while (true) {
            System.out.println("\n===== Service Listing Menu =====");
            System.out.println("1. Search Service Listing");
            System.out.println("2. View Service Listing");
            System.out.println("3. Save Service Listing");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> searchServiceListing();
                case 2 -> viewServiceListing();
                case 3 -> saveServiceListing();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void showShortListMenu() {
        while (true) {
            System.out.println("\n===== Shortlist Menu =====");
            System.out.println("1. Search Shortlist");
            System.out.println("2. View Shortlisted Services");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> searchShortList();
                case 2 -> viewShortList();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public void showPastBookingsMenu() {
        while (true) {
            System.out.println("\n===== Past Bookings Menu =====");
            System.out.println("1. Search Past Bookings");
            System.out.println("2. View Past Bookings");
            System.out.println("0. Back to Main Menu"); 
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> searchPastBookings();
                case 2 -> viewPastBookings();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void searchServiceListing() {
        System.out.println("\n=== Search Service Listing ===");
		System.out.print("Service Name: ");
		String name = scanner.nextLine();

        List<ServiceListing> listings = serviceListingDAO.searchListingsByKeyword(name);
        if (listings != null) {
            for (ServiceListing listing : listings) {
                System.out.println(listing);
            }
        } else {
            System.out.println("No listings found.");
        }
    }

    private void viewServiceListing() {
        List<ServiceListing> listings = serviceListingDAO.getAllListings();
        if (listings.isEmpty()) {
            System.out.println("There are no service listings.");
        } else {
            System.out.println("Available Listings:");
            for (ServiceListing listing : listings) {
                System.out.printf("%d | %s | %s | %s | %d\n",
                    listing.getServiceId(),
                    listing.getName(),
                    listing.getCategory(),
                    listing.getDescription(),
                    listing.getCleanerId()
                );
            }
            
            System.out.print("Enter Listing Id to view details: ");
            int selectedId = scanner.nextInt();
            scanner.nextLine();
            ServiceListing selectedListing = serviceListingDAO.getListingById(selectedId);
            if (selectedListing != null) {
                System.out.println(selectedListing);
            } else {
                System.out.println("Listing not found.");
            }
        }
    }

    private void saveServiceListing() {
        List<ServiceListing> listings = serviceListingDAO.getAllListings();
        if (listings.isEmpty()) {
            System.out.println("There are no service listings.");
        } else {
            System.out.println("Available Listings:");
            for (ServiceListing listing : listings) {
                System.out.printf("%d | %s | %s | %s | %d\n",
                    listing.getServiceId(),
                    listing.getName(),
                    listing.getCategory(),
                    listing.getDescription(),
                    listing.getCleanerId()
                );
            }
            System.out.print("Enter Listing Id to save: ");
            int selectedId = scanner.nextInt();
            scanner.nextLine();
            if(serviceListingDAO.saveServiceListing(uid, selectedId)){
                System.out.println("Service listing saved to shortlist.");
            } else {
                System.out.println("Failed to save service listing to shortlist.");
            }
        }
    }

    private void searchShortList() {
        System.out.println("\n=== Search Shortlist ===");
		System.out.print("Service Name: ");
		String name = scanner.nextLine();

        List<ServiceListing> listings = serviceListingDAO.searchShortlistedService(uid, name);
        if (listings != null) {
            for (ServiceListing listing : listings) {
                System.out.println(listing);
            }
        } else {
            System.out.println("No listings found.");
        }
    }

    private void viewShortList() {
        List<ServiceListing> listings = serviceListingDAO.getShortlistedServices(uid);
        if (listings.isEmpty()) {
            System.out.println("There are no service listings.");
        } else {
            System.out.println("Available Shortlisted Services:");
            for (ServiceListing listing : listings) {
                System.out.printf("%d | %s | %s | %s | %d\n",
                    listing.getServiceId(),
                    listing.getName(),
                    listing.getCategory(),
                    listing.getDescription(),
                    listing.getCleanerId()
                );
            }
            
        }
    }

    private void searchPastBookings() {
        System.out.println("\n=== Search Past Bookings ===");
		System.out.print("Service Name: ");
		String name = scanner.nextLine();

        List<BookingHistory> listings = bookingHistoryDAO.searchPastBookings(uid, name);
        if (listings != null) {
            for (BookingHistory listing : listings) {
                System.out.println(listing);
            }
        } else {
            System.out.println("No listings found.");
        }
    }

    private void viewPastBookings() {
        List<BookingHistory> listings = bookingHistoryDAO.getPastBookings(uid);
        if (listings.isEmpty()) {
            System.out.println("No completed bookings found.");
        } else {
            for (BookingHistory listing : listings) {
                System.out.printf("%s | %s | %s | %s\n",
                    listing.getHistoryId(),
                    listing.getHomeownerId(),
                    listing.getServiceId(),
                    listing.getStatus());
            }
        }
    }

    // Getters
    public int getUid() { return uid; }
    public String getUsername() { return username; }

    //Setters
    public void setUid(int uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }  
}
