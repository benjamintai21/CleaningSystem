package com.cleaningsystem.model;

public class ServiceListing {
    private int listingID;
    private String title;
    private String description;
    private double pricePerHour;
    private String availableDays;
    private int cleanerID;
    private boolean active;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without ID
    public ServiceListing(String title, String description, double pricePerHour, 
                         String availableDays, int cleanerID) {
        this.title = title;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.availableDays = availableDays;
        this.cleanerID = cleanerID;
        this.active = true;
    }

    // Constructor with ID
    public ServiceListing(int listingID, String title, String description, 
                         double pricePerHour, String availableDays, 
                         int cleanerID, boolean active) {
        this(title, description, pricePerHour, availableDays, cleanerID);
        this.listingID = listingID;
        this.active = active;
    }

    // Getters
    public int getListingID() { return listingID; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPricePerHour() { return pricePerHour; }
    public String getAvailableDays() { return availableDays; }
    public int getCleanerID() { return cleanerID; }
    public boolean isActive() { return active; }

    // Setters
    public void setListingID(int listingID) { this.listingID = listingID; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }
    public void setCleanerID(int cleanerID) { this.cleanerID = cleanerID; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "ServiceListing{" +
                "listingID=" + listingID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", availableDays='" + availableDays + '\'' +
                ", cleanerID=" + cleanerID +
                ", active=" + active +
                '}';
    }
}
