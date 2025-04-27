package com.cleaningsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dto.UserAccountDTO;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountDAO userAccountDAO;

    public UserAccountDTO login(UserAccount userAccount, Model model) {
        UserAccount loggedInUser = userAccountDAO.login(userAccount.getUsername(), userAccount.getPassword());

        if (loggedInUser != null) {
            return new UserAccountDTO(
                loggedInUser.getName(),
                loggedInUser.getAge(),
                loggedInUser.getDob(),
                loggedInUser.getGender(),
                loggedInUser.getAddress(),
                loggedInUser.getEmail(),
                loggedInUser.getUsername(),
                loggedInUser.getProfileId()
            );
        } else {
            return null;
        }
    }

    // ✨ signup now accepts a DTO
    public UserAccountDTO signup(UserAccountDTO dto, String password) {
        UserAccount userAccount = convertToEntity(dto, password);

        System.out.println("Inserting user: " + userAccount);
        if(userAccountDAO.insertUserAccount(userAccount)){
            return new UserAccountDTO(
            userAccount.getName(),
            userAccount.getAge(),
            userAccount.getDob(),
            userAccount.getGender(),
            userAccount.getAddress(),
            userAccount.getEmail(),
            userAccount.getUsername(),
            userAccount.getProfileId()
        );}
        else{
            return null;
        }
    }

    // ✨ pass dto + password into this method
    private UserAccount convertToEntity(UserAccountDTO dto, String password) {
        return new UserAccount(
            dto.getName(),
            dto.getAge(),
            dto.getDob(),
            dto.getGender(),
            dto.getAddress(),
            dto.getEmail(),
            dto.getUsername(),
            password,           // Pass password manually
            dto.getProfileId()
        );
    }
}