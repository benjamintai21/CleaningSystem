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
        listing.setCategory(rs.getInt("categoryId"));
        listing.setDescription(rs.getString("description"));
        listing.setPricePerHour(rs.getDouble("price_per_hour"));
        listing.setStartDate(rs.getDate("startDate").toLocalDate().toString());
        listing.setEndDate(rs.getDate("endDate").toLocalDate().toString());
        
        listing.setStatus(rs.getString("status"));
        return listing;
    };



    //Cleaner
    public boolean createListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                    String status, String startDate, String endDate) {
        java.sql.Date sqlStart = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEnd = java.sql.Date.valueOf(endDate);
        return jdbcTemplate.update(CREATE_SERVICE_LISTING, 
        name, cleanerId, categoryId, description, price_per_hour, status, sqlStart, sqlEnd) > 0;
    }

    public ServiceListing getListingById(int serviceId , int cleanerId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_ID, listingRowMapper, serviceId, cleanerId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public boolean updateListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                    String status, String startDate, String endDate, int serviceId) {
        return jdbcTemplate.update(UPDATE_SERVICE_LISTING, 
        name, cleanerId, categoryId, description, price_per_hour, 
        status, startDate, endDate, serviceId) > 0;
    }

    public boolean deleteListing(int listingId) {
        return jdbcTemplate.update(DELETE_SERVICE_LISTING, listingId) > 0;
    }

    public List<ServiceListing> searchMyListings(int cleanerId, String keyword){
        String pattern = "%" + keyword + "$";
        return jdbcTemplate.query(SEARCH_MY_SERVICE_LISTING, listingRowMapper, cleanerId, pattern);
    }

    public List<ServiceListing> getAllListings(){
        return jdbcTemplate.query(GET_ALL_SERVICE_LISTINGS, listingRowMapper);
    }

    //HomeOwner
    public List<ServiceListing> searchListingsByService(String keyword) {
        String pattern = "%" + keyword + "$";
        return jdbcTemplate.query(SEARCH_SERVICE_LISTING_BY_SERVICE, listingRowMapper, pattern);
    }

    public List<ServiceListing> searchListingsByCleaner(String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SERVICE_LISTING_BY_CLEANER, listingRowMapper, pattern);
    }
    
    public boolean saveServiceListing(int homeownerUID, int serviceId) {
        int rows_affected = jdbcTemplate.update(SAVE_SHORTLISTED_SERVICE, 
            homeownerUID, serviceId);

        return rows_affected > 0;
    }

    public List<ServiceListing> getShortlistedServices(int homeownerUID) {
        return jdbcTemplate.query(GET_SHORTLISTED_SERVICES, listingRowMapper, homeownerUID);
    }

    public List<ServiceListing> searchShortlistedService(int homeownerUID, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SHORTLISTED_SERVICE_BY_NAME, listingRowMapper, homeownerUID, pattern);
    }

    //???
    public List<ServiceListing> getNumberofViews(int cleanerId){
        return jdbcTemplate.query(GET_NO_OF_VIEWS, listingRowMapper, cleanerId);
    }

    public List<ServiceListing> getNumberOfShortlists(int serviceId){
        return jdbcTemplate.query(GET_NO_OF_SHORTLISTS, listingRowMapper, serviceId);
        //eg SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE cleanerId = ? GROUP BY cleanerId;
    }


} 