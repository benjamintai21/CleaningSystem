DROP DATABASE IF EXISTS DB_CLEANINGSYSTEM;

CREATE DATABASE DB_CLEANINGSYSTEM;

USE DB_CLEANINGSYSTEM;

CREATE TABLE USERPROFILE (
    profileID int not null auto_increment,
    profilename varchar(32) not null,
    description varchar(512) not null,
    suspension bool not null,

    PRIMARY KEY (profileID),
    CONSTRAINT unique_profilename UNIQUE (profilename)
);

CREATE TABLE USERACCOUNT (
    UID int not null auto_increment, 
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
	cID int not null auto_increment,
    name varchar(50),
    description varchar(512),
    
    PRIMARY KEY(cID)
);

CREATE TABLE SERVICELISTINGS (
    serviceID int not null auto_increment,
    name varchar(50) not null,
    cleanerID int not null,
    cID int not null,
    description varchar(512) not null,
    price int not null,
    status ENUM('ONGOING', 'COMPLETED'),
    views int not null,
    shortlisting int not null,

    PRIMARY KEY(serviceID),
    
    FOREIGN KEY (cleanerID) REFERENCES USERACCOUNT(UID),
    FOREIGN KEY (cID) REFERENCES SERVICECATEGORIES(cID)
);

CREATE TABLE REPORT (
	reportID int(10) not null auto_increment,
	type varchar(15) not null,
	date date not null,
    views int(50) not null,
    shortlist int(50) not null,
    no_homeowners int (50) not null,
    no_cleaners int(50) not null,
    
	PRIMARY KEY (reportID)
);

CREATE TABLE REVIEWS (
	cleanerID int not null,
    serviceID int not null,
	rating int(5) not null,
    description varchar(512) not null,
    comments varchar(512),
    
    PRIMARY KEY (cleanerID, serviceID),
    
    FOREIGN KEY (cleanerID) REFERENCES USERACCOUNT(UID),
    FOREIGN KEY (serviceID) REFERENCES SERVICELISTINGS(serviceID)
);

CREATE TABLE SHORTLISTEDSERVICES (
    homeownerUID int,
    serviceID int,
    
    PRIMARY KEY (homeownerUID, serviceID),
    
    FOREIGN KEY (homeownerUID) REFERENCES USERACCOUNT(UID),
    FOREIGN KEY (serviceID) REFERENCES SERVICELISTINGS(serviceID)
);

CREATE TABLE SHORTLISTEDCLEANERS (
    homeownerUID int,
    cleanerUID int,
    
    PRIMARY KEY (homeownerUID, cleanerUID),
    
    FOREIGN KEY (homeownerUID) REFERENCES USERACCOUNT(UID),
    FOREIGN KEY (cleanerUID) REFERENCES USERACCOUNT(UID)
);

CREATE TABLE BOOKINGHISTORY (
	historyID int not null auto_increment,
    serviceID int,
    homeownerID int,
    
    PRIMARY KEY (historyID),
    
    FOREIGN KEY (serviceID) REFERENCES SERVICELISTINGS(serviceID),
	FOREIGN KEY (homeownerID) REFERENCES USERACCOUNT(UID)
);

CREATE TABLE REPORTHISTORY (
    reportID int not null,
    historyID int not null,

    PRIMARY KEY (reportID, historyID),

    FOREIGN KEY (reportID) REFERENCES REPORT(reportID),
    FOREIGN KEY (historyID) REFERENCES BOOKINGHISTORY(historyID)
);

