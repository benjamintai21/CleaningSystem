package com.cleaningsystem.controller.ServiceCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;

@Service
public class UpdateServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;

    public boolean updateServiceCategory(String type, String name, String description, int categoryId){
        return serviceCategory.updateServiceCategory(type, name, description, categoryId);
    }
}
