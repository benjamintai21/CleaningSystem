package com.cleaningsystem.controller.Shortlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;

@Service
public class AddToShortlistController {

    @Autowired
	private ServiceShortlist serviceShortlist;

    @Autowired
	private CleanerShortlist cleanerShortlist;

    @Autowired
    private ServiceListing serviceListing;

    public boolean shortlistService(int homeownerId, int serviceId){
        
            
        if(serviceListing.updateShortlisting(serviceId)){
            return serviceShortlist.shortlistService(homeownerId, serviceId);
        } else {
            return false;
        }
    }

    public boolean shortlistCleaner(int homeownerId, int cleanerId){
        return cleanerShortlist.shortlistCleaner(homeownerId, cleanerId);
    }
}
