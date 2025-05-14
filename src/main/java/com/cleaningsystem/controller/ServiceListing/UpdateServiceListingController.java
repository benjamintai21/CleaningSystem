package com.cleaningsystem.controller.ServiceListing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceListing;

@Service
public class UpdateServiceListingController {

    @Autowired
    private ServiceListing serviceListing;


    public boolean updateServiceListing(String name, int cleanerId, int categoryId, String description, double price_per_hour, 
                                        String status, String startDate, String endDate, int serviceId) {
        return serviceListing.updateServiceListing(name, cleanerId, categoryId, description, price_per_hour, status, startDate, endDate, serviceId);
    }

}
