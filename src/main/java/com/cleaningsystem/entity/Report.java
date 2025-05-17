package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cleaningsystem.entity.Report;

import static com.cleaningsystem.db.Queries.*;

import java.sql.Date;
// import java.sql.ResultSet;
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

    // private final RowMapper<Report> listingRowMapper = (ResultSet rs, int rowNum) -> {
    //     Report report = new Report();
    //     report.setReportId(rs.getInt("reportId")); 
	//     report.setType(rs.getString("type")); 
    //     report.setDate(rs.getDate("date").toLocalDate()); 
    //     report.setNewHomeOwners(rs.getInt("new_homeOwners"));
    //     report.setTotalHomeOwners(rs.getInt("total_homeOwners")); 
    //     report.setNewCleaner(rs.getInt("new_cleaners"));
    //     report.setTotalCleaner(rs.getInt("total_cleaners"));
    //     report.setNoOfShortlists(rs.getInt("total_shortlists"));
    //     report.setNoOfBookings(rs.getInt("total_bookings"));

    //     return report;
    // };
    
    public int getNewAccounts(LocalDate date, String role, String range) {
        int profileId = userProfile.getProfileIdByName(role);
        Integer result = switch (range) {
            case "daily" ->
                jdbcTemplate.queryForObject(
                    GET_DAILY_CREATED, Integer.class, 
                    profileId, 
                    Date.valueOf(date), 
                    Date.valueOf(date)
                );
            case "weekly" -> 
                jdbcTemplate.queryForObject(
                    GET_WEEKLY_CREATED, Integer.class, 
                    profileId, 
                    Date.valueOf(date), 
                    Date.valueOf(date)
                );
            case "monthly" ->
                jdbcTemplate.queryForObject(
                    GET_MONTHLY_CREATED, Integer.class, 
                    profileId, 
                    Date.valueOf(date), 
                    Date.valueOf(date)
                );
            default -> null;
        };
    
        return result != null ? result : 0;
    }
    

    public int getAccountsUpToPoint(String role){
        int profileId = userProfile.getProfileIdByName(role);   
        return jdbcTemplate.queryForObject(GET_TOTAL_CREATED, Integer.class, profileId);
    }

    public int getAccountsUpToPoint(LocalDate date, String role){
        int profileId = userProfile.getProfileIdByName(role);
        String sql = "SELECT COUNT(*) FROM UserAccount " +
                    "WHERE profileId = ? AND DATE(accountCreated) BETWEEN ? AND ?";

        LocalDate startDate = LocalDate.of(1999, 12, 12);

        Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            profileId,
            Date.valueOf(startDate),
            Date.valueOf(date)
        );

        return count != null ? count : 0;
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


    public int getNewShortlists(LocalDate date, String range) {
        Integer result = switch (range) {
                case "daily" -> jdbcTemplate.queryForObject(GET_DAILY_SHORTLISTS, Integer.class, Date.valueOf(date), Date.valueOf(date));
                case "weekly" -> jdbcTemplate.queryForObject(GET_WEEKLY_SHORTLISTS, Integer.class, Date.valueOf(date), Date.valueOf(date));
                case "monthly" -> jdbcTemplate.queryForObject(GET_MONTHLY_SHORTLISTS, Integer.class, Date.valueOf(date), Date.valueOf(date));
                default -> 0;
            };
        return result != null ? result : 0;
    }

    public int getNewBookings(LocalDate date, String range) {
        Integer result = switch (range) {
            case "daily" -> jdbcTemplate.queryForObject(GET_DAILY_BOOKINGS, Integer.class, Date.valueOf(date), Date.valueOf(date));
            case "weekly" -> jdbcTemplate.queryForObject(GET_WEEKLY_BOOKINGS, Integer.class, Date.valueOf(date), Date.valueOf(date));
            case "monthly" -> jdbcTemplate.queryForObject(GET_MONTHLY_BOOKINGS, Integer.class, Date.valueOf(date), Date.valueOf(date));
            default -> null;
        };
    
        return result != null ? result : 0;
    }
    

    //Generate Herde

    public Report generateDailyReport(LocalDate date) {
        int n_homeowners = getNewAccounts(date, "Home Owner", "daily");
        int n_cleaners = getNewAccounts(date, "Cleaner", "daily");

        int t_home_owners = getAccountsUpToPoint(date, "Home Owner");
        int t_cleaners = getAccountsUpToPoint(date, "Cleaner");

        int n_shortlists = getNewShortlists(date, "daily");
        int n_bookings = getNewBookings(date, "daily");
            
        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "DAILY", generatedDate, 
                n_homeowners, t_home_owners, n_cleaners, t_cleaners, n_shortlists, n_bookings);
        
        if (rows > 0) {
            return new Report(
                "DAILY",
                generatedDate.toLocalDate(),
                n_homeowners,
                t_home_owners,
                n_cleaners,
                t_cleaners,
                n_shortlists,
                n_bookings
            );
        } else {
            return null;
        }    
    }

    
    public Report generateWeeklyReport(LocalDate date) {
        int n_homeowners = getNewAccounts(date, "Home Owner", "weekly");
        int n_cleaners = getNewAccounts(date, "Cleaner", "weekly");

        int t_home_owners = getWeeklyAccountsUpToPoint(date, "Home Owner");
        int t_cleaners = getWeeklyAccountsUpToPoint(date, "Cleaner");

        int n_shortlists = getNewShortlists(date, "weekly");
        int n_bookings = getNewBookings(date, "weekly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "WEEKLY", generatedDate, 
                n_homeowners, t_home_owners, n_cleaners, t_cleaners, n_shortlists, n_bookings);

        if (rows > 0) {
            return new Report(
                "WEEKLY",
                generatedDate.toLocalDate(),
                n_homeowners,
                t_home_owners,
                n_cleaners,
                t_cleaners,
                n_shortlists,
                n_bookings
            );
        } else {
            return null;
        }    
    }

    public Report generateMonthlyReport(LocalDate date) {
        int n_homeowners = getNewAccounts(date, "Home Owner", "monthly");
        int n_cleaners = getNewAccounts(date, "Cleaner", "monthly");

        int t_home_owners = getMonthlyAccountsUpToPoint(date,"Home Owner");
        int t_cleaners = getMonthlyAccountsUpToPoint(date, "Cleaner");

        int n_shortlists = getNewShortlists(date, "monthly");
        int n_bookings = getNewBookings(date, "monthly");

        Date generatedDate = new Date(System.currentTimeMillis());

        int rows = jdbcTemplate.update(CREATE_REPORT, "MONTHLY", generatedDate, 
                n_homeowners, t_home_owners, n_cleaners, t_cleaners, n_shortlists, n_bookings);

        if (rows > 0) {
            return new Report(
                "MONTHLY",
                generatedDate.toLocalDate(),
                n_homeowners,
                t_home_owners,
                n_cleaners,
                t_cleaners,
                n_shortlists,
                n_bookings
            );
        } else {
            return null;
        }    
    }    
}
