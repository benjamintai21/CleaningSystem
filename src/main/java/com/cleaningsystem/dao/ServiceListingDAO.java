package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceListing;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ServiceListingDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceListing> listingRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceListing listing = new ServiceListing();
        listing.setListingID(rs.getInt("listing_id"));
        listing.setTitle(rs.getString("title"));
        listing.setDescription(rs.getString("description"));
        listing.setPricePerHour(rs.getDouble("price_per_hour"));
        listing.setAvailableDays(rs.getString("available_days"));
        listing.setCleanerID(rs.getInt("cleaner_id"));
        listing.setActive(rs.getBoolean("active"));
        return listing;
    };

    public int createListing(ServiceListing listing) {
        String sql = "INSERT INTO service_listings (title, description, price_per_hour, " +
                    "available_days, cleaner_id, active) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            listing.getTitle(), listing.getDescription(), listing.getPricePerHour(),
            listing.getAvailableDays(), listing.getCleanerID(), listing.isActive());
    }

    public ServiceListing getListingByID(int listingId) {
        String sql = "SELECT * FROM service_listings WHERE listing_id = ?";
        List<ServiceListing> listings = jdbcTemplate.query(sql, listingRowMapper, listingId);
        return listings.isEmpty() ? null : listings.get(0);
    }

    public List<ServiceListing> getListingsByCleanerID(int cleanerId) {
        String sql = "SELECT * FROM service_listings WHERE cleaner_id = ?";
        return jdbcTemplate.query(sql, listingRowMapper, cleanerId);
    }

    public boolean updateListing(ServiceListing listing) {
        String sql = "UPDATE service_listings SET title = ?, description = ?, " +
                    "price_per_hour = ?, available_days = ?, active = ? " +
                    "WHERE listing_id = ? AND cleaner_id = ?";
        return jdbcTemplate.update(sql, 
            listing.getTitle(), listing.getDescription(), listing.getPricePerHour(),
            listing.getAvailableDays(), listing.isActive(), 
            listing.getListingID(), listing.getCleanerID()) > 0;
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