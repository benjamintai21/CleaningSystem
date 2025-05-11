package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class Booking {
    
    private int bookingId;
    private int homeownerId;
    private int serviceId;
    private String status;

    //No-args constructor for Spring
    public Booking() {}
    
    public Booking(int bookingId, int homeownerId, int serviceId, String status) {
        this.bookingId = bookingId;
        this.homeownerId = homeownerId;
        this.serviceId = serviceId;
        this.status = status;
    }

    public enum Status {
        CONFIRMED, CANCELED, COMPLETED
    }
    
    
    public int getBookingId() {return bookingId;}
    public int getHomeownerId() {return homeownerId;}            
    public int getServiceId() {return serviceId;}
    public String getStatus() {return status;}

    public void setBookingId(int bookingId) {this.bookingId = bookingId;}       
    public void setHomeownerId(int homeownerId) {this.homeownerId = homeownerId;}
    public void setServiceId(int serviceId) {this.serviceId = serviceId;}
    public void setStatus(String status) {this.status = status;}
}
