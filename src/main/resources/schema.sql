DROP DATABASE IF EXISTS DB_CLEANINGSYSTEM;

CREATE DATABASE DB_CLEANINGSYSTEM;

USE DB_CLEANINGSYSTEM;

CREATE TABLE USERPROFILE (
    profileId int not null auto_increment,
    profilename varchar(32) not null,
    description varchar(512) not null,
    suspension bool not null,

    PRIMARY KEY (profileID),
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

    PRIMARY KEY(UID),
    CONSTRAINT unique_username UNIQUE (username),
    
    FOREIGN KEY (profileID) REFERENCES USERPROFILE(profileID)
);

CREATE TABLE SERVICECATEGORIES (
	categoryId int not null auto_increment,
    type varchar(50),
    name varchar(50),
    description varchar(512),
    
    PRIMARY KEY(categoryID)
);

CREATE TABLE SERVICELISTINGS (
    serviceId int not null auto_increment,
    name varchar(50) not null,
	cleanerId int not null,
    categoryId int not null,
    description varchar(512) not null,
    price_per_hour double not null,
    status ENUM('AVAILABLE', 'ONGOING', 'COMPLETED'),
    startDate date not null,
    endDate date not null,
    views int default 0,
    shortlists int default 0,

    PRIMARY KEY(serviceID),
    
    FOREIGN KEY (cleanerId) REFERENCES USERACCOUNT(UId),
    FOREIGN KEY (categoryId) REFERENCES SERVICECATEGORIES(categoryId)
); 

CREATE TABLE REPORT (
	reportId int(10) not null auto_increment,
	type varchar(15) not null,
	date date not null,
    views int(50) not null,
    shortlist int(50) not null,
    no_homeowners int (50) not null,
    no_cleaners int(50) not null,
    
	PRIMARY KEY (reportId)
);

CREATE TABLE SHORTLISTEDSERVICES (
    homeownerUId int,
    serviceId int,
    cleanerId int,
    
    PRIMARY KEY (homeownerUID, serviceID),
    
    FOREIGN KEY (homeownerUId) REFERENCES USERACCOUNT(UId),
    FOREIGN KEY (serviceId) REFERENCES SERVICELISTINGS(serviceId),
    FOREIGN KEY (cleanerId) REFERENCES USERACCOUNT(UId)
);

CREATE TABLE BOOKING (
	historyId int not null auto_increment,
    serviceId int,
    homeownerId int,
    status varchar(15),
    
    PRIMARY KEY (historyID),
    
    FOREIGN KEY (serviceId) REFERENCES SERVICELISTINGS(serviceId),
	FOREIGN KEY (homeownerId) REFERENCES USERACCOUNT(UID)
);