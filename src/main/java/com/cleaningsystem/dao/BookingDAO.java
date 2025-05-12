package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.Booking;

import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;


@Repository
public class BookingDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Booking> listingRowMapper = (ResultSet rs, int rowNum) -> {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("bookingId"));
        booking.setHomeownerId(rs.getInt("homeownerId"));
        booking.setServiceId(rs.getInt("serviceId"));
        booking.setStatus(rs.getString("status"));
        return booking;
    };

    // HomeOwner
    public boolean addBooking(int serviceId, int homeownerId, String status) {
        return jdbcTemplate.update(CREATE_BOOKING, serviceId, homeownerId, status) > 0;
    }

    public List<Booking> getPastBookings(int homeownerId) {
        return jdbcTemplate.query(GET_COMPLETED_SERVICES, listingRowMapper, homeownerId);
    }

    public List<Booking> searchPastBookings(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_PAST_BOOKINGS, listingRowMapper, homeownerId, pattern);
    }

    // CLeaner
    public List<Booking> getConfirmedMatches(int cleanerId) {
        return jdbcTemplate.query(GET_CONFIRMED_MATCHES, listingRowMapper, cleanerId);
    }

    public Booking getBookingById(int bookingId){
        List<Booking> bookings = jdbcTemplate.query(GET_BOOKING_BY_ID, listingRowMapper, bookingId);
        return bookings.isEmpty() ? null : bookings.get(0);
    }

    public List<Booking> searchConfirmedMatches(int cleanerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_CONFIRMED_MATCHES, listingRowMapper, cleanerId, pattern);
    }
}