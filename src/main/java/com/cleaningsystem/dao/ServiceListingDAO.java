package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceListing;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Date;

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
    public boolean createListing(ServiceListing listing) {
        java.sql.Date sqlStart = java.sql.Date.valueOf(listing.getStartDate());
        java.sql.Date sqlEnd = java.sql.Date.valueOf(listing.getEndDate());
        int rows_affected = jdbcTemplate.update(CREATE_SERVICE_LISTING, 
            listing.getName(), listing.getCleanerId(), listing.getCategory(), listing.getDescription(), listing.getPricePerHour(),
            sqlStart, sqlEnd, listing.getStatus());

        return rows_affected > 0;
    }

    public ServiceListing getListingById(int listingId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_ID, listingRowMapper, listingId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public List<ServiceListing> getListingsByCleanerId(int cleanerId) {
        return jdbcTemplate.query(GET_SERVICE_LISTING_BY_CLEANER_ID, listingRowMapper, cleanerId);
    }

    public boolean updateListing(ServiceListing listing) {
        return jdbcTemplate.update(UPDATE_SERVICE_LISTING, 
        listing.getName(), listing.getCleanerId(), listing.getCategory(), listing.getDescription(), listing.getPricePerHour(),
        listing.getStatus(), listing.getServiceId()) > 0;
    }

    public boolean deleteListing(int listingId) {
        return jdbcTemplate.update(DELETE_SERVICE_LISTING, listingId) > 0;
    }

    public List<ServiceListing> searchMyServiceListings(int cleanerId, String keyword){
        String pattern = "%" + keyword + "$";
        return jdbcTemplate.query(SEARCH_MY_SERVICE_LISTING, listingRowMapper, pattern);
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
    public int getNumberofViews(){
        return 0;
    }

    public int getNumberOfShortlists(int cleanerId){
        //eg SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE cleanerId = ? GROUP BY cleanerId;
        return 0;
    }


} 