package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceListing;

import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ServiceListingDAO {
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
        listing.setShortlist(rs.getInt("shortlists"));
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
        String pattern = "%" + keyword + "$";
        return jdbcTemplate.query(SEARCH_MY_SERVICE_LISTING, listingRowMapper, cleanerId, pattern);
    }

    public List<ServiceListing> getAllListingsById(int cleanerId){
        return jdbcTemplate.query(GET_ALL_SERVICE_LISTINGS_BY_ID, listingRowMapper, cleanerId);
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
}
