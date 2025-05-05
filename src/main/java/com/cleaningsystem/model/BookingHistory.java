package com.cleaningsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class BookingHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    private int homeownerId;
    private int serviceId;
    private String status;

    //No-args constructor for Spring
    public BookingHistory() {}
    
    public BookingHistory(int historyId, int homeownerId, int serviceId) {
        this.historyId = historyId;
        this.homeownerId = homeownerId;
        this.serviceId = serviceId;
    }
    
    public int getHistoryId() {return historyId;}

    public int getHomeownerId() {return homeownerId;}            

    public int getServiceId() {return serviceId;}

    public String getStatus() {return status;}

    public void setHistoryId(int historyId) {this.historyId = historyId;}       

    public void setHomeownerId(int homeownerId) {this.homeownerId = homeownerId;}

    public void setServiceId(int serviceId) {this.serviceId = serviceId;}

    public void setStatus(String status) {this.status = status;}
    
}
