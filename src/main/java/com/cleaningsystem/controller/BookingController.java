package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.BookingDAO;
import com.cleaningsystem.model.Booking;

@Service
public class BookingController {

    @Autowired
    private BookingDAO bookingDAO;

    // HomeOwner
    public List<Booking> getPastBookings(int homeownerId) {
        return bookingDAO.getPastBookings(homeownerId);
    }

    public List<Booking> searchPastBookings(int homeownerId, String keyword) {
        return bookingDAO.searchPastBookings(homeownerId, keyword);
    }

    // CLeaner
    public List<Booking> getConfirmedMatches(int cleanerId) {
        return bookingDAO.getConfirmedMatches(cleanerId);
    }

    public List<Booking> searchConfirmedMatches(int cleanerId, String keyword) {
        return bookingDAO.searchConfirmedMatches(cleanerId, keyword);
    }

    public Booking getBookingById(int bookingId){
        return bookingDAO.getBookingById(bookingId);
    }
}
