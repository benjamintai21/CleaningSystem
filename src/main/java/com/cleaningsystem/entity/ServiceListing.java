package com.cleaningsystem.entity;

import static com.cleaningsystem.db.Queries.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceListing {

    private int serviceId;
    private String name;
    private int cleanerId;
    private int categoryId;
    private String description;
    private double pricePerHour;
    private String startDate;
    private String endDate;
    private String status;

    private int views;
    private int shortlists;

    // No-args constructor
    public ServiceListing() {}

    // Constructor without Id
    public ServiceListing(String name, int cleanerId, int categoryId, String description, double pricePerHour, String startDate, String endDate,
                         String status, int views, int shortlists) {
        this.name = name;
        this.cleanerId = cleanerId;
        this.categoryId = categoryId;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.views = views;
        this.shortlists = shortlists;
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
    public double getPricePerHour() { return pricePerHour; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getStatus() { return status; }

    public int getViews() { return views; }
    public int getShortlists() { return shortlists; }


    //Setters
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public void setName(String new_name) { this.name = new_name; }
    public void setCleanerId(int new_cleanerId) { this.cleanerId = new_cleanerId; }
    public void setDescription(String new_description) { this.description = new_description; }
    public void setCategoryId(int new_categoryId) { this.categoryId = new_categoryId; }
    public void setPricePerHour(double new_price) { this.pricePerHour = new_price; }
    public void setStartDate(String new_startDate) { this.startDate = new_startDate; }
    public void setEndDate(String new_endDate) { this.endDate = new_endDate; }
    public void setStatus(String new_status) { this.status = new_status; }
    public void setViews(int new_views) { this.views = new_views; }
    public void setShortlists(int new_shortlists) { this.shortlists = new_shortlists; }

    // Miscellanous
    @Autowired
    private UserAccount userAccount;

    public List<Integer> getServicesCountList(){
    List<Integer> servicesCountList = new ArrayList<>();
        List<UserAccount> cleaners = userAccount.searchUsersByProfileId(4);
        for (UserAccount cleaner : cleaners) {
        List<ServiceListing> serviceListings = getAllListingsById(cleaner.getUid());
            int servicesCount = serviceListings.size();
            servicesCountList.add(servicesCount);
        }
        return servicesCountList;
    }
    // Databases Stuff
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceListing> listingRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceListing listing = new ServiceListing();
        listing.setServiceId(rs.getInt("serviceId"));
        listing.setName(rs.getString("name"));
        listing.setCleanerId(rs.getInt("cleanerId"));
        listing.setCategoryId(rs.getInt("categoryId"));
        listing.setDescription(rs.getString("description"));
        listing.setPricePerHour(rs.getDouble("price_per_hour"));
        listing.setStartDate(rs.getDate("startDate").toLocalDate().toString());
        listing.setEndDate(rs.getDate("endDate").toLocalDate().toString());
        listing.setStatus(rs.getString("status"));
        listing.setViews(rs.getInt("views"));
        listing.setShortlists(rs.getInt("shortlists"));
        return listing;
    };

    //Cleaner
    public boolean insertListing(String name, int cleanerId, int categoryId, String description, double priceperhour, 
                                 String startDate, String endDate, String status) {
        // java.sql.Date sqlStart = java.sql.Date.valueOf(startDate);
        // java.sql.Date sqlEnd = java.sql.Date.valueOf(endDate);
        return jdbcTemplate.update(CREATE_SERVICE_LISTING, 
        name, cleanerId, categoryId, description, priceperhour, startDate, endDate, status) > 0;
    }

    public ServiceListing viewServiceListingByServiceId(int serviceId) {
        List<ServiceListing> listings = jdbcTemplate.query(VIEW_SERVICE_LISTING_BY_SERVICE_ID, listingRowMapper, serviceId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public ServiceListing getListingById(int serviceId , int cleanerId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_ID, listingRowMapper, serviceId, cleanerId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public boolean updateListing(String name, int cleanerId, int categoryId, String description, double priceperhour, 
                                    String startDate, String endDate, String status, int serviceId) {
        return jdbcTemplate.update(UPDATE_SERVICE_LISTING, 
        name, cleanerId, categoryId, description, priceperhour, 
        startDate, endDate, status, serviceId) > 0;
    }

    public boolean deleteListing(int listingId) {
        return jdbcTemplate.update(DELETE_SERVICE_LISTING, listingId) > 0;
    }

    public boolean deleteListingByCategory(int categoryId) {
        return jdbcTemplate.update(DELETE_SERVICE_LISTING, categoryId) > 0;
    }

    public List<ServiceListing> searchMyListings(int cleanerId, String keyword){
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_MY_SERVICE_LISTING_BY_ID, listingRowMapper, cleanerId, pattern);
    }

    public List<ServiceListing> getAllListingsById(int cleanerId){
        return jdbcTemplate.query(GET_ALL_SERVICE_LISTINGS_BY_ID, listingRowMapper, cleanerId);
    }

    public ServiceListing getLastListing() {
        List<ServiceListing> listings = jdbcTemplate.query(GET_MAX_SERVICELISTING, listingRowMapper);
        return listings.isEmpty() ? null : listings.get(0);
    }

    //HomeOwner-----------------------------------------------------
    public List<ServiceListing> searchListingsByService(String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SERVICE_LISTING_BY_SERVICE, listingRowMapper, pattern);
    }

    public List<ServiceListing> searchListingsByCleaner(String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SERVICE_LISTING_BY_CLEANER, listingRowMapper, pattern);
    }

    public List<ServiceListing> getServiceListingsByCategory(int categoryId) {
        return jdbcTemplate.query(GET_SERVICE_LISTING_BY_CATEGORY, listingRowMapper, categoryId);
    }

    public List<ServiceListing> getAllListings(){
        return jdbcTemplate.query(GET_ALL_SERVICE_LISTINGS, listingRowMapper);
    }

    public boolean updateViews(int serviceId) {
        return jdbcTemplate.update(UPDATE_VIEWS, serviceId) > 0;
    }

    public boolean updateShortlisting(int serviceId) {
        return jdbcTemplate.update(UPDATE_SHORTLISTING, serviceId) > 0;
    }
}
