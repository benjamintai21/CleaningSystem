package com.cleaningsystem.db;

public class Queries {
    //Login
    public static final String LOGIN = "SELECT * FROM USERACCOUNT WHERE username = ? AND password = ? AND profileId = ?";

    //User Admin
    public static final String CREATE_USER_ACCOUNT = "INSERT INTO USERACCOUNT (name, age, dob, gender, address, email, username, password, profileId, accountCreated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    
    public static final String GET_USER_ACCOUNT_BY_ID = "SELECT * FROM USERACCOUNT WHERE UId = ?";

    public static final String GET_USER_ACCOUNT_BY_USERNAME = "SELECT * FROM USERACCOUNT WHERE username = ?";

    public static final String UPDATE_USER_ACCOUNT = "UPDATE USERACCOUNT SET name = ?, age = ?, dob = ?, gender = ?, address = ?, email = ?, username = ?, password = ?, profileId = ? WHERE UId = ?";
    
    public static final String SET_ACCOUNT_SUSPENSION_STATUS = "UPDATE USERACCOUNT SET suspended = ? WHERE UId = ?";

    public static final String SEARCH_USER_ACCOUNT_BY_USERNAME_OR_NAME_OR_ROLE = "SELECT ua.*, up.profilename FROM USERACCOUNT ua JOIN USERPROFILE up ON ua.profileID = up.profileID WHERE ua.username LIKE ? OR ua.name LIKE ? OR up.profilename LIKE ?";

    public static final String SEARCH_USER_ACCOUNT_BY_PROFILEID = "SELECT * FROM USERACCOUNT WHERE profileId = ?";

    public static final String GET_ALL_USER_ACCOUNT = "SELECT * FROM USERACCOUNT";

    public static final String CHECK_USER_ACCOUNT = "SELECT up.profilename FROM USERPROFILE up JOIN USERACCOUNT ua ON up.profileId = ua.profileId WHERE ua.UId = ?";

    //User Profile
    public static final String CREATE_USER_PROFILE = "INSERT INTO USERPROFILE (profilename, description, suspension) VALUES (?, ?, ?)";

    public static final String GET_USER_PROFILE_BY_ID = "SELECT * FROM USERPROFILE WHERE profileId = ?";

    public static final String GET_PROFILE_ID_BY_NAME = "SELECT profileId FROM USERPROFILE WHERE profilename = ?";

    public static final String GET_NEXT_PROFILE = "SELECT * FROM USERPROFILE WHERE profileId = (SELECT MAX(profileId) FROM USERPROFILE)";

    public static final String UPDATE_USER_PROFILE = "UPDATE USERPROFILE SET profilename = ?, description = ?, suspension = ? WHERE profileId = ?"; 

    public static final String SET_PROFILE_SUSPENSION_STATUS = "UPDATE USERPROFILE SET suspension = ? WHERE profileId = ?";

    public static final String SEARCH_PROFILE_BY_NAME = "SELECT * FROM USERPROFILE WHERE profilename LIKE ?";

    public static final String GET_ALL_PROFILE_NAMES = "SELECT profilename FROM USERPROFILE";

    public static final String GET_ALL_PROFILES = "SELECT * FROM USERPROFILE";
    
    //Cleaner
    public static final String CREATE_SERVICE_LISTING = "INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String GET_SERVICE_LISTING_BY_ID = "SELECT * FROM SERVICELISTINGS WHERE serviceId = ? and cleanerId = ?";
    
    public static final String UPDATE_SERVICE_LISTING = "UPDATE SERVICELISTINGS SET name = ?, cleanerId = ?, categoryId = ?, description = ?, price_per_hour = ?, startDate = ?, endDate = ?, status = ? WHERE serviceId = ?";
    
    public static final String DELETE_SERVICE_LISTING = "DELETE FROM SERVICELISTINGS WHERE serviceId = ?";

    public static final String DELETE_ALL_SERVICE_LISTING_BY_CATEGORY = "DELETE FROM SERVICELISTINGS WHERE categoryId = ?";
    
    public static final String SEARCH_MY_SERVICE_LISTING_BY_ID = "SELECT * FROM SERVICELISTINGS WHERE cleanerId = ? and name LIKE ?";

