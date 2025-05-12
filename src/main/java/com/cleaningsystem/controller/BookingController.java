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
    public boolean addBooking(int serviceId, int homeownerId, String status) {
        return bookingDAO.addBooking(serviceId, homeownerId, status);
    }

    public List<Booking> getAllBookingsByHomeOwner(int homeownerId) {
        return bookingDAO.getAllBookingsByHomeOwner(homeownerId);
    }

    public List<Booking> searchHomeOwnerBookings(int homeownerId, String keyword) {
        return bookingDAO.searchHomeOwnerBookings(homeownerId, keyword);
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
