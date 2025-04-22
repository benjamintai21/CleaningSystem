package com.cleaningsystem.model;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;

@Service
public class ServiceProvider {
    @Autowired
    private UserAccountDAO userAccountDAO;
    
    @Autowired
    private UserProfileDAO userProfileDAO;
    
    // ... existing code ... 
} 