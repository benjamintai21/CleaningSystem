package com.cleaningsystem.controller.ServiceListing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceListing;

@Service
public class DeleteServiceListingController {

    @Autowired
    private ServiceListing serviceListing;

    public boolean deleteServiceListing(int serviceId) {
        return serviceListing.deleteServiceListing(serviceId);
    }
}
