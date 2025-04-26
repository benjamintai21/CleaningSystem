package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceListing;
import static com.cleaningsystem.dao.SQL_query.*;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Date;

@Repository
public class ServiceListingDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceListing> listingRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceListing listing = new ServiceListing();
        listing.setServiceID(rs.getInt("servicegId"));
        listing.setName(rs.getString("namee"));
        listing.setCategory(rs.getString("category"));
        listing.setDescription(rs.getString("description"));
        listing.setPricePerHour(rs.getDouble("price_per_hour"));
        listing.setStartDate(rs.getDate("startDate").toLocalDate().toString());
        listing.setEndDate(rs.getDate("endDate").toLocalDate().toString());
        listing.setCleanerID(rs.getInt("cleanerID"));
        listing.setStatus(rs.getString("status"));
        return listing;
    };

    public int createListing(ServiceListing listing) {
        java.sql.Date sqlStart = java.sql.Date.valueOf(listing.getStartDate());
        java.sql.Date sqlEnd = java.sql.Date.valueOf(listing.getEndDate());
        return jdbcTemplate.update(CREATE_SERVICE_LISTING, 
            listing.getName(), listing.getCleanerID(), listing.getCategory(), listing.getDescription(), listing.getPricePerHour(),
            sqlStart, sqlEnd, listing.getStatus());
    }

    public ServiceListing getListingByID(int listingId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_ID, listingRowMapper, listingId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public List<ServiceListing> getListingsByCleanerID(int cleanerId) {
        List<ServiceListing> listings = jdbcTemplate.query(GET_SERVICE_LISTING_BY_CLEANER_ID, listingRowMapper, cleanerId);
        return listings.isEmpty() ? null : listings;
    }

    public boolean updateListing(ServiceListing listing) {
        String sql = "UPDATE service_listings SET title = ?, description = ?, " +
                    "price_per_hour = ?, available_days = ?, active = ? " +
                    "WHERE listing_id = ? AND cleaner_id = ?";
        return jdbcTemplate.update(sql, 
        listing.getName(), listing.getDescription(), listing.getCategory(), listing.getPricePerHour(),
        listing.getStatus()) > 0;
    }

    public boolean deleteListing(int listingId) {
        String sql = "DELETE FROM service_listings WHERE listing_id = ?";
        return jdbcTemplate.update(sql, listingId) > 0;
    }

    public List<ServiceListing> searchListingsByCleanerAndKeyword(int cleanerId, String keyword) {
        String sql = "SELECT * FROM service_listings WHERE cleaner_id = ? AND " +
                    "(title LIKE ? OR description LIKE ?)";
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(sql, listingRowMapper, cleanerId, pattern, pattern);
    }
} 