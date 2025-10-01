package com.cleaningsystem.controller.Booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Booking;

@Service
public class SearchBookingHistoryController {

    @Autowired
    private Booking booking;

    public List<Booking> searchBookingHistory(int homeownerId, String keyword) {
        return booking.searchBookingHistory(homeownerId, keyword);
    }

}
