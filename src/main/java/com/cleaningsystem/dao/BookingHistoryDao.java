package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceListing;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Date;
import com.cleaningsystem.model.BookingHistory;
@Repository
public class BookingHistoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<BookingHistory> listingRowMapper = (ResultSet rs, int rowNum) -> {
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setHistoryId(rs.getInt("historyId"));
        bookingHistory.setHomeownerId(rs.getInt("homeownerId"));
        bookingHistory.setServiceId(rs.getInt("serviceId"));
        bookingHistory.setStatus(rs.getString("status"));
        return bookingHistory;
    };

    public List<BookingHistory> getCompletedServices(int homeownerId) {
        return jdbcTemplate.query(GET_COMPLETED_SERVICES, listingRowMapper, homeownerId);
    }      
    
    public List<BookingHistory> getPastBookings(int homeownerUID) {
        return jdbcTemplate.query(GET_COMPLETED_SERVICES, listingRowMapper, homeownerUID);
    }

    public List<BookingHistory> searchPastBookings(int homeownerUID, String keyword) {
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(SEARCH_PAST_BOOKINGS, listingRowMapper, homeownerUID, pattern);
    }
}