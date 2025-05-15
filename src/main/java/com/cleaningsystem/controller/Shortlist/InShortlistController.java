package com.cleaningsystem.controller.Shortlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceShortlist;

@Service
public class InShortlistController {

    @Autowired
	private ServiceShortlist serviceShortlist;

    @Autowired
	private CleanerShortlist cleanerShortlist;

    public boolean isInServiceShortlist(int serviceId) {
        return serviceShortlist.checkShortlistedServices(serviceId);
    }

    public boolean isInCleanerShortlist(int cleanerId) {
        return cleanerShortlist.checkShortlistedCleaners(cleanerId);
    }
}
