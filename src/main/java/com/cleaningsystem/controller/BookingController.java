package com.cleaningsystem.controller;

import java.util.List;

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

    public List<Booking> getAllBookingsByHomeOwner(int homeownerId) {
        return booking.getAllBookingsByHomeOwner(homeownerId);
    }

    public List<Booking> searchHomeOwnerBookings(int homeownerId, String keyword) {
        return booking.searchHomeOwnerBookings(homeownerId, keyword);
    }

    // CLeaner
    public List<Booking> getConfirmedMatches(int cleanerId) {
        return booking.getConfirmedMatches(cleanerId);
    }

    public List<Booking> searchConfirmedMatches(int cleanerId, String keyword) {
        return booking.searchConfirmedMatches(cleanerId, keyword);
    }

    public Booking getBookingById(int bookingId){
        return booking.getBookingById(bookingId);
    }
}
