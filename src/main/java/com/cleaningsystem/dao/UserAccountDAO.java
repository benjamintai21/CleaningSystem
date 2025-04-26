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
        user.setUid(rs.getInt("UID"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setDob(rs.getDate("dob").toLocalDate().toString());
        user.setGender(rs.getString("gender"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setProfileID(rs.getInt("profileID"));
        return user;
    };

    public UserAccount login(String username, String password) {
        List<UserAccount> users = jdbcTemplate.query(LOGIN, userRowMapper, username, password);
        return users.isEmpty() ? null : users.get(0);
    }

    public int insertUserAccount(UserAccount user) {
        java.sql.Date sqlDob = java.sql.Date.valueOf(user.getDob());
        return jdbcTemplate.update(CREATE_USER_ACCOUNT, 
            user.getName(), user.getAge(), sqlDob, user.getGender(), 
            user.getAddress(), user.getEmail(), user.getUsername(), user.getPassword(), 
            user.getProfileID(),
            new Date(System.currentTimeMillis()));
    }

    public UserAccount getUserByID(int uid) {
        List<UserAccount> users = jdbcTemplate.query(GET_USER_ACCOUNT_BY_ID, userRowMapper, uid);
        return users.isEmpty() ? null : users.get(0);
    }

    public UserAccount getUserByUsername(String username) {
        List<UserAccount> users = jdbcTemplate.query(GET_USER_ACCOUNT_BY_USERNAME, userRowMapper, username);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean updateUserAccount(UserAccount user) {
        return jdbcTemplate.update(UPDATE_USER_ACCOUNT,
            user.getName(), user.getAge(), user.getDob(), user.getGender(),
            user.getAddress(), user.getEmail(), user.getUsername(), user.getProfileID(),
            user.getUid()) > 0;
    }

    public boolean deleteUserAccount(int uid) {
        return jdbcTemplate.update(DELETE_USER_ACCOUNT, uid) > 0;
    }

    public List<UserAccount> searchUsersByUsername(String keyword) {
        return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_USERNAME, userRowMapper, "%" + keyword + "%");
    }

    public List<UserAccount> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USER_ACCOUNT, userRowMapper);
    }

} 



