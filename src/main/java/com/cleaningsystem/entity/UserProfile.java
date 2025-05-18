package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.cleaningsystem.entity.UserProfile;

import static com.cleaningsystem.db.Queries.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserProfile {

	private int profileId;
	private String profileName;
	private String description;
	private boolean suspended = false; // default value
	
	// No-args constructor
	public UserProfile() {}
	
	// Constructor with all defult boolean suspended
    public UserProfile(String profileName, String description) {
        this.profileName = profileName;
        this.description = description;
    }

	// Constructor with all fields except Id
	public UserProfile(String profileName, String description, boolean suspended) {
		this.profileName = profileName;
		this.description = description;
		this.suspended = suspended;
	}
	
	// Constructor with all fields including Id
	public UserProfile(int profileId, String profileName, String description, boolean suspended) {
		this(profileName, description, suspended);
		this.profileId = profileId;
	}
	
	//Getters
	public int getProfileId() {return profileId;}
	public String getProfileName() {return profileName;}
	public String getDescription() {return description;}
	public boolean isSuspended() {return suspended;}
	
	//Setters
	public void setProfileId(int profileId) {this.profileId = profileId;}
	public void setProfileName(String profileName) {this.profileName = profileName;}
	public void setDescription(String description) {this.description = description;}
	public void setSuspended(boolean suspended) {this.suspended = suspended;}
	
	// Miscellanous
	public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> userAccounts) {
		List<String> profileNames = new ArrayList<>();

        for (UserAccount user : userAccounts) {
        UserProfile userProfile = viewUserProfile(user.getProfileId());
        String profileName = userProfile.getProfileName();
        profileNames.add(profileName);
        }
		return profileNames;
	}

	// Database Stuff
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserAccount userAccount;

	private final RowMapper<UserProfile> profileRowMapper = (ResultSet rs, int rowNum) -> {
		UserProfile profile = new UserProfile();
		profile.setProfileId(rs.getInt("profileId"));
		profile.setProfileName(rs.getString("profilename"));
		profile.setDescription(rs.getString("description"));
		profile.setSuspended(rs.getBoolean("suspension"));
		return profile;
	};

	public boolean createUserProfile(String name, String description, boolean suspended) {
		int rows_affected = jdbcTemplate.update(CREATE_USER_PROFILE, 
			name, description, suspended);
			
		return rows_affected > 0;
	}

	public UserProfile viewUserProfile(int profileId) {
		List<UserProfile> profiles = jdbcTemplate.query(GET_USER_PROFILE_BY_ID, profileRowMapper, profileId);
		return profiles.isEmpty() ? null : profiles.get(0);
	}

	public UserProfile viewUserProfile() {
		List<UserProfile> profiles = jdbcTemplate.query(GET_NEXT_PROFILE, profileRowMapper);
		return profiles.isEmpty() ? null : profiles.get(0);
	}

	public Integer getProfileIdByName(String profileName) {
		List<Integer> ids = jdbcTemplate.query(GET_PROFILE_ID_BY_NAME, 
			(rs, rowNum) -> rs.getInt("profileId"), profileName);
		return ids.isEmpty() ? null : ids.get(0);
	}

	public boolean updateUserProfile(String name, String description, boolean suspended, int profileId) {
		return jdbcTemplate.update(UPDATE_USER_PROFILE, 
			name, description, suspended, profileId) > 0;
	}

	@Transactional
	public boolean suspendUserProfile(int profileId, boolean suspension) {
		List<UserAccount> userAccounts = userAccount.searchUserAccount(profileId);

		try {
			for (UserAccount user : userAccounts) {
				boolean userSuspensionUpdated = userAccount.suspendUserAccount(user.getUid(), suspension);

				if (!userSuspensionUpdated) {
					throw new RuntimeException("Failed to update user suspension for User ID: " + user.getUid());
				}
			}

			int processProfileSuspension = jdbcTemplate.update(SET_PROFILE_SUSPENSION_STATUS, suspension, profileId);

			if (processProfileSuspension <= 0) {
				throw new RuntimeException("Failed to update profile suspension status for Profile ID: " + profileId);
			}

			return true;

		} catch (Exception e) {
			System.out.println("Exception in suspendUserProfile: " + e.getMessage());
			return false;
		}
	}


	public List<String> getAllProfileNames() {
		return jdbcTemplate.query(GET_ALL_PROFILE_NAMES, (rs, rowNum) -> rs.getString("profilename"));
	}
	

	public List<UserProfile> searchUserProfile(String keyword) {
		return jdbcTemplate.query(SEARCH_PROFILE_BY_NAME, profileRowMapper, "%" + keyword + "%");
	}

	public List<UserProfile> searchUserProfile() {
		return jdbcTemplate.query(GET_ALL_PROFILES, profileRowMapper);
	}
} 


