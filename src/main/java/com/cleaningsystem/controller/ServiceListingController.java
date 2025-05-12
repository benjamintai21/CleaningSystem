package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.ServiceListingDAO;
import com.cleaningsystem.model.ServiceListing;

@Service
public class ServiceListingController {

    @Autowired
	private ServiceListingDAO serviceListingDAO;

    public boolean createServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String startDate, String endDate, String status) {
        return serviceListingDAO.insertListing(name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status);
    }

    public ServiceListing viewServiceListingByServiceId(int serviceId) {
        ServiceListing listing = serviceListingDAO.viewServiceListingByServiceId(serviceId);
        if(serviceListingDAO.updateViews(serviceId)){
            return listing;
        } else {
            return null;
        }
    }

    public ServiceListing viewServiceListing(int serviceId, int cleanerId) {
        return serviceListingDAO.getListingById(serviceId, cleanerId);
    }

    public boolean updateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate, int serviceId) {
        return serviceListingDAO.updateListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate, serviceId);
    }

    public boolean deleteServiceListing(int serviceId) {
        return serviceListingDAO.deleteListing(serviceId);
    }

    public boolean deleteListingByCategory(int categoryId) {
        return serviceListingDAO.deleteListingByCategory(categoryId);
    }

    public List<ServiceListing> searchServiceListing(int cleanerId, String keyword) {
        return serviceListingDAO.searchMyListings(cleanerId, keyword);
    }

    public List<ServiceListing> getAllListingsById (int cleanerId) {
        return serviceListingDAO.getAllListingsById(cleanerId);
    }

    public ServiceListing getListingById (int serviceId, int cleanerId) {
        return serviceListingDAO.getListingById(serviceId, cleanerId);
    }

    public ServiceListing getLastListing() {
        return serviceListingDAO.getLastListing();
    }

    public List<ServiceListing> getServiceListingsByCategory(int categoryId) {
        return serviceListingDAO.getServiceListingsByCategory(categoryId);
    }

    public List<ServiceListing> getAllListings() {
        return serviceListingDAO.getAllListings();
    }

    public List<ServiceListing> searchListingsByService(String keyword) {
        return serviceListingDAO.searchListingsByService(keyword);
    }

    public List<ServiceListing> searchListingsByCleaner(String keyword) {
        return serviceListingDAO.searchListingsByCleaner(keyword);
    }

    public ServiceListing getShortlistedServiceListing(int serviceId) {
        return serviceListingDAO.viewServiceListingByServiceId(serviceId);
    }

}
