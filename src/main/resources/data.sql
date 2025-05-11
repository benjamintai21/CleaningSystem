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
VALUES ('Ren',	69,	'2002-03-10', 'Male',	'179 ABC',	'renaway@gmail.com',	'ren_cleaner', 'ren',	4, CURRENT_DATE);

INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
VALUES ('Benjamin',	24,	'2001-12-12', 'Male', '123 ABC', 'ben@gmail.com', 'benjamin_owner', 'owner', 3, CURRENT_DATE);

INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
VALUES ('Andy',	26,	'1999-03-03', 'Male', '123 ABC', 'andy@gmail.com', 'andy_owner', 'owner', 3, CURRENT_DATE);

INSERT INTO USERACCOUNT (name,age,dob,gender,address,email,username,password,profileID,accountCreated)
VALUES ('Hubert',	35,	'1989-04-11', 'Male', '123 ASDF', 'hurt@gmail.com', 'hubert_pm', 'pm', 1, CURRENT_DATE);

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Ironing', 'Ironing of clothes');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Vacuuming', 'Vacuuming the floor');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Mopping', 'Mopping of floors');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Dusting', 'Dusting of furniture and surfaces');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Window Cleaning', 'Cleaning of windows and glass surfaces');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Cleaning', 'Laundry', 'Washing and folding clothes');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Maintenance', 'Gardening', 'General gardening and landscaping services');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Maintenance', 'Plumbing', 'Plumbing services for leak repairs, installations');

INSERT INTO SERVICECATEGORIES (type, name, description)
VALUES ('Maintenance', 'Electrical', 'Electrical installations and repairs');

INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status)
VALUES ('Listing 1', 2 , 1 , 'Ironing your clothes for cheap', 50, '2025-10-10', '2025-12-12', 'AVAILABLE');

INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status)
VALUES ('Listing 2', 2 , 1 , 'Ironing your clothes for expensive', 50, '2025-10-11', '2025-12-12', 'AVAILABLE');

INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status)
VALUES ('Listing 3', 2 , 2 , 'Vacuuming the floor', 50, '2025-10-11', '2025-12-12', 'AVAILABLE');

INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status)
VALUES ('Listing 4', 2 , 2 , 'Vacuuming the rug', 50, '2025-10-11', '2025-12-12', 'AVAILABLE');

INSERT INTO SERVICELISTINGS (name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status)
VALUES ('Listing 5', 3 , 5 , 'Dancing your clothes', 103, '2025-10-11', '2025-12-12', 'AVAILABLE');

INSERT INTO BOOKING (serviceId, homeownerId, status)
VALUES (1, 3, 'completed');

INSERT INTO BOOKING (serviceId, homeownerId, status)
VALUES (2, 3, 'confirmed');

INSERT INTO BOOKING (serviceId, homeownerId, status)
VALUES (3, 3, 'completed');

INSERT INTO BOOKING (serviceId, homeownerId, status)
VALUES (4, 3, 'completed');

DELIMITER //
CREATE TRIGGER after_status_update
AFTER UPDATE ON SERVICELISTINGS
FOR EACH ROW
BEGIN
    IF NEW.status = 'completed' THEN
        UPDATE booking SET NEW.status = 'completed' WHERE serviceId = NEW.serviceId;
    END IF;
END;
//
DELIMITER ;

