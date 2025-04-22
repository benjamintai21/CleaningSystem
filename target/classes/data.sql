INSERT INTO USERPROFILE (profileID, profileName, description, suspension)
VALUES (-1, 'Unassigned', 'NIL', 0);

INSERT INTO USERPROFILE (profileName, description, suspension)
VALUES ('Platform Manager', 'Maintaining the Platform', 0);

INSERT INTO USERPROFILE (profileName, description, suspension)
VALUES ('User Admin', 'Responsible for managing user accounts', 0);

INSERT INTO USERPROFILE (profileName, description, suspension)
VALUES ('Home Owner', 'Hiring cleaners for jobs', 0);

INSERT INTO USERPROFILE (profileName, description, suspension)
VALUES ('Cleaner', 'Responsible for cleaning jobs', 0);


INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
values ('Admin', 25, '2001-01-01', 'Male', '123 Clementi', 'admin@gmail.com', 'admin', 'password', 2, '2025-04-19');

INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
VALUES ('Bennett Siew',	49,	'2001-10-10', 'Female',	'123 ABC',	'b@gmail.com',	'bennett_cleaner', 'clean',	4, CURRENT_DATE);

INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
VALUES ('Benjamin',	24,	'2001-12-12', 'Male', '123 ABC', 'ben@gmail.com', 'benjamin_owner', 'owner', 3, CURRENT_DATE);
