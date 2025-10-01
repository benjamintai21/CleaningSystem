package com.cleaningsystem.controller.LoginLogout;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class LogoutController {

    public void logout(HttpSession session) {
        session.invalidate(); 
    }
}
