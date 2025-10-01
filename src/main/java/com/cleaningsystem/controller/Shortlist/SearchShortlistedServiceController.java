package com.cleaningsystem.controller.Shortlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceShortlist;

@Service
public class SearchShortlistedServiceController {

    @Autowired
    private ServiceShortlist serviceShortlist;

    public List<ServiceShortlist> searchShortlistedService(int homeownerId, String keyword) {
        return serviceShortlist.searchShortlistedService(homeownerId, keyword);
    }

}
