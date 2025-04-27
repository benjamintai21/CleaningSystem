package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.UserProfile;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserProfileDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	public boolean updateUserProfile(UserProfile profile) {
		return jdbcTemplate.update(UPDATE_USER_PROFILE, 
			profile.getProfileName(), profile.getDescription(), profile.isSuspended(), 
			profile.getProfileId()) > 0;
	}

	public boolean setSuspension(int profileId, boolean suspension) {
		return jdbcTemplate.update(SET_SUSPENSION_STATUS, suspension, profileId) > 0;
	}

	public List<UserProfile> searchProfilesByName(String keyword) {
		return jdbcTemplate.query(SEARCH_PROFILE_BY_NAME, profileRowMapper, "%" + keyword + "%");
	}

	public List<UserProfile> getAllProfiles() {
		return jdbcTemplate.query(GET_ALL_PROFILES, profileRowMapper);
	}
} 


