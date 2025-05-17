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

    public boolean isInServiceShortlist(int serviceId, int homeownerId) {
        return serviceShortlist.checkShortlistedServices(serviceId,homeownerId);
    }

    public boolean isInCleanerShortlist(int cleanerId, int homeownerId) {
        return cleanerShortlist.checkShortlistedCleaners(cleanerId,homeownerId);
    }
}
