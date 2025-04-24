package com.cleaningsystem.model;

public class ServiceListing {
    private int serviceID;
    private String name;
    private String category;
    private String description;
    private double price;
    private String status;
    private int views;
    private int shortlist;
    private int cleanerID;
    private String availableDays;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without ID
    public ServiceListing(String name, String category, String description, double price,
                         String status) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    // Getters
    public int getServiceID() { return serviceID; }
    public String getName() { return name; }
    public String getCategory() {return category; }
    public String getDescription() { return description; }
    public double getPricePerHour() { return price; }
    public String getAvailableDays() { return availableDays; }
    public String getStatus() { return status; }
    public int getCleanerID() { return cleanerID; }


    //Setters
    public void setServiceID(int serviceID) { this.serviceID = serviceID; }
    public void setName(String new_name) { this.name = new_name; }
    public void setDescription(String new_description) { this.description = new_description; }
    public void setCategory(String new_category) { this.category = new_category; }
    public void setPricePerHour(double new_price) { this.price = new_price; }
    public void setCleanerID(int new_cleanerID) { this.cleanerID = new_cleanerID; }
    public void setAvailableDays(String new_availableDays) { this.availableDays = new_availableDays; }
    public void setStatus(String new_status) { this.status = new_status; }

}
