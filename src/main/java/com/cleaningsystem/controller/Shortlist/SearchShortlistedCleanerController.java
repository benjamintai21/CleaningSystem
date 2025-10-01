package com.cleaningsystem.controller.Shortlist;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cleaningsystem.entity.CleanerShortlist;

@Service
public class SearchShortlistedCleanerController {

    @Autowired
    private CleanerShortlist cleanerShortlist;

    public List<CleanerShortlist> searchShortlistedCleaner(int homeownerId, String keyword) {
        return cleanerShortlist.searchShortlistedCleaner(homeownerId, keyword);
    }

}
