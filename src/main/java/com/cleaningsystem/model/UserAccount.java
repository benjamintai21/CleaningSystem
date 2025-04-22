package com.cleaningsystem.model;

import java.time.LocalDate;

public class UserAccount {
	private int uid;
	private String name;
	private int age;
	private String dob;
	private String gender;
	private String address;
	private String email;
	private String username;
	private String password;
	
	private int profileID;
	
	// No-args constructor
	public UserAccount() {}
	
	// Constructor with all fields
	public UserAccount(String name, int age, String dob, String gender, 
						String address, String email, 
						String username, String password, int profileID) {
		this.name = name;
		this.age = age;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		
		this.profileID = profileID;
	}
	
	// Constructor with UID
	public UserAccount(int uid, String name, int age, String dob, String gender, 
			String address, String email, 
			String username, String password, int profileID) {
		this(name, age, dob, gender, address, email, username, password, profileID);
		this.uid = uid;
	}
	
	//Getters
	public int getUID() {return uid;}
	public String getName() {return name;}
	public int getAge() {return age;}
	public String getDOB() {return dob;}
	public String getGender() {return gender;}
	public String getAddress() {return address;}
	public String getEmail() {return email;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public int getProfileID() {return profileID;}
	
	//Setters
	public void setUID(int new_uid) {this.uid = new_uid;}
	public void setName(String new_name) {this.name = new_name;}
	public void setAge(int new_age) {this.age = new_age;}
	public void setDOB(String new_dob) {this.dob = new_dob;}
	public void setGender(String new_gender) {this.gender = new_gender;}
	public void setAddress(String new_address) {this.address = new_address;}
	public void setEmail(String new_email) {this.email = new_email;}
	public void setUsername(String new_username) {this.username = new_username;}
	public void setPassword(String new_password) {this.password = new_password;}
	public void setProfileID(int new_profileID) {this.profileID = new_profileID;}
	
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
				", profileID=" + profileID +
				'}';
	}
}


