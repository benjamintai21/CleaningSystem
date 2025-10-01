package com.cleaningsystem.controller.Shortlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.CleanerShortlist;

@Service
public class ViewShortlistedCleanerController {

    @Autowired
    private CleanerShortlist cleanerShortlist;

    public List<CleanerShortlist> viewShortlistedCleaner(int homeownerId) {
        return cleanerShortlist.viewShortlistedCleaner(homeownerId);
    } 
}
