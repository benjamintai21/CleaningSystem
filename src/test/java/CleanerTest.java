import com.cleaningsystem.controller.Booking.SearchCompletedBookingController;
import com.cleaningsystem.controller.Booking.ViewCompletedBookingController;
import com.cleaningsystem.controller.ServiceListing.*;
import com.cleaningsystem.entity.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CleanerTest {

    @Mock
    private ServiceListing serviceListing;

    @Mock
    private Booking booking;

    @InjectMocks
    private CreateServiceListingController createController;

    @InjectMocks
    private DeleteServiceListingController deleteController;

    @InjectMocks
    private OthersServiceListingController othersController;

    @InjectMocks
    private SearchServiceListingController searchServiceController;

    @InjectMocks
    private UpdateServiceListingController updateController;

    @InjectMocks
    private ViewServiceListingController viewController;

    @InjectMocks
    private SearchCompletedBookingController searchBookingController;

    @InjectMocks
    private ViewCompletedBookingController viewBookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
//serviceListing_Controller
    @Test
    void testCreateServiceListing() {
        when(serviceListing.createServiceListing(any(), anyInt(), anyInt(), any(), anyDouble(), any(), any(), any()))
                .thenReturn(true);
        assertTrue(createController.createServiceListing("Test", 1, 1, "Desc", 25.0, "2025-01-01", "2025-01-10", "active"));
    }

    @Test
    void testDeleteServiceListing() {
        when(serviceListing.deleteServiceListing(1)).thenReturn(true);
        assertTrue(deleteController.deleteServiceListing(1));
    }

    @Test
    void testGetServicesCountList() {
        List<Integer> expected = Arrays.asList(1, 2, 3);
        when(serviceListing.getServicesCountList()).thenReturn(expected);
        assertEquals(expected, othersController.getServicesCountList());
    }

    @Test
    void testGetServiceListingsByBookings() {
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.getServiceListingsByBookings(1)).thenReturn(expected);
        assertEquals(expected, othersController.getServiceListingsByBookings(1));
    }

    @Test
    void testGetServiceListingsCount() {
        List<ServiceCategory> categories = Collections.singletonList(new ServiceCategory());
        List<Integer> expected = Arrays.asList(1, 2);
        when(serviceListing.getServiceListingsCount(categories)).thenReturn(expected);
        assertEquals(expected, othersController.getServiceListingsCount(categories));
    }

    @Test
    void testGetAllServiceListingsFromShortlist() {
        List<ServiceShortlist> shortlists = Collections.singletonList(new ServiceShortlist());
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.getAllServiceListingsFromShortlist(shortlists)).thenReturn(expected);
        assertEquals(expected, othersController.getAllServiceListingsFromShortlist(shortlists));
    }

    @Test
    void testGetServiceListingsCountFromShortlist() {
        List<CleanerShortlist> shortlists = Collections.singletonList(new CleanerShortlist());
        List<Integer> expected = Collections.singletonList(1);
        when(serviceListing.getServiceListingsCountFromShortlist(shortlists)).thenReturn(expected);
        assertEquals(expected, othersController.getServiceListingsCountFromShortlist(shortlists));
    }

    @Test
    void testGetServiceListingsFromBookings() {
        List<Booking> bookings = Collections.singletonList(new Booking());
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.getServiceListingsFromBookings(bookings)).thenReturn(expected);
        assertEquals(expected, othersController.getServiceListingsFromBookings(bookings));
    }

    @Test
    void testGetCleanerAccountsFromBookings() {
        List<Booking> bookings = Collections.singletonList(new Booking());
        List<UserAccount> expected = Collections.singletonList(new UserAccount());
        when(serviceListing.getCleanerAccountsFromBookings(bookings)).thenReturn(expected);
        assertEquals(expected, othersController.getCleanerAccountsFromBookings(bookings));
    }

    @Test
    void testSearchServiceListing() {
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.searchServiceListing()).thenReturn(expected);
        assertEquals(expected, searchServiceController.searchServiceListing());
    }

    @Test
    void testSearchServiceListingByKeyword() {
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.searchServiceListing("keyword")).thenReturn(expected);
        assertEquals(expected, searchServiceController.searchServiceListing("keyword"));
    }

    @Test
    void testSearchServiceListingByCleanerAndKeyword() {
        List<ServiceListing> expected = Collections.singletonList(new ServiceListing());
        when(serviceListing.searchServiceListing(1, "key")).thenReturn(expected);
        assertEquals(expected, searchServiceController.searchServiceListing(1, "key"));
    }

    @Test
    void testUpdateServiceListing() {
        when(serviceListing.updateServiceListing(any(), anyInt(), anyInt(), any(), anyDouble(), any(), any(), any(), anyInt()))
                .thenReturn(true);
        assertTrue(updateController.updateServiceListing("Updated", 1, 1, "Updated Desc", 30.0, "active", "2025-01-01", "2025-01-10", 1));
    }

    @Test
    void testViewServiceListingAsHomeOwner() {
        ServiceListing listing = new ServiceListing();
        when(serviceListing.viewServiceListingAsHomeOwner(1)).thenReturn(listing);
        when(serviceListing.updateViews(1)).thenReturn(true);
        assertEquals(listing, viewController.viewServiceListingAsHomeOwner(1));
    }

    @Test
    void testViewServiceListingAsHomeOwnerReturnsNullWhenUpdateFails() {
        when(serviceListing.viewServiceListingAsHomeOwner(1)).thenReturn(new ServiceListing());
        when(serviceListing.updateViews(1)).thenReturn(false);
        assertNull(viewController.viewServiceListingAsHomeOwner(1));
    }

    @Test
    void testViewServiceListingWithCleanerId() {
        ServiceListing listing = new ServiceListing();
        when(serviceListing.viewServiceListing(1, 2)).thenReturn(listing);
        assertEquals(listing, viewController.viewServiceListing(1, 2));
    }

    @Test
    void testViewServiceListingDefault() {
        ServiceListing listing = new ServiceListing();
        when(serviceListing.viewServiceListing()).thenReturn(listing);
        assertEquals(listing, viewController.viewServiceListing());
    }
