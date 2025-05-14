package com.cleaningsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Booking;

@Service
public class BookingController {

    @Autowired
    private Booking booking;

    // HomeOwner
    public boolean addBooking(int serviceId, int homeownerId, String status) {
        return booking.addBooking(serviceId, homeownerId, status);
    }

    // Cleaner
    public Booking getBookingById(int bookingId){
        return booking.getBookingById(bookingId);
    }
}
