package com.cleaningsystem.dao;

public class Queries {
    //Login
    public static final String LOGIN = "SELECT * FROM USERACCOUNT WHERE username = ? AND password = ?";

    //User Admin
    public static final String CREATE_USER_ACCOUNT = "INSERT INTO USERACCOUNT (name, age, dob, gender, address, email, username, password, profileId, accountCreated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    
    public static final String GET_USER_ACCOUNT_BY_ID = "SELECT * FROM USERACCOUNT WHERE UID = ?";

    public static final String GET_USER_ACCOUNT_BY_USERNAME = "SELECT * FROM USERACCOUNT WHERE username = ?";

    public static final String UPDATE_USER_ACCOUNT = "UPDATE USERACCOUNT SET name = ?, age = ?, dob = ?, gender = ?, address = ?, email = ?, username = ?, password = ?, profileID = ? WHERE UID = ?";
    
    public static final String DELETE_USER_ACCOUNT = "DELETE FROM USERACCOUNT WHERE UID = ?";

    public static final String SEARCH_USER_ACCOUNT_BY_USERNAME = "SELECT * FROM USERACCOUNT WHERE username LIKE ?";

    public static final String SEARCH_USER_ACCOUNT_BY_PROFILEID = "SELECT * FROM USERACCOUNT WHERE profileID = ?";

    public static final String GET_ALL_USER_ACCOUNT = "SELECT * FROM USERACCOUNT";

    //User Profile
    public static final String CREATE_USER_PROFILE = "INSERT INTO USERPROFILE (profilename, description, suspension) VALUES (?, ?, ?)";

    public static final String GET_USER_PROFILE_BY_ID = "SELECT * FROM USERPROFILE WHERE profileID = ?";

    public static final String GET_PROFILE_ID_BY_NAME = "SELECT profileID FROM USERPROFILE WHERE profilename = ?";

    public static final String GET_PROFILE_NAMES = "SELECT profilename FROM db_cleaningsystem.userprofile ORDER BY profileID";

    public static final String UPDATE_USER_PROFILE = "UPDATE USERPROFILE SET profilename = ?, description = ?, suspension = ? WHERE profileID = ?"; 

    public static final String SET_SUSPENSION_STATUS = "UPDATE USERPROFILE SET suspension = ? WHERE profileID = ?";

    public static final String SEARCH_PROFILE_BY_NAME = "SELECT * FROM USERPROFILE WHERE profilename LIKE ?";

    public static final String GET_ALL_PROFILES = "SELECT * FROM USERPROFILE";
    
    //Cleaner
    public static final String CREATE_SERVICE_LISTING = "INSERT INTO SERVICELISTINGS (name, cleanerID, categoryID, description, price_per_hour, startDate, endDate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String GET_SERVICE_LISTING_BY_ID = "SELECT * FROM SERVICELISTINGS WHERE serviceId = ? and cleanerId = ?";
    
    public static final String UPDATE_SERVICE_LISTING = "UPDATE SERVICELISTINGS SET name = ?, cleanerId = ?, categoryId = ?, description = ?, price_per_hour = ?, status = ? WHERE serviceId = ?";
    
    public static final String DELETE_SERVICE_LISTING = "DELETE FROM SERVICELISTINGS WHERE serviceId = ?";

    public static final String SEARCH_MY_SERVICE_LISTING = "SELECT * FROM SERVICE LISTING WHERE WHERE cleanerId = ? and name LIKE ?";

    public static final String GET_ALL_SERVICE_LISTINGS = "SELECT * FROM SERVICELISTINGS";

    public static final String GET_NO_OF_SHORTLISTS = "SELECT shortlists FROM SERVICELISTINGS WHERE cleanerId = ?";
   
    public static final String GET_NO_OF_VIEWS = "SELECT views FROM SERVICELISTINGS where cleanerId = ?";

    //Service Categories
    public static final String CREATE_SERVICE_CATEGORIES = "INSERT INTO SERVICECATEGORIES (type, name, description) VALUES (?, ?, ?)";

    public static final String GET_SERVICE_CATEGORY_BY_ID = "SELECT * FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String GET_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name = ?";

    public static final String UPDATE_SERVICE_CATEGORY = "UPDATE SERVICECATEGORIES SET type = ?, name = ?, description = ? WHERE categoryId = ?";

    public static final String DELETE_SERVICE_CATEGORY = "DELETE FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String SEARCH_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name LIKE ?";

    public static final String GET_ALL_SERVICE_CATEGORIES = "SELECT * FROM SERVICECATEGORIES";
    
    //HomeOwner
    public static final String SEARCH_SERVICE_LISTING_BY_SERVICE = "SELECT * FROM SERVICELISTINGS WHERE name = ?";
    
    public static final String SEARCH_SERVICE_LISTING_BY_CLEANER = "SELECT s.* FROM SERVICELISTINGS s JOIN USERACCOUNT u ON s.cleanerId = u.UID WHERE u.profileID = 4 AND u.name LIKE ?";

    public static final String SAVE_SHORTLISTED_SERVICE = "INSERT INTO SHORTLISTEDSERVICES (homeownerUID, serviceId) VALUES (?, ?)";

    public static final String GET_SHORTLISTED_SERVICES = "SELECT * FROM SHORTLISTEDSERVICES WHERE homeownerUID = ?";

    public  static final String DELETE_SHORTLISTED_SERVICE = "DELETE FROM SHORTLISTEDSERVICES WHERE homeownerUID = ? AND serviceId = ?";

    public static final String SEARCH_SHORTLISTED_SERVICE_BY_NAME = "SELECT sl.* FROM SHORTLISTEDSERVICES ss JOIN SERVICELISTINGS sl ON ss.serviceId = sl.serviceId WHERE ss.homeownerUID = ? AND sl.name LIKE ?";

    //Booking History
    public static final String GET_COMPLETED_SERVICES = "SELECT * FROM BOOKINGY WHERE status = 'completed' AND homeownerId = ?";
    
    public static final String SEARCH_PAST_BOOKINGS = "SELECT bh.*, sl.name FROM BOOKING bh JOIN SERVICELISTINGS sl ON bh.serviceId = sl.serviceId WHERE bh.status = 'completed' AND bh.homeownerId = ? AND sl.name LIKE ?";  
}         

