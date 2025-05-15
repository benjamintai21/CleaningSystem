package com.cleaningsystem.controller.ServiceCategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;

@Service
public class SearchServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;

    public List<ServiceCategory> searchServiceCategory() {
        return serviceCategory.searchServiceCategory();
    }
    
    public List<ServiceCategory> searchServiceCategory(String keyword) {
        return serviceCategory.searchServiceCategory(keyword);
    }
}
