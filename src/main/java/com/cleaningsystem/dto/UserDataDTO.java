package com.cleaningsystem.dto;

public class UserDataDTO {
    private int uid;
    private String name;
    private int age;
    private String dob;
    private String gender;
    private String address;
    private String email;
    private String username;
    private int profileID;

    // Constructor
    public UserDataDTO(int uid, String name, int age, String dob, String gender, 
                      String address, String email, String username, int profileID) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.username = username;
        this.profileID = profileID;
    }

    // Getters and Setters
    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getProfileID() { return profileID; }
    public void setProfileID(int profileID) { this.profileID = profileID; }
}
