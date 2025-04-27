package com.cleaningsystem.controller;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.dto.UserProfileDTO;
import com.cleaningsystem.dao.UserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    @Autowired
    private UserProfileDAO userProfileDAO;

    @GetMapping
    public List<UserProfile> getAllProfiles() {
        return userProfileDAO.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable int id) {
        UserProfile profile = userProfileDAO.getProfileById(id);
        return (profile != null) ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody UserProfileDTO dto,
                                                @RequestHeader("X-Profile-Name") String profileName) {
        if (!"User Admin".equalsIgnoreCase(profileName)) {
            return ResponseEntity.status(403).body("Forbidden: Admins only");
        }

        UserProfile profile = new UserProfile();
        profile.setProfileName(dto.getProfileName());
        profile.setDescription(dto.getDescription());
        profile.setSuspended(dto.isSuspended());
        
        int result = userProfileDAO.insertUserProfile(profile);
        return (result > 0) ? ResponseEntity.ok("Profile created successfully.") : ResponseEntity.badRequest().body("Failed to create profile.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable int id,
                                                @RequestBody UserProfile updatedProfile,
                                                @RequestHeader("X-Profile-Name") String profileName) {
        if (!"User Admin".equalsIgnoreCase(profileName)) {
            return ResponseEntity.status(403).body("Forbidden: Admins only");
        }

        updatedProfile.setProfileId(id);
        boolean updated = userProfileDAO.updateUserProfile(updatedProfile);
        return updated ? ResponseEntity.ok("Profile updated.") : ResponseEntity.badRequest().body("Failed to update profile.");
    }

    @PatchMapping("/suspend/{id}")
    public ResponseEntity<String> suspendProfile(@PathVariable int id,
                                              @RequestHeader("X-Profile-Name") String profileName) {
    if (!"User Admin".equalsIgnoreCase(profileName)) {
        return ResponseEntity.status(403).body("Forbidden: Admins only");
    }

    boolean suspended = userProfileDAO.setSuspension(id, true);

    return suspended
            ? ResponseEntity.ok("Profile suspended successfully.")
            : ResponseEntity.badRequest().body("Failed to suspend profile.");
    }

    @GetMapping("/search")
    public List<UserProfile> searchProfiles(@RequestParam String keyword) {
        return userProfileDAO.searchProfilesByName(keyword);
    }
}
