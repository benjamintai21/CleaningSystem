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

    public boolean CreateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate) {
        return serviceListingDAO.createListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate);
    }

    public ServiceListing ViewServiceListing(int cleanerId, int serviceId) {
        return serviceListingDAO.getListingById(cleanerId, serviceId);
    }

    public boolean UpdateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate, int serviceId) {
        return serviceListingDAO.updateListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate, serviceId);
    }

    public boolean DeleteServiceListing(int serviceId) {
        return serviceListingDAO.deleteListing(serviceId);
    }

    public List<ServiceListing> SearchServiceListing(int cleanerId, String keyword) {
        return serviceListingDAO.searchMyListings(cleanerId, keyword);
    }


    public List<ServiceListing> GetAllListings() {
        return serviceListingDAO.getAllListings();
    }    
}