//Booking_Controller
    @Test
    void testSearchCompletedBooking() {
        List<Booking> expected = Collections.singletonList(new Booking());
        when(booking.searchCompletedBooking(1, "search")).thenReturn(expected);
        assertEquals(expected, searchBookingController.searchCompletedBooking(1, "search"));
    }

    @Test
    void testViewCompletedBooking() {
        List<Booking> expected = Collections.singletonList(new Booking());
        when(booking.viewCompletedBooking(1)).thenReturn(expected);
        assertEquals(expected, viewBookingController.viewCompletedBooking(1));
    }
//Service Listing Entity test
@Test
public void testServiceListingEntityFields() {
    ServiceListing listing = new ServiceListing();
    
    listing.setServiceId(1);
    listing.setName("Test Service");
    listing.setCleanerId(10);
    listing.setCategoryId(5);
    listing.setDescription("Test Description");
    listing.setPricePerHour(25.5);
    listing.setStartDate("2023-11-11");
    listing.setEndDate("2023-12-31");
    listing.setStatus("AVAILABLE");
    listing.setViews(110);
    listing.setShortlists(210);

    assertEquals(1, listing.getServiceId());
    assertEquals("Test Service", listing.getName());
    assertEquals(10, listing.getCleanerId());
    assertEquals(5, listing.getCategoryId());
    assertEquals("Test Description", listing.getDescription());
    assertEquals(25.5, listing.getPricePerHour());
    assertEquals("2023-11-11", listing.getStartDate());
    assertEquals("2023-12-31", listing.getEndDate());
    assertEquals("AVAILABLE", listing.getStatus());
    assertEquals(110, listing.getViews());
    assertEquals(210, listing.getShortlists());
}
}

