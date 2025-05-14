package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Booking;
import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;
import com.cleaningsystem.entity.UserAccount;

@Service
public class ServiceListingController {

    @Autowired
	private ServiceListing serviceListing;

    public boolean deleteListingByCategory(int categoryId) {
        return serviceListing.deleteListingByCategory(categoryId);
    }

    public ServiceListing getLastListing() {
        return serviceListing.getLastListing();
    }

    public List<ServiceListing> getServiceListingsByCategory(int categoryId) {
        return serviceListing.getServiceListingsByCategory(categoryId);
    }

    public ServiceListing viewServiceListingByServiceId(int serviceId) {
        return serviceListing.viewServiceListingAsHomeOwner(serviceId);
    }

    public List<Integer> getServicesCountList(){
        return serviceListing.getServicesCountList();
    }

    public List<ServiceListing> getServiceListingsByBookings(int uid) {
        return serviceListing.getServiceListingsByBookings(uid);
    }

    public List<Integer> getServiceListingsCount(List<ServiceCategory> serviceCategories) {
        return serviceListing.getServiceListingsCount(serviceCategories);
    }

    public List<ServiceListing> getAllServiceListingsFromShortlist(List<ServiceShortlist> shortlists) {
        return serviceListing.getAllServiceListingsFromShortlist(shortlists);
    }

    public List<Integer> getServiceListingsCountFromShortlist(List<CleanerShortlist> shortlists) {
        return serviceListing.getServiceListingsCountFromShortlist(shortlists);
    }

    public List<ServiceListing> getServiceListingsFromBookings(List<Booking> bookings) {
        return serviceListing.getServiceListingsFromBookings(bookings);
    }

    public List<UserAccount> getCleanerAccountsFromBookings(List<Booking> bookings) {
		return serviceListing.getCleanerAccountsFromBookings(bookings);
	}
}
