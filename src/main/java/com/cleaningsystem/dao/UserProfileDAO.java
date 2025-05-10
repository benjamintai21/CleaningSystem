package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserProfileDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserAccountDAO userAccountDAO;

	private final RowMapper<UserProfile> profileRowMapper = (ResultSet rs, int rowNum) -> {
		UserProfile profile = new UserProfile();
		profile.setProfileId(rs.getInt("profileId"));
		profile.setProfileName(rs.getString("profilename"));
		profile.setDescription(rs.getString("description"));
		profile.setSuspended(rs.getBoolean("suspension"));
		return profile;
	};

	public boolean insertUserProfile(UserProfile profile) {
		int rows_affected = jdbcTemplate.update(CREATE_USER_PROFILE, 
			profile.getProfileName(), profile.getDescription(), profile.isSuspended());
			
		return rows_affected > 0;
	}

	public UserProfile getProfileById(int profileId) {
		List<UserProfile> profiles = jdbcTemplate.query(GET_USER_PROFILE_BY_ID, profileRowMapper, profileId);
		return profiles.isEmpty() ? null : profiles.get(0);
	}

	public Integer getProfileIdByName(String profileName) {
		List<Integer> ids = jdbcTemplate.query(GET_PROFILE_ID_BY_NAME, 
			(rs, rowNum) -> rs.getInt("profileId"), profileName);
		return ids.isEmpty() ? null : ids.get(0);
	}

	public List<String> getProfileNames() {
		return jdbcTemplate.query(GET_PROFILE_NAMES, (rs, rowNum) -> rs.getString("profilename"));
	}

	public boolean updateUserProfile(UserProfile profile) {
		return jdbcTemplate.update(UPDATE_USER_PROFILE, 
			profile.getProfileName(), profile.getDescription(), profile.isSuspended(), 
			profile.getProfileId()) > 0;
	}

	@Transactional
	public boolean setSuspensionStatusForAllAccounts(int profileId, boolean suspension) {
		List<UserAccount> userAccounts = userAccountDAO.searchUsersByProfileId(profileId);
	
		try {
			for (UserAccount user : userAccounts) {
				boolean userSuspensionUpdated = userAccountDAO.setSuspensionStatus(user.getUid(), suspension);
				
				if (!userSuspensionUpdated) {
					throw new RuntimeException("Failed to update user suspension for user ID: " + user.getUid());
				}
			}
			
			int processProfileSuspension = jdbcTemplate.update(SET_PROFILE_SUSPENSION_STATUS, suspension, profileId);
		
			if (processProfileSuspension <= 0) {
				throw new RuntimeException("Failed to update profile suspension status for profile ID: " + profileId);
			}

			return true;
	
		} catch (Exception e) {
			return false;
		}
	}

	public List<UserProfile> searchProfilesByName(String keyword) {
		return jdbcTemplate.query(SEARCH_PROFILE_BY_NAME, profileRowMapper, "%" + keyword + "%");
	}

	public List<UserProfile> getAllProfiles() {
		return jdbcTemplate.query(GET_ALL_PROFILES, profileRowMapper);
	}
} 


