    package com.cleaningsystem.dao;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.springframework.stereotype.Repository;
    import com.cleaningsystem.model.UserAccount;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.List;
    import java.sql.Date;

    @Repository
    public class UserAccountDAO {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        private final RowMapper<UserAccount> userRowMapper = (ResultSet rs, int rowNum) -> {
            UserAccount user = new UserAccount();
            user.setUID(rs.getInt("UID"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setDOB(rs.getDate("dob").toLocalDate().toString());
            user.setGender(rs.getString("gender"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setProfileID(rs.getInt("profileID"));
            return user;
        };

        public UserAccount login(String username, String password) {
            String sql = "SELECT * FROM USERACCOUNT WHERE username = ? AND password = ?";
            List<UserAccount> users = jdbcTemplate.query(sql, userRowMapper, username, password);
            System.out.println(users.get(0).getDOB());
            return users.isEmpty() ? null : users.get(0);
        }

        public int insertUserAccount(UserAccount user) {
            String sql = "INSERT INTO USERACCOUNT (name, age, dob, gender, address, email, username, password, profileID, accountCreated) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            java.sql.Date sqlDob = java.sql.Date.valueOf(user.getDOB());
            return jdbcTemplate.update(sql, 
                user.getName(), user.getAge(), sqlDob, user.getGender(), 
                user.getAddress(), user.getEmail(), user.getUsername(), user.getPassword(), 
                user.getProfileID(),
                new Date(System.currentTimeMillis()));
        }

        public UserAccount getUserByID(int uid) {
            String sql = "SELECT * FROM USERACCOUNT WHERE UID = ?";
            List<UserAccount> users = jdbcTemplate.query(sql, userRowMapper, uid);
            return users.isEmpty() ? null : users.get(0);
        }

        public boolean updateUserAccount(UserAccount user) {
            String sql = "UPDATE USERACCOUNT SET name = ?, age = ?, dob = ?, gender = ?, " +
                        "address = ?, email = ?, username = ?, profileID = ? WHERE UID = ?";
            return jdbcTemplate.update(sql, 
                user.getName(), user.getAge(), user.getDOB(), user.getGender(),
                user.getAddress(), user.getEmail(), user.getUsername(), user.getProfileID(),
                user.getUID()) > 0;
        }

        public boolean deleteUserAccount(int uid) {
            String sql = "DELETE FROM USERACCOUNT WHERE UID = ?";
            return jdbcTemplate.update(sql, uid) > 0;
        }

        public List<UserAccount> getAllUsers() {
            String sql = "SELECT * FROM USERACCOUNT";
            return jdbcTemplate.query(sql, userRowMapper);
        }

        public List<UserAccount> searchUsersByUsername(String keyword) {
            String sql = "SELECT * FROM USERACCOUNT WHERE username LIKE ?";
            return jdbcTemplate.query(sql, userRowMapper, "%" + keyword + "%");
        }

        public UserAccount getUserByUsername(String username) {
            String sql = "SELECT * FROM USERACCOUNT WHERE username = ?";
            List<UserAccount> users = jdbcTemplate.query(sql, userRowMapper, username);
            return users.isEmpty() ? null : users.get(0);
        }
    } 



