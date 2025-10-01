import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.jdbc.core.RowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cleaningsystem.controller.LoginLogout.LoginController;
import com.cleaningsystem.controller.LoginLogout.LogoutController;
import com.cleaningsystem.entity.Booking;
import com.cleaningsystem.entity.Report;
import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

import java.time.LocalDate;
import java.util.*;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)

public class PlatformManagerTest {

    @InjectMocks
    private ServiceCategory serviceCategory;

    @InjectMocks
    private Report report;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private UserProfile userProfile;

    private LocalDate testDate;

    @Mock
    private Booking booking;

    @Mock
    private ServiceListing serviceListing;

    @Mock
    private ServiceShortlist serviceShortlist;

    @BeforeEach
    public void setup() {
        testDate = LocalDate.of(2025, 5, 1);
    }

    @Test
    public void testViewServiceCategoryById_Found() {
        ServiceCategory expected = new ServiceCategory(1, "Cleaning", "Floor", "Description");
    
        // Use a typed RowMapper<ServiceCategory>
        RowMapper<ServiceCategory> rowMapper = (rs, rowNum) -> expected;
    
        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<ServiceCategory>>any(), eq(1)))
                .thenReturn(List.of(expected));
    
        ServiceCategory result = serviceCategory.viewServiceCategory(1);
    
        assertEquals("Floor", result.getName());
    }
    

    @Test
    public void testUpdateServiceCategory_Success() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString(), anyInt())).thenReturn(1);

        boolean updated = serviceCategory.updateServiceCategory("Cleaning", "Carpet",
                                                         "Deep clean", 2);
        assertTrue(updated);
    }

    @Test
    public void testDeleteServiceCategory_Success() {
        when(booking.deleteBookingByCategory(1)).thenReturn(true);
        when(serviceShortlist.deleteShortlistedServicesByCategory(1)).thenReturn(true);
        when(serviceListing.deleteListingByCategory(1)).thenReturn(true);
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);

        boolean deleted = serviceCategory.deleteServiceCategory(1);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteServiceCategory_FailDueToBooking() {
        when(booking.deleteBookingByCategory(1)).thenReturn(false); // simulate failure

        boolean deleted = serviceCategory.deleteServiceCategory(1);
        assertFalse(deleted);
    }

    @Test
    public void testSearchServiceCategory_ByKeyword() {
        ServiceCategory cat = new ServiceCategory(1, "TypeA", "Gardening", "Lawn care");
    
        // Suppress raw type warning by specifying the RowMapper type
        when(jdbcTemplate.query(
                anyString(),
                ArgumentMatchers.<RowMapper<ServiceCategory>>any(),
                eq("%garden%"))
        ).thenReturn(List.of(cat));
    
        List<ServiceCategory> results = serviceCategory.searchServiceCategory("garden");
    
        assertEquals(1, results.size());
        assertEquals("Gardening", results.get(0).getName());
    }
    

    @Test
    public void testSearchServiceCategory_All() {
        List<ServiceCategory> expected = List.of(
                new ServiceCategory(1, "Type1", "Name1", "Desc1"),
                new ServiceCategory(2, "Type2", "Name2", "Desc2")
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expected);

        List<ServiceCategory> result = serviceCategory.searchServiceCategory();
        assertEquals(2, result.size());
    }

    // Generate Report

    @Test
    public void testGenerateDailyReport() {
        // Arrange
        when(userProfile.getProfileIdByName("Home Owner")).thenReturn(1);
        when(userProfile.getProfileIdByName("Cleaner")).thenReturn(2);

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(Object[].class)))
            .thenReturn(5); // Simulate every query returning 5

        when(jdbcTemplate.update(anyString(), any(Object[].class)))
            .thenReturn(1); // Simulate report creation success

        // Act
        Report result = report.generateDailyReport(testDate);

        // Assert
        assertNotNull(result);
        assertEquals("DAILY", result.getType());
        assertEquals(5, result.getNewHomeOwners());
        assertEquals(5, result.getTotalHomeOwners());
        assertEquals(5, result.getNewCleaners());
        assertEquals(5, result.getTotalCleaners());
        assertEquals(5, result.getNoOfShortlists());
        assertEquals(5, result.getNoOfBookings());
    }

    @Test
    public void testGenerateWeeklyReport() {
        when(userProfile.getProfileIdByName("Home Owner")).thenReturn(1);
        when(userProfile.getProfileIdByName("Cleaner")).thenReturn(2);

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(Object[].class)))
            .thenReturn(10); // Simulate all values as 10

        when(jdbcTemplate.update(anyString(), any(Object[].class)))
            .thenReturn(1);

        Report result = report.generateWeeklyReport(testDate);

        assertNotNull(result);
        assertEquals("WEEKLY", result.getType());
        assertEquals(10, result.getNewHomeOwners());
        assertEquals(10, result.getTotalHomeOwners());
        assertEquals(10, result.getNewCleaners());
        assertEquals(10, result.getTotalCleaners());
        assertEquals(10, result.getNoOfShortlists());
        assertEquals(10, result.getNoOfBookings());
    }

    @Test
    public void testGenerateMonthlyReport() {
        when(userProfile.getProfileIdByName("Home Owner")).thenReturn(1);
        when(userProfile.getProfileIdByName("Cleaner")).thenReturn(2);

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any(Object[].class)))
            .thenReturn(20); // Simulate all values as 20

        when(jdbcTemplate.update(anyString(), any(Object[].class)))
            .thenReturn(1);

        Report result = report.generateMonthlyReport(testDate);

        assertNotNull(result);
        assertEquals("MONTHLY", result.getType());
        assertEquals(20, result.getNewHomeOwners());
        assertEquals(20, result.getTotalHomeOwners());
        assertEquals(20, result.getNewCleaners());
        assertEquals(20, result.getTotalCleaners());
        assertEquals(20, result.getNoOfShortlists());
        assertEquals(20, result.getNoOfBookings());
    }
}



