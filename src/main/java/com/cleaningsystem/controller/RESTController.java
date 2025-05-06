package com.cleaningsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.dao.UserAccountDAO;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RESTController {

    @Autowired
    private UserAccountDAO userAccountDAO;
    
    @PostMapping
    public ResponseEntity<?> createUserAccount(@RequestBody UserAccount userAccount) {
        try {
            // Validate required fields
            if (userAccount.getUsername() == null || userAccount.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (userAccount.getPassword() == null || userAccount.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            if (userAccount.getProfileId() <= 0) {
                return ResponseEntity.badRequest().body("Valid profile Id is required");
            }

            // Check if username already exists
            if (userAccountDAO.getUserByUsername(userAccount.getUsername()) != null) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // Create the user account
            if (userAccountDAO.insertUserAccount(userAccount)) {
                return ResponseEntity.ok("User account created successfully");
            } else {
                return ResponseEntity.internalServerError().body("Failed to create user account");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating user account: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAccount loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            UserAccount user = userAccountDAO.login(username, password);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error during login: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAccount(@PathVariable int id) {
        try {
            UserAccount userAccount = userAccountDAO.getUserById(id);
            if (userAccount != null) {
                return ResponseEntity.ok(userAccount);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving user account: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserAccount(@PathVariable int id, @RequestBody UserAccount userAccount) {
        try {
            // Ensure the Id in path matches the user account
            userAccount.setUid(id);
            
            // Validate required fields
            if (userAccount.getUsername() == null || userAccount.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (userAccount.getProfileId() <= 0) {
                return ResponseEntity.badRequest().body("Valid profile Id is required");
            }

            // Check if username exists for other users
            UserAccount existingUser = userAccountDAO.getUserByUsername(userAccount.getUsername());
            if (existingUser != null && existingUser.getUid() != id) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // Update the user account
            if (userAccountDAO.updateUserAccount(userAccount)) {
                return ResponseEntity.ok("User account updated successfully");
            } else {
                return ResponseEntity.internalServerError().body("Failed to update user account");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating user account: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable int id) {
        try {
            if (userAccountDAO.deleteUserAccount(id)) {
                return ResponseEntity.ok("User account deleted successfully");
            } else {
                return ResponseEntity.internalServerError().body("Failed to delete user account");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting user account: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserAccounts() {
        try {
            List<UserAccount> users = userAccountDAO.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving user accounts: " + e.getMessage());
        }
    }
} 