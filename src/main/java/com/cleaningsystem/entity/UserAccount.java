package com.cleaningsystem.entity;

import static com.cleaningsystem.db.Queries.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserAccount {
	
	private int uid;
	private String name;
	private int age;
	private String dob;
	private String gender;
	private String address;
	private String email;
	private String username;
	private String password;
	private int profileId;
	private boolean suspended = false;
		
	// No-args constructor
	public UserAccount() {}
	
	// Constructor with all fields
	public UserAccount(String name, int age, String dob, String gender, 
						String address, String email, 
						String username, String password, int profileId) {
		this.name = name;
		this.age = age;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.profileId = profileId;
	}
	
	// Constructor with UId
	public UserAccount(int uid, String name, int age, String dob, String gender, 
			String address, String email, 
			String username, String password, int profileId, boolean suspended) {
		this(name, age, dob, gender, address, email, username, password, profileId);
		this.uid = uid;
		this.suspended = suspended;
	}
	
	//Getters
	public int getUid() {return uid;}
	public String getName() {return name;}
	public int getAge() {return age;}
	public String getDob() {return dob;}
	public String getGender() {return gender;}
	public String getAddress() {return address;}
	public String getEmail() {return email;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public int getProfileId() {return profileId;}
	public boolean isSuspended() {return suspended;}

	//Setters
	public void setUid(int new_uid) {this.uid = new_uid;}
	public void setName(String new_name) {this.name = new_name;}
	public void setAge(int new_age) {this.age = new_age;}
	public void setDob(String new_dob) {this.dob = new_dob;}
	public void setGender(String new_gender) {this.gender = new_gender;}
	public void setAddress(String new_address) {this.address = new_address;}
	public void setEmail(String new_email) {this.email = new_email;}
	public void setUsername(String new_username) {this.username = new_username;}
	public void setPassword(String new_password) {this.password = new_password;}
	public void setProfileId(int new_profileId) {this.profileId = new_profileId;}
	public void setSuspended(boolean new_suspended) {this.suspended = new_suspended;}

	// Micellanous
	public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> userProfiles){
		List<Integer> usersPerProfileCount = new ArrayList<>();

        for (UserProfile userProfile : userProfiles) {
            List<UserAccount> userAccounts = searchUserAccount(userProfile.getProfileId());
            usersPerProfileCount.add(userAccounts.size());
        }
		return usersPerProfileCount;
	}

	public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> serviceListings) {
		List<String> cleanersName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            UserAccount user = viewUserAccount(listing.getCleanerId());
            String cleanerName = user.getName();
            cleanersName.add(cleanerName);
		}
		return cleanersName;
	}

	public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> shortlists) {
        List<UserAccount> cleanersShortlist = new ArrayList<>();
        
        for (CleanerShortlist shortlist : shortlists) {
            UserAccount cleaner = viewUserAccount(shortlist.getCleanerId());
            cleanersShortlist.add(cleaner);
        }
        return cleanersShortlist;
	}

	// Database Stuff
	@Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<UserAccount> userRowMapper = (ResultSet rs, int rowNum) -> {
        UserAccount user = new UserAccount();
        user.setUid(rs.getInt("UId"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setDob(rs.getDate("dob").toLocalDate().toString());
        user.setGender(rs.getString("gender"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setProfileId(rs.getInt("profileId"));
        user.setSuspended(rs.getBoolean("suspended"));
        return user;
    };

    public UserAccount login(String username, String password, int profileId) {
        List<UserAccount> users = jdbcTemplate.query(LOGIN, userRowMapper, username, password, profileId);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
        java.sql.Date sqlDob = java.sql.Date.valueOf(dob);
        return jdbcTemplate.update(CREATE_USER_ACCOUNT, 
            name,age,sqlDob,gender,address,email,username,password,profileId,
            new Date(System.currentTimeMillis())) > 0;
    }

    public UserAccount viewUserAccount(int uid) {
        List<UserAccount> users = jdbcTemplate.query(GET_USER_ACCOUNT_BY_ID, userRowMapper, uid);
        return users.isEmpty() ? null : users.get(0);
    }

    public UserAccount getUserByUsername(String username) {
        List<UserAccount> users = jdbcTemplate.query(GET_USER_ACCOUNT_BY_USERNAME, userRowMapper, username);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int Uid) {
        java.sql.Date sqlDob = java.sql.Date.valueOf(dob);
        return jdbcTemplate.update(UPDATE_USER_ACCOUNT, 
            name,age,sqlDob,gender,address,email,username,password,profileId, Uid) > 0;
    }

    public boolean suspendUserAccount(int uid, boolean suspended) {
        return jdbcTemplate.update(SET_ACCOUNT_SUSPENSION_STATUS, suspended, uid) > 0;
    }

    public List<UserAccount> searchUserAccount(String keyword) {
        return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_USERNAME_OR_NAME_OR_ROLE, userRowMapper, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }

    public List<UserAccount> searchUserAccount(int profileId) {
        return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_PROFILEID, userRowMapper, profileId);
    }

    public List<UserAccount> searchUserAccount() {
        return jdbcTemplate.query(GET_ALL_USER_ACCOUNT, userRowMapper);
    }

    public String getProfileNameByUid(int uid) {
        return jdbcTemplate.queryForObject(CHECK_USER_ACCOUNT, String.class, uid);
    }
} 


