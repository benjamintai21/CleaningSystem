package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.ServiceListingDAO;
import com.cleaningsystem.dao.ShortlistDAO;
import com.cleaningsystem.model.CleanerShortlist;
import com.cleaningsystem.model.ServiceShortlist;

@Service
public class ShortlistController {

    @Autowired
	private ShortlistDAO shortlistDAO;

    @Autowired
    private ServiceListingDAO serviceListingDAO;

    public boolean shortlistService(int homeownerId, int serviceId){
        if(serviceListingDAO.updateShortlisting(serviceId)){
            return shortlistDAO.saveToServiceShortlist(homeownerId, serviceId);
        } else {
            return false;
        }
    }

    public boolean shortlistCleaner(int homeownerId, int cleanerId){
        return shortlistDAO.saveToCleanerShortlist(homeownerId, cleanerId);
    }

    public ServiceShortlist viewShortlist(int shortlistId, int homeownerId) {
        return shortlistDAO.getShortlistById(shortlistId, homeownerId);
    }

    public List<ServiceShortlist> searchShortlistedServices(int homeownerId, String keyword) {
        return shortlistDAO.searchShortlistedServices(homeownerId, keyword);
    }

    

    // public boolean DeleteShortlist(int serviceId) {
    //     return shortlistDAO.deleteListing(serviceId);
    // }

    public List<ServiceShortlist> getAllShortlistedServices(int homeownerId) {
        return shortlistDAO.getAllShortlistedServices(homeownerId);
    } 

    public List<CleanerShortlist> getAllShortlistedCleaners(int homeownerId) {
        return shortlistDAO.getAllShortlistedCleaners(homeownerId);
    } 
    
    public List<CleanerShortlist> searchShortlistedCleaners(int homeownerId, String keyword) {
        return shortlistDAO.searchShortlistedCleaners(homeownerId, keyword);
    }

    public List<ServiceShortlist> getNumberOfShortlists(int serviceId){
        return shortlistDAO.getNumberOfShortlists(serviceId);
    }
    
    public boolean isInServiceShortlist(int serviceId) {
        return shortlistDAO.checkShortlistedServices(serviceId);
    }

    public boolean isInCleanerShortlist(int cleanerId) {
        return shortlistDAO.checkShortlistedCleaners(cleanerId);
    }

    public boolean deleteShortlistedServices(int homeownerId, int serviceId) {
        return shortlistDAO.deleteShortlistedServices(homeownerId, serviceId);
    }

    public boolean deleteShortlistedCleaners(int homeownerId, int cleanerId) {
        return shortlistDAO.deleteShortlistedCleaners(homeownerId, cleanerId);
    }

}
