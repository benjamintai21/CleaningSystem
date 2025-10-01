package com.cleaningsystem.controller.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Booking;

@Service
public class AddtoBookingController {

    @Autowired
    private Booking booking;

    // HomeOwner
    public boolean addBooking(int serviceId, int homeownerId, String status) {
        return booking.addBooking(serviceId, homeownerId, status);
    }
}
