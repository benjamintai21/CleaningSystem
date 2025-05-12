package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cleaningsystem.model.CleanerShortlist;
import com.cleaningsystem.model.ServiceShortlist;

import static com.cleaningsystem.dao.Queries.*;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ShortlistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceShortlist> listingRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceShortlist serviceShortlists = new ServiceShortlist();
        // shortlist.setShortlistId(rs.getInt("shortlistId"));
        serviceShortlists.setHomeownerId(rs.getInt("homeownerId"));
        serviceShortlists.setServiceId(rs.getInt("serviceId"));
        return serviceShortlists;
    };

    private final RowMapper<CleanerShortlist> listingRowMapper2 = (ResultSet rs, int rowNum) -> {
        CleanerShortlist cleanerShortlist = new CleanerShortlist();
        // shortlist.setShortlistId(rs.getInt("shortlistId"));
        cleanerShortlist.setHomeownerId(rs.getInt("homeownerId"));
        cleanerShortlist.setCleanerId(rs.getInt("cleanerId"));
        return cleanerShortlist;
    };
    
    public boolean saveToServiceShortlist(int homeownerId, int serviceId) {
        int rows_affected = jdbcTemplate.update(SAVE_SHORTLISTED_SERVICE, 
            homeownerId, serviceId);

        return rows_affected > 0;
    }

    public boolean saveToCleanerShortlist(int homeownerId, int cleanerId) {
        int rows_affected = jdbcTemplate.update(SAVE_SHORTLISTED_CLEANER, homeownerId, 
            cleanerId);

        return rows_affected > 0;
    }

    public ServiceShortlist getShortlistById(int shortlistId, int homeownerId) {
        List<ServiceShortlist> shortlists = jdbcTemplate.query(GET_SHORTLIST_BY_ID, listingRowMapper, shortlistId, homeownerId);
        return shortlists.isEmpty() ? null : shortlists.get(0);
    }

    public List<ServiceShortlist> searchShortlistedServices(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SHORTLISTED_SERVICE_BY_NAME, listingRowMapper, homeownerId, pattern);
    }

    public List<ServiceShortlist> getAllShortlistedServices(int homeownerId) {
        return jdbcTemplate.query(GET_ALL_SHORTLISTED_SERVICES, listingRowMapper, homeownerId);
    }

    public List<CleanerShortlist> getAllShortlistedCleaners(int homeownerId) {
        return jdbcTemplate.query(GET_ALL_SHORTLISTED_CLEANERS, listingRowMapper2, homeownerId);
    }

    public List<CleanerShortlist> searchShortlistedCleaners(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SHORTLISTED_CLEANER_BY_USERNAME, listingRowMapper2, homeownerId, pattern);
    }
    
    //???
    public List<ServiceShortlist> getNumberofViews(int cleanerId){
        return jdbcTemplate.query(GET_NO_OF_VIEWS, listingRowMapper, cleanerId);
    }

    public List<ServiceShortlist> getNumberOfShortlists(int serviceId){
        return jdbcTemplate.query(GET_NO_OF_SHORTLISTS, listingRowMapper, serviceId);
        //eg SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE cleanerId = ? GROUP BY cleanerId;
    }
} 