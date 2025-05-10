package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.ShortlistDAO;
import com.cleaningsystem.model.Shortlist;

@Service
public class ShortlistController {

    @Autowired
	private ShortlistDAO shortlistDAO;

    public boolean ShortlistCleaner(int homeownerId, int serviceId){
        return shortlistDAO.saveToShortlist(homeownerId, serviceId);
    }

    public Shortlist ViewShortlist(int shortlistId, int homeownerId) {
        return shortlistDAO.getShortlistById(shortlistId, homeownerId);
    }

    public List<Shortlist> SearchShortlist(int homeownerId, String keyword) {
        return shortlistDAO.searchShortlistedService(homeownerId, keyword);
    }

    // public boolean DeleteShortlist(int serviceId) {
    //     return shortlistDAO.deleteListing(serviceId);
    // }

    public List<Shortlist> GetAllShortlists(int homeownerId) {
        return shortlistDAO.getAllShortlists(homeownerId);
    }    
}
