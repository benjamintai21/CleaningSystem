package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.dao.ServiceCategoryDAO;
import com.cleaningsystem.model.ServiceCategory;

@Service
public class ServiceCategoryController {

    @Autowired
	private ServiceCategoryDAO serviceCategoryDAO;

    public List<ServiceCategory> GetAllCategories() {
        return serviceCategoryDAO.getAllCategories();
    }    
}