package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.UserAccount;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Date;

@Repository
public class UserAccountDAO {
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

    public boolean insertUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
        java.sql.Date sqlDob = java.sql.Date.valueOf(dob);
        return jdbcTemplate.update(CREATE_USER_ACCOUNT, 
            name,age,sqlDob,gender,address,email,username,password,profileId,
            new Date(System.currentTimeMillis())) > 0;
    }

    public UserAccount getUserById(int uid) {
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

    public boolean setSuspensionStatus(int uid, boolean suspended) {
        return jdbcTemplate.update(SET_ACCOUNT_SUSPENSION_STATUS, suspended, uid) > 0;
    }

    public List<UserAccount> searchUsersByUsername(String keyword) {
        return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_USERNAME, userRowMapper, "%" + keyword + "%");
    }

    public List<UserAccount> searchUsersByProfileId(int profileId) {
        return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_PROFILEID, userRowMapper, profileId);
    }

    public List<UserAccount> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USER_ACCOUNT, userRowMapper);
    }

    public String getProfileNameByUid(int uid) {
        return jdbcTemplate.queryForObject(CHECK_USER_ACCOUNT, String.class, uid);
    }
} 



