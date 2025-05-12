DROP DATABASE IF EXISTS DB_CLEANINGSYSTEM;

CREATE DATABASE DB_CLEANINGSYSTEM;

USE DB_CLEANINGSYSTEM;

CREATE TABLE USERPROFILE (
    profileId int not null auto_increment,
    profilename varchar(32) not null,
    description varchar(512) not null,
    suspension bool not null,

    PRIMARY KEY (profileId),
    CONSTRAINT unique_profilename UNIQUE (profilename)
);

CREATE TABLE USERACCOUNT (
    UId int not null auto_increment, 
    name varchar(50) not null,
    age int not null,
    dob date not null,
    gender varchar(20) not null,
    address varchar(128) not null, 
    email varchar(128) not null,
    username varchar(32) not null,
    password varchar(32) not null,
    profileID int not null,
    accountCreated date not null,
    suspended bool default false,

    PRIMARY KEY(UId),
    CONSTRAINT unique_username UNIQUE (username),
    
    FOREIGN KEY (profileID) REFERENCES USERPROFILE(profileID)
);

CREATE TABLE SERVICECATEGORIES (
	categoryId int not null auto_increment,
    type varchar(50),
    name varchar(50),
    description varchar(512),
    
    PRIMARY KEY(categoryId)
);

CREATE TABLE SERVICELISTINGS (
    serviceId int not null auto_increment,
    name varchar(50) not null,
	cleanerId int not null,
    categoryId int not null,
    description varchar(512) not null,
	price_per_hour DECIMAL(10, 2) NOT NULL,
    startDate date not null,
    endDate date not null,
    status ENUM('AVAILABLE', 'PENDING', 'UNAVAILABLE') NOT NULL,
    views int default 0,
    shortlists int default 0,

    PRIMARY KEY(serviceId),
    
    FOREIGN KEY (cleanerId) REFERENCES USERACCOUNT(UId),
    FOREIGN KEY (categoryId) REFERENCES SERVICECATEGORIES(categoryId)
); 

CREATE TABLE REPORT (
	reportId int(10) not null auto_increment,
	type varchar(15) not null,
	date date not null,
    new_homeOwners int(50) not null,
    total_homeOwners int(50) not null,
    new_cleaners int (50) not null,
    total_cleaners int(50) not null,
    total_shortlists int (50) not null,
    total_bookings int (50) not null,
    
	PRIMARY KEY (reportId)
);

CREATE TABLE SHORTLISTEDSERVICES (
    homeownerId int,
    serviceId int,
    dateAdded date,

    PRIMARY KEY (homeownerId, serviceId),

    FOREIGN KEY (homeownerId) REFERENCES USERACCOUNT(UId),
    FOREIGN KEY (serviceId) REFERENCES SERVICELISTINGS(serviceId)
);

CREATE TABLE SHORTLISTEDCLEANERS (
    homeownerId int,
    cleanerId int,
    dateAdded date,

    PRIMARY KEY (homeownerId, cleanerId),

    FOREIGN KEY (homeownerId) REFERENCES USERACCOUNT(UId),
    FOREIGN KEY (cleanerId) REFERENCES USERACCOUNT(UId)
);

CREATE TABLE BOOKING (
	bookingId int not null auto_increment,
    serviceId int,
    homeownerId int,
    status ENUM('CONFIRMED', 'CANCELED', 'COMPLETED') NOT NULL,
    dateAdded date,
    
    PRIMARY KEY (bookingId),
    
    FOREIGN KEY (serviceId) REFERENCES SERVICELISTINGS(serviceId),
	FOREIGN KEY (homeownerId) REFERENCES USERACCOUNT(UID)
);