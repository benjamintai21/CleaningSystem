package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cleaningsystem.model.Shortlist;

import static com.cleaningsystem.dao.Queries.*;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ShortlistDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Shortlist> listingRowMapper = (ResultSet rs, int rowNum) -> {
        Shortlist shortlist = new Shortlist();
        shortlist.setShortlistId(rs.getInt("serviceId"));
        shortlist.setHomeOwnerId(rs.getInt("homeownerid"));
        shortlist.setServiceId(rs.getInt("serviceIdId"));
        return shortlist;
    };
    
    public boolean saveToShortlist(int homeownerId, int serviceId) {
        int rows_affected = jdbcTemplate.update(SAVE_SHORTLISTED_SERVICE, 
            homeownerId, serviceId);

        return rows_affected > 0;
    }

    public Shortlist getShortlistById(int shortlistId, int homeownerId) {
        List<Shortlist> shortlists = jdbcTemplate.query(GET_SHORTLIST_BY_ID, listingRowMapper, shortlistId, homeownerId);
        return shortlists.isEmpty() ? null : shortlists.get(0);
    }

    public List<Shortlist> searchShortlistedService(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_SHORTLISTED_SERVICE_BY_NAME, listingRowMapper, homeownerId, pattern);
    }

    public List<Shortlist> getAllShortlists(int homeownerId) {
        return jdbcTemplate.query(GET_ALL_SHORTLISTS, listingRowMapper, homeownerId);
    }

    
    //???
    public List<Shortlist> getNumberofViews(int cleanerId){
        return jdbcTemplate.query(GET_NO_OF_VIEWS, listingRowMapper, cleanerId);
    }

    public List<Shortlist> getNumberOfShortlists(int serviceId){
        return jdbcTemplate.query(GET_NO_OF_SHORTLISTS, listingRowMapper, serviceId);
        //eg SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE cleanerId = ? GROUP BY cleanerId;
    }
} 