package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ServiceShortlist {

    private int shortlistId;
    private int homeownerId;
    private int serviceId;

    // No-args constructor
    public ServiceShortlist() {}

    public ServiceShortlist(int homeownerId, int serviceId) {
        this.homeownerId = homeownerId;
        this.serviceId = serviceId;
    }

    public int getShortlistId(){ return shortlistId; }
    public int getHomeownerId(){ return homeownerId; }
    public int getServiceId(){ return serviceId; }

    public void setShortlistId(int new_shortlistId){this.shortlistId = new_shortlistId;}
    public void setHomeownerId(int new_homeownerId){this.homeownerId = new_homeownerId;}
    public void setServiceId(int new_serviceId){this.serviceId = new_serviceId;}
}