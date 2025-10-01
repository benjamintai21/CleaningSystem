package com.cleaningsystem.controller.ServiceListing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceListing;

@Service
public class SearchServiceListingController {

    @Autowired
    private ServiceListing serviceListing;

    public List<ServiceListing> searchServiceListing() {
        return serviceListing.searchServiceListing();
    }

    public List<ServiceListing> searchServiceListing(String keyword) {
        return serviceListing.searchServiceListing(keyword);
    }

    public List<ServiceListing> searchServiceListing(int cleanerId, String keyword) {
        return serviceListing.searchServiceListing(cleanerId, keyword);
    }
}
