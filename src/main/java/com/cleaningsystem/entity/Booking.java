package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cleaningsystem.entity.Booking;

import static com.cleaningsystem.db.Queries.*;

import java.sql.ResultSet;
import java.util.List;

@Component
public class Booking {
    
    private int bookingId;
    private int homeownerId;
    private int serviceId;
    private String status = "CONFIRMED";

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

    // Databases Stuff
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

    public List<Booking> getAllBookingsByHomeOwner(int homeownerId) {
        return jdbcTemplate.query(GET_ALL_BOOKINGS_BY_HOMEOWNER, listingRowMapper, homeownerId);
    }

    public List<Booking> searchHomeOwnerBookings(int homeownerId, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_HOMEOWNER_BOOKINGS, listingRowMapper, homeownerId, pattern);
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
