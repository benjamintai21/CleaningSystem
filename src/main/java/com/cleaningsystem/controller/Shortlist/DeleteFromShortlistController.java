package com.cleaningsystem.controller.Shortlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceShortlist;

@Service
public class DeleteFromShortlistController {

    @Autowired
    private ServiceShortlist serviceShortlist;

    @Autowired
    private CleanerShortlist cleanerShortlist;

    public boolean deleteShortlistedServices(int homeownerId, int serviceId) {
        return serviceShortlist.deleteShortlistedServices(homeownerId, serviceId);
    }

    public boolean deleteShortlistedCleaners(int homeownerId, int cleanerId) {
        return cleanerShortlist.deleteShortlistedCleaners(homeownerId, cleanerId);
    }
}
