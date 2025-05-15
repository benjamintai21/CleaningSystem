package com.cleaningsystem.controller.ServiceListing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceListing;

@Service
public class ViewServiceListingController {
    
    @Autowired
    private ServiceListing serviceListing;

    public ServiceListing viewServiceListingAsHomeOwner(int serviceId) {
        ServiceListing listing = serviceListing.viewServiceListingAsHomeOwner(serviceId);
        if(serviceListing.updateViews(serviceId)){
            return listing;
        } else {
            return null;
        }
    }
    
    public ServiceListing viewServiceListing(int serviceId, int cleanerId) {
        return serviceListing.viewServiceListing(serviceId, cleanerId);
    }

    public ServiceListing viewServiceListing() {
        return serviceListing.viewServiceListing();
    }
}
