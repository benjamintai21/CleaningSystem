package com.cleaningsystem.model;

public class ServiceListing {
    private int serviceID;
    private String name;
    private int cleanerID;
    private String category;
    private String description;
    private double price_per_hour;
    private String startDate;
    private String endDate;
    private String status;

    private int views;
    private int shortlist;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without ID
    public ServiceListing(String name, int cleanerID, String category, String description, double price_per_hour, String startDate, String endDate,
                         String status) {
        this.name = name;
        this.cleanerID = cleanerID;
        this.category = category;
        this.description = description;
        this.price_per_hour = price_per_hour;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters
    public int getServiceID() { return serviceID; }
    public String getName() { return name; }
    public int getCleanerID() { return cleanerID; }
    public String getCategory() {return category; }
    public String getDescription() { return description; }
    public double getPricePerHour() { return price_per_hour; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }

    public int getViews() { return views; }
    public int getShortlist() { return shortlist; }


    //Setters
    public void setServiceID(int serviceID) { this.serviceID = serviceID; }
    public void setName(String new_name) { this.name = new_name; }
    public void setCleanerID(int new_cleanerID) { this.cleanerID = new_cleanerID; }
    public void setDescription(String new_description) { this.description = new_description; }
    public void setCategory(String new_category) { this.category = new_category; }
    public void setPricePerHour(double new_price) { this.price_per_hour = new_price; }
    public void setStartDate(String new_startDate) { this.startDate = new_startDate; }
    public void setEndDate(String new_endDate) { this.endDate = new_endDate; }
    public void setStatus(String new_status) { this.status = new_status; }

    public void setViews(int new_views) { this.views = new_views; }
    public void setShortlist(int new_shortlist) { this.shortlist = new_shortlist; }
}
