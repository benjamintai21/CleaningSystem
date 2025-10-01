package com.cleaningsystem.controller.Booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Booking;

@Service
public class SearchCompletedBookingController {
    
    @Autowired
    private Booking booking;

    public List<Booking> searchCompletedBooking(int cleanerId, String keyword) {
        return booking.searchCompletedBooking(cleanerId, keyword);
    }
}
