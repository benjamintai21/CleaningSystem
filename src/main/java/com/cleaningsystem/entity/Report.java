package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cleaningsystem.entity.Report;

import static com.cleaningsystem.db.Queries.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;

@Component
public class Report {
	private int reportId;
	private String type;
    private LocalDate date;
    private int new_homeOwners;
    private int total_homeOwners;
    private int new_cleaners;
    private int total_cleaners;
    private int no_shortlists;
    private int no_bookings;

	public Report() {}

	public Report(String type, LocalDate date, int new_homeOwners, int total_homeOwners, int new_cleaners, int total_cleaners, int no_shortlists, int no_bookings) {
		this.type = type;
        this.date = date;
        this.new_homeOwners = new_homeOwners;
        this.total_homeOwners = total_homeOwners;
        this.new_cleaners = new_cleaners;
        this.total_cleaners = total_cleaners;
        this.no_shortlists = no_shortlists;
        this.no_bookings = no_bookings;
	}

	// Getters
	public int getReportId() { return reportId;}
	public String getType() {return type;}
    public LocalDate getDate() {return date;}
    
    public int getNewHomeOwners() {return new_homeOwners;}
    public int getTotalHomeOwners() {return total_homeOwners;}
    public int getNewCleaners() {return new_cleaners;}
    public int getTotalCleaners() {return total_cleaners;}
    public int getNoOfShortlists() {return no_shortlists;}
    public int getNoOfBookings() {return no_bookings;}

	//Setters
	public void setReportId(int new_reportId) { new_reportId = reportId;}
	public void setType(String new_type) {new_type = type;}
    public void setDate(LocalDate new_date) {new_date = date;}

    public void setNewHomeOwners(int new_new_homeOwners) {new_new_homeOwners = new_homeOwners;}
    public void setTotalHomeOwners(int new_total_homeOwners) {new_total_homeOwners = total_homeOwners;} 
    public void setNewCleaner(int new_new_cleaners) {new_new_cleaners = new_cleaners;} 
    public void setTotalCleaner(int new_total_cleaners) {new_total_cleaners = total_cleaners;} 
    public void setNoOfShortlists(int new_total_shortlists) {new_total_shortlists = no_shortlists;} 
    public void setNoOfBookings(int new_total_bookings) {new_total_bookings = no_bookings;}

    // Databases Stuff
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserProfile userProfile;

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
        int profileId = userProfile.getProfileIdByName(role);
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
        int profileId = userProfile.getProfileIdByName(role);   
        return jdbcTemplate.queryForObject(GET_TOTAL_CREATED, Integer.class, profileId);
    }

    public int getWeeklyAccountsUpToPoint(LocalDate pointDate, String role) {
        int profileId = userProfile.getProfileIdByName(role);
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
        int profileId = userProfile.getProfileIdByName(role);   
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
