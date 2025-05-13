package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cleaningsystem.controller.UserProfileController;
import com.cleaningsystem.controller.ReportController;
import com.cleaningsystem.model.Report;

import static com.cleaningsystem.dao.Queries.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.time.*;

@Repository
public class ReportDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserProfileController userProfileC;

    private final RowMapper<Report> listingRowMapper = (ResultSet rs, int rowNum) -> {
        Report report = new Report();
        report.setReportId(rs.getInt("reportId")); 
	    report.setType(rs.getString("type")); 
        report.setDate(rs.getDate("date").toLocalDate()); 
        report.setNewHomeOwners(rs.getInt("new_homeOwners"));
        report.setTotalHomeOwners(rs.getInt("total_homeOwners")); 
        report.setNewCleaner(rs.getInt("new_cleaners"));
        report.setTotalCleaner(rs.getInt("total_cleaners"));
        report.setNoOfShortlists(rs.getInt("total_shortlists"));
        report.setNoOfBookings(rs.getInt("total_bookings"));

        return report;
    };
    
    public int getNewAccounts (String role, String range) {
        int profileId = userProfileC.getProfileIdByName(role);
        switch (range) {
            case "daily":
                return jdbcTemplate.queryForObject(GET_DAILY_CREATED, Integer.class, profileId);
            case "weekly":
                return jdbcTemplate.queryForObject(GET_WEEKLY_CREATED, Integer.class, profileId);
            case "monthly":
                return jdbcTemplate.queryForObject(GET_MONTHLY_CREATED, Integer.class, profileId);                                                                                 
        }
        return -1;  
        // Execute query with JDBC
    }

    public int getAccountsUpToPoint(String role){
        int profileId = userProfileC.getProfileIdByName(role);   
        return jdbcTemplate.queryForObject(GET_TOTAL_CREATED, Integer.class, profileId);
    }

    public int getWeeklyAccountsUpToPoint(LocalDate pointDate, String role) {
        int profileId = userProfileC.getProfileIdByName(role);
        String sql = "SELECT COUNT(*) FROM UserAccount " +
                    "WHERE profileId = ? AND DATE(accountCreated) BETWEEN ? AND ?";

        LocalDate startDate = LocalDate.of(1999, 12, 12);
        LocalDate endDate = pointDate.plusDays(6);

        Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            profileId,
            Date.valueOf(startDate),
            Date.valueOf(endDate)
        );

        return count != null ? count : 0;
    }

        public int getMonthlyAccountsUpToPoint(LocalDate pointDate, String role) {
        int profileId = userProfileC.getProfileIdByName(role);   
        String sql = "SELECT COUNT(*) FROM UserAccount " +
                    "WHERE profileId = ? AND DATE(accountCreated) BETWEEN ? AND ?";

        LocalDate startDate = LocalDate.of(1999, 12, 12);
        LocalDate endDate = pointDate.plusMonths(1);

        Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            profileId,
            Date.valueOf(startDate),
            Date.valueOf(endDate)
        );

        return count != null ? count : 0;
    }


    public int getNewShortlists (String range) {
        Integer result;
        switch (range) {
            case "daily":
                result =  jdbcTemplate.queryForObject(GET_DAILY_SHORTLISTS, Integer.class);
            case "weekly":
                result = jdbcTemplate.queryForObject(GET_WEEKLY_SHORTLISTS, Integer.class);
            case "monthly":
                result = jdbcTemplate.queryForObject(GET_MONTHLY_SHORTLISTS, Integer.class);

            return result != null ? result : 0;                                                                          
        }
        return -1;  
        // Execute query with JDBC
    }

    public int getNewBookings (String range) {
        Integer result;
        switch (range) {
            case "daily":
                result = jdbcTemplate.queryForObject(GET_DAILY_BOOKINGS, Integer.class);
            case "weekly":
                result = jdbcTemplate.queryForObject(GET_WEEKLY_BOOKINGS, Integer.class);
            case "monthly":
                result = jdbcTemplate.queryForObject(GET_MONTHLY_BOOKINGS, Integer.class);   
                
            return result != null ? result : 0;
        }
        return -1;  
        // Execute query with JDBC
    }

