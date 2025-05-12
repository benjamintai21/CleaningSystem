package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cleaningsystem.model.Booking;
import com.cleaningsystem.model.Report;

import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ReportDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Report> listingRowMapper = (ResultSet rs, int rowNum) -> {
        Report report = new Report();
        report.setReportId(rs.getInt("reportId")); 
	    report.setType(rs.getString("type")); 
        report.setDate(rs.getString("date")); 
        report.setNewOwners(rs.getInt("new_owners"));
        report.setTotalOwners(rs.getInt("total_owners")); 
        report.setNewCleaner(rs.getInt("new_cleaners"));
        report.setTotalCleaner(rs.getInt("total_cleaners"));
        report.setNoOfShortlists(rs.getInt("no_shortlists"));
        report.setNoOfBookings(rs.getInt("no_bookings"));
        return report;
    };

    public int generateDailyReport() {
        jdbcTemplate.query(GET_DAILY_REPORT, listingRowMapper);
        return 0;
    }
    
    public int generateWeeklyReport() {
        jdbcTemplate.query(GET_WEEKLY_REPORT, listingRowMapper);
        return 0;
    }

    public int generateMonthlyReport() {
        jdbcTemplate.query(GET_MONTHLY_REPORT, listingRowMapper);
        return 0;
    }
}
