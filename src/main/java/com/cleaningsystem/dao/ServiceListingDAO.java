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
        listing.setName(rs.getString("namee"));
        listing.setCategory(rs.getString("category"));
        listing.setDescription(rs.getString("description"));
        listing.setPricePerHour(rs.getDouble("price_per_hour"));
        listing.setStartDate(rs.getDate("startDate").toLocalDate().toString());
        listing.setEndDate(rs.getDate("endDate").toLocalDate().toString());
        listing.setCleanerId(rs.getInt("cleanerId"));
        listing.setStatus(rs.getString("status"));
        return listing;
    };

    public int createListing(ServiceListing listing) {
        java.sql.Date sqlStart = java.sql.Date.valueOf(listing.getStartDate());
        java.sql.Date sqlEnd = java.sql.Date.valueOf(listing.getEndDate());
        return jdbcTemplate.update(CREATE_SERVICE_LISTING, 
            listing.getName(), listing.getCleanerId(), listing.getCategory(), listing.getDescription(), listing.getPricePerHour(),
            sqlStart, sqlEnd, listing.getStatus());
    }

    public ServiceListing getListingById(int listingId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_ID, listingRowMapper, listingId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public List<ServiceListing> getListingsByCleanerId(int cleanerId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_CLEANER_ID, listingRowMapper, cleanerId);
        return listings.isEmpty() ? null : listings;
    }

    public boolean updateListing(ServiceListing listing) {
        return jdbcTemplate.update(UPDATE_SERVICE_LISTING, 
        listing.getName(), listing.getDescription(), listing.getCategory(), listing.getPricePerHour(),
        listing.getStatus()) > 0;
    }

    public boolean deleteListing(int listingId) {
        return jdbcTemplate.update(DELETE_SERVICE_LISTING, listingId) > 0;
    }

    public List<ServiceListing> searchListingsByCleanerAndKeyword(int cleanerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SERVICE_LISTING_BY_CLEANER_AND_KEYWORD, listingRowMapper, cleanerId, pattern, pattern);
    }
} 