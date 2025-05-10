package com.cleaningsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
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
	private boolean suspended = false;
		
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
			String username, String password, int profileId, boolean suspended) {
		this(name, age, dob, gender, address, email, username, password, profileId);
		this.uid = uid;
		this.suspended = suspended;
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
	public boolean isSuspended() {return suspended;}

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
	public void setSuspended(boolean new_suspended) {this.suspended = new_suspended;}

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
}


