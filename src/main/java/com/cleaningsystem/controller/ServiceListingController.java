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

    public boolean createServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String startDate, String endDate, String status) {
        return serviceListing.insertListing(name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status);
    }

    public ServiceListing viewSLandUpdateViewsByServiceId(int serviceId) {
        ServiceListing listing = serviceListing.viewServiceListingByServiceId(serviceId);
        if(serviceListing.updateViews(serviceId)){
            return listing;
        } else {
            return null;
        }
    }

    public ServiceListing viewServiceListing(int serviceId, int cleanerId) {
        return serviceListing.getListingById(serviceId, cleanerId);
    }

    public boolean updateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate, int serviceId) {
        return serviceListing.updateListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate, serviceId);
    }

    public boolean deleteServiceListing(int serviceId) {
        return serviceListing.deleteListing(serviceId);
    }

    public boolean deleteListingByCategory(int categoryId) {
        return serviceListing.deleteListingByCategory(categoryId);
    }

    public List<ServiceListing> searchServiceListing(int cleanerId, String keyword) {
        return serviceListing.searchMyListings(cleanerId, keyword);
    }

    public List<ServiceListing> getAllListingsById (int cleanerId) {
        return serviceListing.getAllListingsById(cleanerId);
    }

    public ServiceListing getListingById (int serviceId, int cleanerId) {
        return serviceListing.getListingById(serviceId, cleanerId);
    }

    public ServiceListing getLastListing() {
        return serviceListing.getLastListing();
    }

    public List<ServiceListing> getServiceListingsByCategory(int categoryId) {
        return serviceListing.getServiceListingsByCategory(categoryId);
    }

    public List<ServiceListing> getAllListings() {
        return serviceListing.getAllListings();
    }

    public List<ServiceListing> searchListingsByService(String keyword) {
        return serviceListing.searchListingsByService(keyword);
    }

    public List<ServiceListing> searchListingsByCleaner(String keyword) {
        return serviceListing.searchListingsByCleaner(keyword);
    }

    public ServiceListing viewServiceListingByServiceId(int serviceId) {
        return serviceListing.viewServiceListingByServiceId(serviceId);
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
