package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.UserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserProfileDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<UserProfile> profileRowMapper = (ResultSet rs, int rowNum) -> {
		UserProfile profile = new UserProfile();
		profile.setProfileID(rs.getInt("profileID"));
		profile.setProfileName(rs.getString("profilename"));
		profile.setDescription(rs.getString("description"));
		profile.setSuspended(rs.getBoolean("suspension"));
		return profile;
	};

	public int insertUserProfile(UserProfile profile) {
		String sql = "INSERT INTO USERPROFILE (profilename, description, suspension) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql, 
			profile.getProfileName(), profile.getDescription(), profile.isSuspended());
	}

	public UserProfile getProfileByID(int profileId) {
		String sql = "SELECT * FROM USERPROFILE WHERE profileID = ?";
		List<UserProfile> profiles = jdbcTemplate.query(sql, profileRowMapper, profileId);
		return profiles.isEmpty() ? null : profiles.get(0);
	}

	public boolean updateUserProfile(UserProfile profile) {
		String sql = "UPDATE USERPROFILE SET profilename = ?, description = ?, suspension = ? WHERE profileID = ?";
		return jdbcTemplate.update(sql, 
			profile.getProfileName(), profile.getDescription(), profile.isSuspended(), 
			profile.getProfileID()) > 0;
	}

	public boolean setSuspensionStatus(int profileId, boolean suspension) {
		String sql = "UPDATE USERPROFILE SET suspension = ? WHERE profileID = ?";
		return jdbcTemplate.update(sql, suspension, profileId) > 0;
	}

	public List<UserProfile> getAllProfiles() {
		String sql = "SELECT * FROM USERPROFILE";
		return jdbcTemplate.query(sql, profileRowMapper);
	}

	public int getProfileIDByName(String profileName) {
		String sql = "SELECT profileID FROM USERPROFILE WHERE profilename = ?";
		List<Integer> ids = jdbcTemplate.query(sql, 
			(rs, rowNum) -> rs.getInt("profileID"), profileName);
		return ids.isEmpty() ? -1 : ids.get(0);
	}

	public List<UserProfile> searchProfilesByName(String keyword) {
		String sql = "SELECT * FROM USERPROFILE WHERE profilename LIKE ?";
		return jdbcTemplate.query(sql, profileRowMapper, "%" + keyword + "%");
	}
} 


