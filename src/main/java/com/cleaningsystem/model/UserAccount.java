package com.cleaningsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.UserAccountDAO;

@Component
public class UserAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;

	private String name;
	private int age;
	private String dob;
	private String gender;
	private String address;
	private String email;
	private String username;
	private String password;
	
	private int profileId;
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	// No-args constructor
	public UserAccount() {}
	
	// Constructor with all fields
	public UserAccount(String name, int age, String dob, String gender, 
						String address, String email, 
						String username, String password, int profileId) {
		this.name = name;
		this.age = age;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		
		this.profileId = profileId;
	}
	
	// Constructor with UId
	public UserAccount(int uid, String name, int age, String dob, String gender, 
			String address, String email, 
			String username, String password, int profileId) {
		this(name, age, dob, gender, address, email, username, password, profileId);
		this.uid = uid;
	}
	
	//Getters
	public int getUid() {return uid;}
	public String getName() {return name;}
	public int getAge() {return age;}
	public String getDob() {return dob;}
	public String getGender() {return gender;}
	public String getAddress() {return address;}
	public String getEmail() {return email;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public int getProfileId() {return profileId;}
	
	//Setters
	public void setUid(int new_uid) {this.uid = new_uid;}
	public void setName(String new_name) {this.name = new_name;}
	public void setAge(int new_age) {this.age = new_age;}
	public void setDob(String new_dob) {this.dob = new_dob;}
	public void setGender(String new_gender) {this.gender = new_gender;}
	public void setAddress(String new_address) {this.address = new_address;}
	public void setEmail(String new_email) {this.email = new_email;}
	public void setUsername(String new_username) {this.username = new_username;}
	public void setPassword(String new_password) {this.password = new_password;}
	public void setProfileId(int new_profileId) {this.profileId = new_profileId;}
	
	@Override
	public String toString() {
		return "UserAccount{" +
				"uid=" + uid +
				", name='" + name + '\'' +
				", age=" + age +
				", dob='" + dob + '\'' +
				", gender='" + gender + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", profileId=" + profileId +
				'}';
	}

	public UserAccount login(String username, String password) {
        return userAccountDAO.login(username, password);	
    }

    public boolean insertUserAccount(UserAccount user) {
        return userAccountDAO.insertUserAccount(user);
    }

    public UserAccount getUserById(int uid) {
        return userAccountDAO.getUserById(uid);
    }

    public UserAccount getUserByUsername(String username) {
        return userAccountDAO.getUserByUsername(username);
    }

    public boolean updateUserAccount(UserAccount user) {
        return userAccountDAO.updateUserAccount(user);
    }

    public boolean deleteUserAccount(int uid) {
        return userAccountDAO.deleteUserAccount(uid);
    }

    public List<UserAccount> searchUsersByUsername(String keyword) {
        return userAccountDAO.searchUsersByUsername(keyword);
    }

    public List<UserAccount> searchUsersByProfileId(int profileId) {
        return userAccountDAO.searchUsersByProfileId(profileId);
    }

    public List<UserAccount> getAllUsers() {
        return userAccountDAO.getAllUsers();
    }
}


