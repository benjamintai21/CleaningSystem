package com.cleaningsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.ServiceShortlist;

@Service
public class ShortlistController {

    @Autowired
	private ServiceShortlist serviceShortlist;

    @Autowired
	private CleanerShortlist cleanerShortlist;

    @Autowired
    private ServiceListing serviceListing;

    public boolean shortlistService(int homeownerId, int serviceId){
        if(serviceListing.updateShortlisting(serviceId)){
            return serviceShortlist.saveToServiceShortlist(homeownerId, serviceId);
        } else {
            return false;
        }
    }

    public boolean shortlistCleaner(int homeownerId, int cleanerId){
        return cleanerShortlist.saveToCleanerShortlist(homeownerId, cleanerId);
    }

    public ServiceShortlist viewShortlist(int shortlistId, int homeownerId) {
        return serviceShortlist.getShortlistById(shortlistId, homeownerId);
    }

    public List<ServiceShortlist> searchShortlistedServices(int homeownerId, String keyword) {
        return serviceShortlist.searchShortlistedServices(homeownerId, keyword);
    }

    

    // public boolean DeleteShortlist(int serviceId) {
    //     return shortlistDAO.deleteListing(serviceId);
    // }

    public List<ServiceShortlist> getAllShortlistedServices(int homeownerId) {
        return serviceShortlist.getAllShortlistedServices(homeownerId);
    } 

    public List<CleanerShortlist> getAllShortlistedCleaners(int homeownerId) {
        return cleanerShortlist.getAllShortlistedCleaners(homeownerId);
    } 
    
    public List<CleanerShortlist> searchShortlistedCleaners(int homeownerId, String keyword) {
        return cleanerShortlist.searchShortlistedCleaners(homeownerId, keyword);
    }

    public List<ServiceShortlist> getNumberOfShortlists(int serviceId){
        return serviceShortlist.getNumberOfShortlists(serviceId);
    }
    
    public boolean isInServiceShortlist(int serviceId) {
        return serviceShortlist.checkShortlistedServices(serviceId);
    }

    public boolean isInCleanerShortlist(int cleanerId) {
        return cleanerShortlist.checkShortlistedCleaners(cleanerId);
    }

    public boolean deleteShortlistedServices(int homeownerId, int serviceId) {
        return serviceShortlist.deleteShortlistedServices(homeownerId, serviceId);
    }

    public boolean deleteShortlistedCleaners(int homeownerId, int cleanerId) {
        return cleanerShortlist.deleteShortlistedCleaners(homeownerId, cleanerId);
    }

}