    public static final String GET_ALL_SERVICE_LISTINGS_BY_ID = "SELECT * FROM SERVICELISTINGS WHERE cleanerId = ?";
   
    public static final String GET_NO_OF_VIEWS = "SELECT views FROM SERVICELISTINGS where cleanerId = ?";

    public static final String GET_SERVICE_LISTING_BY_CATEGORY = "SELECT * FROM SERVICELISTINGS WHERE categoryId = ?";

    public static final String GET_MAX_SERVICELISTING = "SELECT * FROM SERVICELISTINGS WHERE serviceId = (SELECT MAX(serviceId) FROM SERVICELISTINGS)";

    public static final String GET_ALL_SERVICE_LISTINGS = "SELECT * FROM SERVICELISTINGS";

    //Service Categories
    public static final String CREATE_SERVICE_CATEGORIES = "INSERT INTO SERVICECATEGORIES (type, name, description) VALUES (?, ?, ?)";

    public static final String GET_SERVICE_CATEGORY_BY_ID = "SELECT * FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String GET_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name = ?";

    public static final String UPDATE_SERVICE_CATEGORY = "UPDATE SERVICECATEGORIES SET type = ?, name = ?, description = ? WHERE categoryId = ?";

    public static final String DELETE_SERVICE_CATEGORY = "DELETE FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String SEARCH_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name LIKE ?";

    public static final String GET_ALL_SERVICE_CATEGORIES = "SELECT * FROM SERVICECATEGORIES";

    public static final String DELETE_SERVICE_LISTING_BY_CATEGORY = "DELETE FROM SERVICELISTINGS WHERE categoryId = ?";

    public static final String DELETE_SHORTLISTED_SERVICES_BY_CATEGORY = "DELETE ss FROM SHORTLISTEDSERVICES ss JOIN SERVICELISTINGS s ON ss.serviceId = s.serviceId WHERE s.categoryId = ?";

    public static final String DELETE_BOOKING_BY_CATEGORY = "DELETE b FROM BOOKING b JOIN SERVICELISTINGS s ON b.serviceId = s.serviceId WHERE s.categoryId = ?";
    
    //HomeOwner
    public static final String SEARCH_SERVICE_LISTING_BY_SERVICE = "SELECT * FROM SERVICELISTINGS WHERE name LIKE ?";
    
    public static final String SEARCH_SERVICE_LISTING_BY_CLEANER = "SELECT s.* FROM SERVICELISTINGS s JOIN USERACCOUNT u ON s.cleanerId = u.UId WHERE u.name LIKE ?";

    public static final String VIEW_SERVICE_LISTING_BY_SERVICE_ID = "SELECT * FROM SERVICELISTINGS WHERE serviceId = ?";
    
    public static final String SAVE_SHORTLISTED_SERVICE = "INSERT INTO SHORTLISTEDSERVICES (homeownerId, serviceId) VALUES (?, ?)";

    public static final String SAVE_SHORTLISTED_CLEANER = "INSERT INTO SHORTLISTEDCLEANERS (homeownerId, cleanerId) VALUES (?, ?)";

    public static final String GET_ALL_SHORTLISTED_SERVICES = "SELECT * FROM SHORTLISTEDSERVICES WHERE homeownerId = ?";

    public static final String GET_ALL_SHORTLISTED_CLEANERS = "SELECT * FROM SHORTLISTEDCLEANERS WHERE homeownerId = ?";

    public static final String SEARCH_SHORTLISTED_CLEANER_BY_USERNAME = "SELECT sc.* FROM SHORTLISTEDCLEANERS sc JOIN USERACCOUNT ua ON ua.UId = sc.cleanerId WHERE sc.homeownerId = ? AND ua.username LIKE ?";

    public static final String CHECK_SHORTLISTED_SERVICES = "SELECT * FROM SHORTLISTEDSERVICES WHERE serviceId = ? AND homeownerId = ?";

    public static final String CHECK_SHORTLISTED_CLEANERS = "SELECT * FROM SHORTLISTEDCLEANERS WHERE cleanerId = ? AND homeownerId = ?";

