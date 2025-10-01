package com.cleaningsystem.controller.ServiceCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;

@Service
public class DeleteServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;
    
    public boolean deleteServiceCategory(int serviceId) {
        return serviceCategory.deleteServiceCategory(serviceId);
    }
}
