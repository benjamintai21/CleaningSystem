package com.cleaningsystem.dao;

public class Queries {
    //Login
    public static final String LOGIN = "SELECT * FROM USERACCOUNT WHERE username = ? AND password = ?";

    //User Admin
    public static final String CREATE_USER_ACCOUNT = "INSERT INTO USERACCOUNT (name, age, dob, gender, address, email, username, password, profileId, accountCreated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    
    public static final String GET_USER_ACCOUNT_BY_ID = "SELECT * FROM USERACCOUNT WHERE UID = ?";

    public static final String GET_USER_ACCOUNT_BY_USERNAME = "SELECT * FROM USERACCOUNT WHERE username = ?";

    public static final String UPDATE_USER_ACCOUNT = "UPDATE USERACCOUNT SET name = ?, age = ?, dob = ?, gender = ?, address = ?, email = ?, username = ?, profileID = ? WHERE UID = ?";
    
    public static final String DELETE_USER_ACCOUNT = "DELETE FROM USERACCOUNT WHERE UID = ?";

    public static final String SEARCH_USER_ACCOUNT_BY_USERNAME = "SELECT * FROM USERACCOUNT WHERE username LIKE ?";

    public static final String GET_ALL_USER_ACCOUNT = "SELECT * FROM USERACCOUNT";

    //User Profile
    public static final String CREATE_USER_PROFILE = "INSERT INTO USERPROFILE (profilename, description, suspension) VALUES (?, ?, ?)";

    public static final String GET_USER_PROFILE_BY_ID = "SELECT * FROM USERPROFILE WHERE profileID = ?";

    public static final String GET_PROFILE_ID_BY_NAME = "SELECT profileID FROM USERPROFILE WHERE profilename = ?";

    public static final String UPDATE_USER_PROFILE = "UPDATE USERPROFILE SET profilename = ?, description = ?, suspension = ? WHERE profileID = ?"; 

    public static final String SET_SUSPENSION_STATUS = "UPDATE USERPROFILE SET suspension = ? WHERE profileID = ?";

    public static final String SEARCH_PROFILE_BY_NAME = "SELECT * FROM USERPROFILE WHERE profilename LIKE ?";

    public static final String GET_ALL_PROFILES = "SELECT * FROM USERPROFILE";
    
    //Service Listing
    public static final String CREATE_SERVICE_LISTING = "INSERT INTO SERVICELISTINGS (name, cleanerID, categoryID, description, price_per_hour, startDate, endDate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String GET_SERVICE_LISTING_BY_ID = "SELECT * FROM SERVICELISTINGS WHERE serviceId = ?";

    public static final String GET_SERVICE_LISTING_BY_CLEANER_ID = "SELECT * FROM SERVICELISTINGS WHERE cleanerId = ?";
    
    public static final String UPDATE_SERVICE_LISTING = "UPDATE SERVICELISTINGS SET name = ?, category = ?, description = ?, price_per_hour = ?, status = ? WHERE serviceId = ?";
    
    public static final String DELETE_SERVICE_LISTING = "DELETE FROM SERVICELISTINGS WHERE serviceId = ?";
    
    public static final String SEARCH_SERVICE_LISTING_BY_CLEANER_AND_KEYWORD = "SELECT * FROM SERVICELISTINGS WHERE cleanerId = ? AND name LIKE ?";

    public static final String GET_ALL_SERVICE_LISTINGS = "SELECT * FROM SERVICELISTINGS";

    //Service Categories
    public static final String CREATE_SERVICE_CATEGORIES = "INSERT INTO SERVICECATEGORIES (type, name, description) VALUES (?, ?, ?)";

    public static final String GET_SERVICE_CATEGORY_BY_ID = "SELECT * FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String GET_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name = ?";

    public static final String UPDATE_SERVICE_CATEGORY = "UPDATE SERVICECATEGORIES SET type = ?, name = ?, description = ? WHERE categoryId = ?";

    public static final String DELETE_SERVICE_CATEGORY = "DELETE FROM SERVICECATEGORIES WHERE categoryId = ?";

    public static final String SEARCH_SERVICE_CATEGORY_BY_NAME = "SELECT * FROM SERVICECATEGORIES WHERE name LIKE ?";

    public static final String GET_ALL_SERVICE_CATEGORIES = "SELECT * FROM SERVICECATEGORIES";
    
}
