package com.cleaningsystem.controller.ServiceCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;

@Service
public class SearchServiceCategoryNamesController {

    @Autowired
    private ServiceCategory serviceCategory;

    public List<String> searchServiceCategoryNamesByServiceListings(List<ServiceListing> serviceListings) {
        return serviceCategory.searchServiceCategoryNamesByServiceListings(serviceListings);
	}

    public List<String> searchAllServiceCategoryNames(List<ServiceListing> serviceListings){
        return serviceCategory.searchAllServiceCategoryNames(serviceListings);
    }
}
