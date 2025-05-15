package com.cleaningsystem.controller.ServiceCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;

@Service
public class ViewServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;

    public ServiceCategory viewServiceCategory(int categoryId) {
        return serviceCategory.viewServiceCategory(categoryId);
    }

    public ServiceCategory viewServiceCategory(String name) {
        return serviceCategory.viewServiceCategory(name);
    }

}
