package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ServiceListing {

    private int serviceId;
    private String name;
    private int cleanerId;
    private int categoryId;
    private String description;
    private double priceperhour;
    private String startDate;
    private String endDate;
    private String status;

    private int views;
    private int shortlist;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without Id
    public ServiceListing(String name, int cleanerId, int categoryId, String description, double priceperhour, String startDate, String endDate,
                         String status) {
        this.name = name;
        this.cleanerId = cleanerId;
        this.categoryId = categoryId;
        this.description = description;
        this.priceperhour = priceperhour;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public enum Status {
        AVAILABLE, PENDING, UNAVAILABLE
    }
    

    // Getters
    public int getServiceId() { return serviceId; }
    public String getName() { return name; }
    public int getCleanerId() { return cleanerId; }
    public int getCategoryId() {return categoryId; }
    public String getDescription() { return description; }
    public double getPricePerHour() { return priceperhour; }
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
    public void setCategoryId(int new_categoryId) { this.categoryId = new_categoryId; }
    public void setPricePerHour(double new_price) { this.priceperhour = new_price; }
    public void setStartDate(String new_startDate) { this.startDate = new_startDate; }
    public void setEndDate(String new_endDate) { this.endDate = new_endDate; }
    public void setStatus(String new_status) { this.status = new_status; }

    public void setViews(int new_views) { this.views = new_views; }
    public void setShortlist(int new_shortlist) { this.shortlist = new_shortlist; }
}
