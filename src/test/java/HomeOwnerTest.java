import com.cleaningsystem.controller.Booking.AddtoBookingController;
import com.cleaningsystem.controller.Booking.SearchBookingHistoryController;
import com.cleaningsystem.controller.Booking.ViewBookingHistoryController;
import com.cleaningsystem.controller.Shortlist.*;

import com.cleaningsystem.entity.Booking;
import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeOwnerTest {

    @Mock private Booking booking;
    @Mock private ServiceShortlist serviceShortlist;
    @Mock private CleanerShortlist cleanerShortlist;
    @Mock private ServiceListing serviceListing;

    @InjectMocks private ViewBookingHistoryController viewBookingHistoryController;
    @InjectMocks private SearchBookingHistoryController searchBookingHistoryController;
    @InjectMocks private AddtoBookingController addtoBookingController;

    @InjectMocks private ViewShortlistedServiceController viewShortlistedServiceController;
    @InjectMocks private ViewShortlistedCleanerController viewShortlistedCleanerController;
    @InjectMocks private SearchShortlistedServiceController searchShortlistedServiceController;
    @InjectMocks private SearchShortlistedCleanerController searchShortlistedCleanerController;

    @InjectMocks private InShortlistController inShortlistController;
    @InjectMocks private DeleteFromShortlistController deleteFromShortlistController;
    @InjectMocks private AddToShortlistController addToShortlistController;

    private ServiceShortlist realServiceShortlist;
    private Booking realBooking;
    private CleanerShortlist realCleanerShortlist;

    @Mock private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        realServiceShortlist = new ServiceShortlist();
        realBooking = new Booking();
        realCleanerShortlist = new CleanerShortlist();

        // Inject JdbcTemplate into entities
        var serviceShortlistField = ServiceShortlist.class.getDeclaredField("jdbcTemplate");
        serviceShortlistField.setAccessible(true);
        serviceShortlistField.set(realServiceShortlist, jdbcTemplate);

        var bookingField = Booking.class.getDeclaredField("jdbcTemplate");
        bookingField.setAccessible(true);
        bookingField.set(realBooking, jdbcTemplate);

        var cleanerShortlistField = CleanerShortlist.class.getDeclaredField("jdbcTemplate");
        cleanerShortlistField.setAccessible(true);
        cleanerShortlistField.set(realCleanerShortlist, jdbcTemplate);
    }

    // Controller Tests

    @Test
    void testViewBookingHistory() {
        List<Booking> expected = Collections.singletonList(new Booking());
        when(booking.viewBookingHistory(1)).thenReturn(expected);
        assertEquals(expected, viewBookingHistoryController.viewBookingHistory(1));
    }

    @Test
    void testSearchBookingHistory() {
        List<Booking> expected = Collections.singletonList(new Booking());
        when(booking.searchBookingHistory(1, "keyword")).thenReturn(expected);
        assertEquals(expected, searchBookingHistoryController.searchBookingHistory(1, "keyword"));
    }

    @Test
    void testAddBooking() {
        when(booking.addBooking(1, 2, "CONFIRMED")).thenReturn(true);
        assertTrue(addtoBookingController.addBooking(1, 2, "CONFIRMED"));
    }

    @Test
    void testViewShortlistedService() {
        List<ServiceShortlist> expected = Collections.singletonList(new ServiceShortlist());
        when(serviceShortlist.viewShortlistedService(1)).thenReturn(expected);
        assertEquals(expected, viewShortlistedServiceController.viewShortlistedService(1));
    }

    @Test
    void testViewShortlistedCleaner() {
        List<CleanerShortlist> expected = Collections.singletonList(new CleanerShortlist());
        when(cleanerShortlist.viewShortlistedCleaner(1)).thenReturn(expected);
        assertEquals(expected, viewShortlistedCleanerController.viewShortlistedCleaner(1));
    }

    @Test
    void testSearchShortlistedService() {
        List<ServiceShortlist> expected = Collections.singletonList(new ServiceShortlist());
        when(serviceShortlist.searchShortlistedService(1, "cleaning")).thenReturn(expected);
        assertEquals(expected, searchShortlistedServiceController.searchShortlistedService(1, "cleaning"));
    }

    @Test
    void testSearchShortlistedCleaner() {
        List<CleanerShortlist> expected = Collections.singletonList(new CleanerShortlist());
        when(cleanerShortlist.searchShortlistedCleaner(1, "vacuum")).thenReturn(expected);
        assertEquals(expected, searchShortlistedCleanerController.searchShortlistedCleaner(1, "vacuum"));
    }

    @Test
    void testIsInServiceShortlist() {
        when(serviceShortlist.checkShortlistedServices(5, 0)).thenReturn(true);
        assertTrue(inShortlistController.isInServiceShortlist(5, 0));
    }

    @Test
    void testIsInCleanerShortlist() {
        when(cleanerShortlist.checkShortlistedCleaners(3, 0)).thenReturn(true);
        assertTrue(inShortlistController.isInCleanerShortlist(3, 0));
    }

    @Test
    void testDeleteShortlistedServices() {
        when(serviceShortlist.deleteShortlistedServices(1, 10)).thenReturn(true);
        assertTrue(deleteFromShortlistController.deleteShortlistedServices(1, 10));
    }

    @Test
    void testDeleteShortlistedCleaners() {
        when(cleanerShortlist.deleteShortlistedCleaners(2, 20)).thenReturn(true);
        assertTrue(deleteFromShortlistController.deleteShortlistedCleaners(2, 20));
    }

    @Test
    void testShortlistServiceSuccess() {
        when(serviceListing.updateShortlisting(10)).thenReturn(true);
        when(serviceShortlist.shortlistService(1, 10)).thenReturn(true);
        assertTrue(addToShortlistController.shortlistService(1, 10));
    }

    @Test
    void testShortlistServiceFailure() {
        when(serviceListing.updateShortlisting(10)).thenReturn(false);
        assertFalse(addToShortlistController.shortlistService(1, 10));
    }

    @Test
    void testShortlistCleaner() {
        when(cleanerShortlist.shortlistCleaner(1, 5)).thenReturn(true);
        assertTrue(addToShortlistController.shortlistCleaner(1, 5));
    }

    // ServiceShortlistEntity 

    @Test
    void testShortlistService_Success() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(2))).thenReturn(1);
        assertTrue(realServiceShortlist.shortlistService(1, 2));
    }

    // @Test
    // void testShortlistService_Failure() {
    //     when(jdbcTemplate.update(anyString(), eq(1), eq(2))).thenReturn(0);
    //     assertFalse(realServiceShortlist.shortlistService(1, 2));
    // }

    @Test
    void testViewShortlistedService_Entity() {
        ServiceShortlist item = new ServiceShortlist(1, 2);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
            .thenReturn(Collections.singletonList(item));
        List<ServiceShortlist> result = realServiceShortlist.viewShortlistedService(1);
        assertEquals(1, result.size());
    }

    @Test
    void testSearchShortlistedService_Entity() {
        ServiceShortlist item = new ServiceShortlist(1, 2);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), eq("%clean%")))
            .thenReturn(Collections.singletonList(item));
        List<ServiceShortlist> result = realServiceShortlist.searchShortlistedService(1, "clean");
        assertEquals(1, result.size());
    }

    @Test
    void testGetNumberOfViews() {
        ServiceShortlist item = new ServiceShortlist(1, 2);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(5)))
            .thenReturn(Collections.singletonList(item));
        List<ServiceShortlist> result = realServiceShortlist.getNumberofViews(5);
        assertEquals(1, result.size());
    }

    // @Test
    // void testCheckShortlistedServices_True() {
    //     when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(2)))
    //         .thenReturn(Collections.singletonList(new ServiceShortlist()));
    //     assertTrue(realServiceShortlist.checkShortlistedServices(2, 0));
    // }

    @Test
    void testCheckShortlistedServices_False() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(2)))
            .thenReturn(Collections.emptyList());
        assertFalse(realServiceShortlist.checkShortlistedServices(2, 0));
    }

    @Test
    void testDeleteShortlistedServices_Entity_Success() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(2))).thenReturn(1);
        assertTrue(realServiceShortlist.deleteShortlistedServices(1, 2));
    }

    // @Test
    // void testDeleteShortlistedServices_Entity_Failure() {
    //     when(jdbcTemplate.update(anyString(), eq(1), eq(2))).thenReturn(0);
    //     assertFalse(realServiceShortlist.deleteShortlistedServices(1, 2));
    // }

    // CleanerShortlistEntity Tests
    @Test
    void testShortlistCleaner_Entity_Success() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(5))).thenReturn(1);
        assertTrue(realCleanerShortlist.shortlistCleaner(1, 5));
    }

    @Test
    void testShortlistCleaner_Entity_Failure() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(5))).thenReturn(0);
        assertFalse(realCleanerShortlist.shortlistCleaner(1, 5));
    }

    @Test
    void testViewShortlistedCleaner_Entity() {
        CleanerShortlist item = new CleanerShortlist(1, 5);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
            .thenReturn(Collections.singletonList(item));
        List<CleanerShortlist> result = realCleanerShortlist.viewShortlistedCleaner(1);
        assertEquals(1, result.size());
    }

    @Test
    void testSearchShortlistedCleaner_Entity() {
        CleanerShortlist item = new CleanerShortlist(1, 5);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), eq("%john%")))
            .thenReturn(Collections.singletonList(item));
        List<CleanerShortlist> result = realCleanerShortlist.searchShortlistedCleaner(1, "john");
        assertEquals(1, result.size());
    }

    // @Test
    // void testCheckShortlistedCleaners_True() {
    //     when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(5)))
    //         .thenReturn(Collections.singletonList(new CleanerShortlist()));
    //     assertTrue(realCleanerShortlist.checkShortlistedCleaners(5, 0));
    // }

    @Test
    void testCheckShortlistedCleaners_False() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(5)))
            .thenReturn(Collections.emptyList());
        assertFalse(realCleanerShortlist.checkShortlistedCleaners(5, 0));
    }

    @Test
    void testDeleteShortlistedCleaners_Entity_Success() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(5))).thenReturn(1);
        assertTrue(realCleanerShortlist.deleteShortlistedCleaners(1, 5));
    }

    @Test
    void testDeleteShortlistedCleaners_Entity_Failure() {
        when(jdbcTemplate.update(anyString(), eq(1), eq(5))).thenReturn(0);
        assertFalse(realCleanerShortlist.deleteShortlistedCleaners(1, 5));
    }

    //test booking Entity
    @Test
    void testAddBooking_Entity_Success() {
        when(jdbcTemplate.update(anyString(), eq(2), eq(1), eq("CONFIRMED"))).thenReturn(1);
        assertTrue(realBooking.addBooking(2, 1, "CONFIRMED"));
    }

    @Test
    void testAddBooking_Entity_Failure() {
        when(jdbcTemplate.update(anyString(), eq(2), eq(1), eq("CONFIRMED"))).thenReturn(0);
        assertFalse(realBooking.addBooking(2, 1, "CONFIRMED"));
    }

    @Test
    void testViewBookingHistory_Entity() {
        Booking booking = new Booking(1, 1, 2, "CONFIRMED");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
            .thenReturn(Collections.singletonList(booking));
        List<Booking> result = realBooking.viewBookingHistory(1);
        assertEquals(1, result.size());
        assertEquals("CONFIRMED", result.get(0).getStatus());
    }
    @Test
    void testViewCompletedBooking_Entity() {
        Booking booking = new Booking(1, 1, 2, "COMPLETED");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(2)))
            .thenReturn(Collections.singletonList(booking));
        List<Booking> result = realBooking.viewCompletedBooking(2);
        assertEquals(1, result.size());
        assertEquals("COMPLETED", result.get(0).getStatus());
    }

    @Test
    void testSearchCompletedBooking_Entity() {
        Booking booking = new Booking(1, 1, 2, "COMPLETED");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(2), eq("%deep clean%")))
            .thenReturn(Collections.singletonList(booking));
        List<Booking> result = realBooking.searchCompletedBooking(2, "deep clean");
        assertEquals(1, result.size());
        assertEquals("COMPLETED", result.get(0).getStatus());
    }

    @Test
    void testSearchBookingHistory_Entity() {
        Booking booking = new Booking(1, 1, 2, "COMPLETED");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), eq("%cleaning%")))
            .thenReturn(Collections.singletonList(booking));
        List<Booking> result = realBooking.searchBookingHistory(1, "cleaning");
        assertEquals(1, result.size());
        assertEquals("COMPLETED", result.get(0).getStatus());
}

}
