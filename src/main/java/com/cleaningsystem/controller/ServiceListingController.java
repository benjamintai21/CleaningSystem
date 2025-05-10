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
                                        String status, String startDate, String endDate) {
        return serviceListingDAO.insertListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate);
    }

    public ServiceListing viewServiceListing(int cleanerId, int serviceId) {
        return serviceListingDAO.getListingById(cleanerId, serviceId);
    }

    public boolean updateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate, int serviceId) {
        return serviceListingDAO.updateListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate, serviceId);
    }

    public boolean deleteServiceListing(int serviceId) {
        return serviceListingDAO.deleteListing(serviceId);
    }

    public List<ServiceListing> searchServiceListing(int cleanerId, String keyword) {
        return serviceListingDAO.searchMyListings(cleanerId, keyword);
    }

    public List<ServiceListing> getAllListings() {
        return serviceListingDAO.getAllListings();
    }    

    public List<ServiceListing> getServiceListingsByCategory(int categoryId) {
        return serviceListingDAO.getServiceListingsByCategory(categoryId);
    }
}
