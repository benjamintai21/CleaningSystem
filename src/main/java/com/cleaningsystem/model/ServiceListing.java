package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ServiceListing {

    private int serviceId;
    private String name;
    private int cleanerId;
    private int category;
    private String description;
    private double price_per_hour;
    private String startDate;
    private String endDate;
    private String status;

    private int views;
    private int shortlist;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without Id
    public ServiceListing(String name, int cleanerId, int category, String description, double price_per_hour, String startDate, String endDate,
                         String status) {
        this.name = name;
        this.cleanerId = cleanerId;
        this.category = category;
        this.description = description;
        this.price_per_hour = price_per_hour;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters
    public int getServiceId() { return serviceId; }
    public String getName() { return name; }
    public int getCleanerId() { return cleanerId; }
    public int getCategory() {return category; }
    public String getDescription() { return description; }
    public double getPricePerHour() { return price_per_hour; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }

    public int getViews() { return views; }
    public int getShortlist() { return shortlist; }


    //Setters
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setName(String new_name) { this.name = new_name; }
    public void setCleanerId(int new_cleanerId) { this.cleanerId = new_cleanerId; }
    public void setDescription(String new_description) { this.description = new_description; }
    public void setCategory(int new_category) { this.category = new_category; }
    public void setPricePerHour(double new_price) { this.price_per_hour = new_price; }
    public void setStartDate(String new_startDate) { this.startDate = new_startDate; }
    public void setEndDate(String new_endDate) { this.endDate = new_endDate; }
    public void setStatus(String new_status) { this.status = new_status; }

    public void setViews(int new_views) { this.views = new_views; }
    public void setShortlist(int new_shortlist) { this.shortlist = new_shortlist; }
}
