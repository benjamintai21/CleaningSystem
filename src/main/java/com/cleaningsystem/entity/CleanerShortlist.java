package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import static com.cleaningsystem.db.Queries.*;

import java.sql.ResultSet;
import java.util.List;

@Component
public class CleanerShortlist {

    private int shortlistId;
    private int homeownerId;
    private int cleanerId;

    // No-args constructor
    public CleanerShortlist() {}

    public CleanerShortlist(int homeownerId, int cleanerId) {
        this.homeownerId = homeownerId;
        this.cleanerId = cleanerId;
    }

    public int getShortlistId(){ return shortlistId; }
    public int getHomeownerId(){ return homeownerId; }
    public int getCleanerId(){ return cleanerId; }

    public void setShortlistId(int new_shortlistId){this.shortlistId = new_shortlistId;}
    public void setHomeownerId(int new_homeownerId){this.homeownerId = new_homeownerId;}
    public void setCleanerId(int new_cleanerId){this.cleanerId = new_cleanerId;}

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<CleanerShortlist> listingRowMapper2 = (ResultSet rs, int rowNum) -> {
        CleanerShortlist cleanerShortlist = new CleanerShortlist();
        // shortlist.setShortlistId(rs.getInt("shortlistId"));
        cleanerShortlist.setHomeownerId(rs.getInt("homeownerId"));
        cleanerShortlist.setCleanerId(rs.getInt("cleanerId"));
        return cleanerShortlist;
    };

    public boolean saveToCleanerShortlist(int homeownerId, int cleanerId) {
        int rows_affected = jdbcTemplate.update(SAVE_SHORTLISTED_CLEANER, homeownerId, 
            cleanerId);

        return rows_affected > 0;
    }

    public List<CleanerShortlist> getAllShortlistedCleaners(int homeownerId) {
        return jdbcTemplate.query(GET_ALL_SHORTLISTED_CLEANERS, listingRowMapper2, homeownerId);
    }

    public List<CleanerShortlist> searchShortlistedCleaners(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SHORTLISTED_CLEANER_BY_USERNAME, listingRowMapper2, homeownerId, pattern);
    }

    public boolean checkShortlistedCleaners(int cleanerId) {
        List<CleanerShortlist> shortlists = jdbcTemplate.query(CHECK_SHORTLISTED_CLEANERS, listingRowMapper2, cleanerId);
        return !shortlists.isEmpty();
    }

    public boolean deleteShortlistedCleaners(int homeownerId, int cleanerId) {
        return jdbcTemplate.update(DELETE_SHORTLISTED_CLEANERS, homeownerId, cleanerId) > 0;
    }
}