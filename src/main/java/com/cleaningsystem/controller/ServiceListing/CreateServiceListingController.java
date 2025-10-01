package com.cleaningsystem.controller.ServiceListing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceListing;

@Service
public class CreateServiceListingController {
    
    @Autowired
    private ServiceListing serviceListing;

    public boolean createServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String startDate, String endDate, String status) {
        return serviceListing.createServiceListing(name, cleanerId, categoryId, description, price_per_hour, startDate, endDate, status);
    }
}
