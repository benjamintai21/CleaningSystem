package com.cleaningsystem.model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.dao.ServiceListingDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.model.ServiceListing;

@Component
public class Cleaner {
	private int uid;
	private String username;
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Autowired
	private UserProfileDAO userProfileDAO;
	
	@Autowired
	private ServiceListingDAO serviceListingDAO;
	
	private static Scanner scanner = new Scanner(System.in);

	// No-args constructor for Spring
	public Cleaner() {}

	public Cleaner(int uid, String username) {
		this.uid = uid;
		this.username = username;
	}

	public void showCleanerMenu() {
		while (true) {
			System.out.println("\n===== Cleaner Menu =====");
			System.out.println("1. Create a Service Listing");
			System.out.println("2. View My Listing");
			System.out.println("3. Update My Listing");
			System.out.println("4. Delete My Listing");
			System.out.println("5. Search My Listing");
			System.out.println("6. Logout");
			System.out.println("0. Exit");
			System.out.print("Select an option: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> createServiceListing();
				case 2 -> viewServiceListing();
				case 3 -> updateServiceListing();
				case 4 -> deleteServiceListing();
				case 5 -> searchServiceListing();
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
	
	private void createServiceListing() {
		System.out.println("\n=== Create Service Listing ===");
		System.out.print("Service Name: ");
		String name = scanner.nextLine();
		
		System.out.print("Service Category: ");
		String category = scanner.nextLine();
		
		System.out.print("Description: ");
		String description = scanner.nextLine();
		
		System.out.print("Price per Hour ($/hr): ");
		double pricePerHour = scanner.nextDouble();
		scanner.nextLine();
		
		System.out.print("Available Days (comma-separated, e.g. Mon,Tue,Wed): ");
		String availableDays = scanner.nextLine();
		
		ServiceListing listing = new ServiceListing(name,description, pricePerHour, 
			availableDays, uid);
		
		if (serviceListingDAO.createListing(listing) > 0) {
			System.out.println("Service listing created successfully.");
		} else {
			System.out.println("Failed to create service listing.");
		}
	}

	private void viewServiceListing() {
		List<ServiceListing> listings = serviceListingDAO.getListingsByCleanerID(uid);
		if (listings.isEmpty()) {
			System.out.println("You have no service listings.");
			return;
		}
		
		System.out.println("\n=== Your Service Listings ===");
		for (ServiceListing listing : listings) {
			System.out.println(listing);
		}
	}

	private void updateServiceListing() {
		List<ServiceListing> listings = serviceListingDAO.getListingsByCleanerID(uid);
		if (listings.isEmpty()) {
			System.out.println("You have no service listings to update.");
			return;
		}
		
		System.out.println("\n=== Your Service Listings ===");
		for (ServiceListing listing : listings) {
			System.out.println(listing.getListingID() + ": " + listing.getTitle());
		}
		
		System.out.print("\nEnter Listing ID to update: ");
		int listingId = scanner.nextInt();
		scanner.nextLine();
		
		ServiceListing listing = serviceListingDAO.getListingByID(listingId);
		if (listing == null || listing.getCleanerID() != uid) {
			System.out.println("Invalid listing ID or you don't own this listing.");
			return;
		}
		
		System.out.println("Leave blank to keep existing value.");
		
		System.out.print("New Title (" + listing.getTitle() + "): ");
		String title = scanner.nextLine();
		if (!title.isEmpty()) listing.setTitle(title);
		
		System.out.print("New Description (" + listing.getDescription() + "): ");
		String description = scanner.nextLine();
		if (!description.isEmpty()) listing.setDescription(description);
		
		System.out.print("New Price per Hour (" + listing.getPricePerHour() + "): ");
		String priceStr = scanner.nextLine();
		if (!priceStr.isEmpty()) {
			listing.setPricePerHour(Double.parseDouble(priceStr));
		}
		
		System.out.print("New Available Days (" + listing.getAvailableDays() + "): ");
		String days = scanner.nextLine();
		if (!days.isEmpty()) listing.setAvailableDays(days);
		
		if (serviceListingDAO.updateListing(listing)) {
			System.out.println("Service listing updated successfully.");
		} else {
			System.out.println("Failed to update service listing.");
		}
	}

	private void deleteServiceListing() {
		List<ServiceListing> listings = serviceListingDAO.getListingsByCleanerID(uid);
		if (listings.isEmpty()) {
			System.out.println("You have no service listings to delete.");
			return;
		}
		
		System.out.println("\n=== Your Service Listings ===");
		for (ServiceListing listing : listings) {
			System.out.println(listing.getListingID() + ": " + listing.getTitle());
		}
		
		System.out.print("\nEnter Listing ID to delete: ");
		int listingId = scanner.nextInt();
		scanner.nextLine();
		
		ServiceListing listing = serviceListingDAO.getListingByID(listingId);
		if (listing == null || listing.getCleanerID() != uid) {
			System.out.println("Invalid listing ID or you don't own this listing.");
			return;
		}
		
		System.out.print("Are you sure you want to delete this listing? (yes/no): ");
		String confirm = scanner.nextLine();
		
		if (confirm.equalsIgnoreCase("yes")) {
			if (serviceListingDAO.deleteListing(listingId)) {
				System.out.println("Service listing deleted successfully.");
			} else {
				System.out.println("Failed to delete service listing.");
			}
		}
	}

	private void searchServiceListing() {
		System.out.print("Enter search keyword: ");
		String keyword = scanner.nextLine();
		
		List<ServiceListing> results = serviceListingDAO.searchListingsByCleanerAndKeyword(uid, keyword);
		if (results.isEmpty()) {
			System.out.println("No matching listings found.");
			return;
		}
		
		System.out.println("\n=== Search Results ===");
		for (ServiceListing listing : results) {
			System.out.println(listing);
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

	private void viewAvailableJobs() {
		// TODO: Implement job listing functionality
		System.out.println("Available jobs functionality coming soon...");
	}

	private void viewMyJobs() {
		// TODO: Implement my jobs functionality
		System.out.println("My jobs functionality coming soon...");
	}

	// Getters
	public int getUid() { return uid; }
	public String getUsername() { return username; }

	//Setters
	public void setUid(int uid) { this.uid = uid; }
	public void setUsername(String username) { this.username = username; }
}
