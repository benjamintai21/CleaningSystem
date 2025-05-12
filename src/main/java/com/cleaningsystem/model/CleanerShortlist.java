package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class CleanerShortlist {

    private int shortlistId;
    private int homeownerId;
    private int cleanerId;

    // No-args constructor
    public CleanerShortlist() {}

    public CleanerShortlist(int homeownerId, int cleanerId) {
        this.homeownerId = homeownerId;
        this.cleanerId = cleanerId;
    }

    public int getShortlistId(){ return shortlistId; }
    public int getHomeownerId(){ return homeownerId; }
    public int getCleanerId(){ return cleanerId; }

    public void setShortlistId(int new_shortlistId){this.shortlistId = new_shortlistId;}
    public void setHomeownerId(int new_homeownerId){this.homeownerId = new_homeownerId;}
    public void setCleanerId(int new_cleanerId){this.cleanerId = new_cleanerId;}
}