    public  static final String DELETE_SHORTLISTED_SERVICES = "DELETE FROM SHORTLISTEDSERVICES WHERE homeownerId = ? AND serviceId = ?";
    
    public  static final String DELETE_SHORTLISTED_CLEANERS = "DELETE FROM SHORTLISTEDCLEANERS WHERE homeownerId = ? AND cleanerId = ?";

    public static final String SEARCH_SHORTLISTED_SERVICE_BY_NAME = "SELECT ss.* FROM SHORTLISTEDSERVICES ss JOIN SERVICELISTINGS sl ON ss.serviceId = sl.serviceId WHERE ss.homeownerId = ? AND sl.name LIKE ?";

    public static final String UPDATE_VIEWS = "UPDATE SERVICELISTINGS SET views = views + 1 WHERE serviceId = ?";

    public static final String UPDATE_SHORTLISTING = "UPDATE SERVICELISTINGS SET shortlists = shortlists + 1 WHERE serviceId = ?";

    //Booking
    public static final String CREATE_BOOKING = "INSERT INTO BOOKING (serviceId, homeownerId, status) VALUES (?, ?, ?)";

    public static final String GET_ALL_BOOKINGS_BY_HOMEOWNER = "SELECT * FROM BOOKING WHERE homeownerId = ?";
    
    public static final String SEARCH_HOMEOWNER_BOOKINGS = "SELECT b.* FROM BOOKING b JOIN SERVICELISTINGS sl ON b.serviceId = sl.serviceId WHERE b.homeownerId = ? AND sl.name LIKE ?";

    public static final String GET_CONFIRMED_MATCHES = "SELECT bh.*, sl.name FROM BOOKING bh JOIN SERVICELISTINGS sl ON bh.serviceId = sl.serviceId WHERE sl.cleanerId = ?";

    public static final String SEARCH_CONFIRMED_MATCHES = "SELECT bh.* FROM BOOKING bh JOIN SERVICELISTINGS sl ON bh.serviceId = sl.serviceId WHERE sl.cleanerId = ? AND sl.name LIKE ?";

    //New Account Created
    public static final String GET_DAILY_CREATED = "SELECT COUNT(*) FROM UserAccount WHERE profileId = ? AND accountCreated >= ? AND accountCreated < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_WEEKLY_CREATED = "SELECT COUNT(*) FROM UserAccount WHERE profileId = ? AND accountCreated >= DATE_SUB(?, INTERVAL 6 DAY) AND accountCreated < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_MONTHLY_CREATED = "SELECT COUNT(*) FROM UserAccount WHERE profileId = ? AND MONTH(accountCreated) = MONTH(?) AND YEAR(accountCreated) = YEAR(?)";
    
    //Total Account Created
    public static final String GET_TOTAL_CREATED = "SELECT COUNT(*) FROM UserAccount WHERE profileId = ?";

    //New Shortlists Created
    public static final String GET_DAILY_SHORTLISTS = "SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE dateAdded >= ? AND dateAdded < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_WEEKLY_SHORTLISTS = "SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE dateAdded >= DATE_SUB(?, INTERVAL 6 DAY) AND dateAdded < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_MONTHLY_SHORTLISTS = "SELECT COUNT(*) FROM SHORTLISTEDSERVICES WHERE MONTH(dateAdded) = MONTH(?) AND YEAR(dateAdded) = YEAR(?)";

    public static final String CREATE_REPORT = "INSERT INTO REPORT (type,date,new_homeOwners,total_homeOwners,new_cleaners,total_cleaners,total_shortlists,total_bookings) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    //New Booking Created
    public static final String GET_DAILY_BOOKINGS = "SELECT COUNT(*) FROM BOOKING WHERE dateAdded >= ? AND dateAdded < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_WEEKLY_BOOKINGS = "SELECT COUNT(*) FROM BOOKING WHERE dateAdded >= DATE_SUB(?, INTERVAL 6 DAY) AND dateAdded < DATE_ADD(?, INTERVAL 1 DAY)";

    public static final String GET_MONTHLY_BOOKINGS = "SELECT COUNT(*) FROM BOOKING WHERE MONTH(dateAdded) = MONTH(?) AND YEAR(dateAdded) = YEAR(?)";

}         

