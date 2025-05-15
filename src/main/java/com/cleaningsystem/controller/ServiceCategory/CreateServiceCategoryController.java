package com.cleaningsystem.controller.ServiceCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;

@Service
public class CreateServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;

    public boolean createServiceCategory(String type, String name, String description){
        return serviceCategory.createServiceCategory(type, name, description);
    }
}