//Generate Here
    public Report generateDailyReport() {
        int new_homeowners = getNewAccounts("Home Owner", "daily");
        int new_cleaners = getNewAccounts("Cleaner", "daily");

        int total_home_owners = getAccountsUpToPoint("Home Owner");
        int total_cleaners = getAccountsUpToPoint("Cleaner");

        int new_shortlists = getNewShortlists("daily");
        int new_bookings = getNewBookings("daily");
            
        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "DAILY", generatedDate, 
                new_homeowners, total_home_owners, new_cleaners, total_cleaners, new_shortlists, new_bookings);
        
        if (rows > 0) {
            return new Report(
                "DAILY",
                generatedDate.toLocalDate(),
                new_homeowners,
                total_home_owners,
                new_cleaners,
                total_cleaners,
                new_shortlists,
                new_bookings
            );
        } else {
            return null;
        }    
    }

    public Report generateWeeklyReport() {
        int new_homeowners = getNewAccounts("Home Owner", "weekly");
        int new_cleaners = getNewAccounts("Cleaner", "weekly");

        int total_home_owners = getWeeklyAccountsUpToPoint(LocalDate.now(), "Home Owner");
        int total_cleaners = getWeeklyAccountsUpToPoint(LocalDate.now(), "Cleaner");

        int new_shortlists = getNewShortlists("weekly");
        int new_bookings = getNewBookings("weekly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "WEEKLY", generatedDate, 
                new_homeowners, total_home_owners, new_cleaners, total_cleaners, new_shortlists, new_bookings);

        if (rows > 0) {
            return new Report(
                "WEEKLY",
                generatedDate.toLocalDate(),
                new_homeowners,
                total_home_owners,
                new_cleaners,
                total_cleaners,
                new_shortlists,
                new_bookings
            );
        } else {
            return null;
        }    
    }
    
    public Report generateWeeklyReport(LocalDate date) {
        int new_homeowners = getNewAccounts("Home Owner", "weekly");
        int new_cleaners = getNewAccounts("Cleaner", "weekly");

        int total_home_owners = getWeeklyAccountsUpToPoint(date, "Home Owner");
        int total_cleaners = getWeeklyAccountsUpToPoint(date, "Cleaner");

        int new_shortlists = getNewShortlists("weekly");
        int new_bookings = getNewBookings("weekly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "WEEKLY", generatedDate, 
                new_homeowners, total_home_owners, new_cleaners, total_cleaners, new_shortlists, new_bookings);

        if (rows > 0) {
            return new Report(
                "WEEKLY",
                generatedDate.toLocalDate(),
                new_homeowners,
                total_home_owners,
                new_cleaners,
                total_cleaners,
                new_shortlists,
                new_bookings
            );
        } else {
            return null;
        }    
    }

    public Report generateMonthlyReport() {
        int new_homeowners = getNewAccounts("Home Owner", "monthly");
        int new_cleaners = getNewAccounts("Cleaner", "monthly");

        int total_home_owners = getMonthlyAccountsUpToPoint(LocalDate.now(),"Home Owner");
        int total_cleaners = getMonthlyAccountsUpToPoint(LocalDate.now(), "Cleaner");

        int new_shortlists = getNewShortlists("monthly");
        int new_bookings = getNewBookings("monthly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "MONTHLY", generatedDate, 
                new_homeowners, total_home_owners, new_cleaners, total_cleaners, new_shortlists, new_bookings);

        if (rows > 0) {
            return new Report(
                "MONTHLY",
                generatedDate.toLocalDate(),
                new_homeowners,
                total_home_owners,
                new_cleaners,
                total_cleaners,
                new_shortlists,
                new_bookings
            );
        } else {
            return null;
        }    
    }

    public Report generateMonthlyReport(LocalDate date) {
        int new_homeowners = getNewAccounts("Home Owner", "monthly");
        int new_cleaners = getNewAccounts("Cleaner", "monthly");

        int total_home_owners = getMonthlyAccountsUpToPoint(date,"Home Owner");
        int total_cleaners = getMonthlyAccountsUpToPoint(date, "Cleaner");

        int new_shortlists = getNewShortlists("monthly");
        int new_bookings = getNewBookings("monthly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "MONTHLY", generatedDate, 
                new_homeowners, total_home_owners, new_cleaners, total_cleaners, new_shortlists, new_bookings);

        if (rows > 0) {
            return new Report(
                "MONTHLY",
                generatedDate.toLocalDate(),
                new_homeowners,
                total_home_owners,
                new_cleaners,
                total_cleaners,
                new_shortlists,
                new_bookings
            );
        } else {
            return null;
        }    
    }
}
