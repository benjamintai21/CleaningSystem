package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;
import com.cleaningsystem.model.Booking;

@Repository
public class BookingDao {
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
    
    public List<Booking> getPastBookings(int homeownerUID) {
        return jdbcTemplate.query(GET_COMPLETED_SERVICES, listingRowMapper, homeownerUID);
    }

    public List<Booking> searchPastBookings(int homeownerUID, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_PAST_BOOKINGS, listingRowMapper, homeownerUID, pattern);
    }
}