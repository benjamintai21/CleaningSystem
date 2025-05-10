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

    public boolean CreateServiceCategory(String type, String name, String description){
        return serviceCategoryDAO.insertServiceCategory(type, name, description);
    }

    public ServiceCategory ViewServiceCategory(int categoryId) {
        return serviceCategoryDAO.getCategoryById(categoryId);
    }

    public ServiceCategory ViewServiceCategory(String name) {
        return serviceCategoryDAO.getCategoryByName(name);
    }

    public boolean UpdateServiceCategory(String type, String name, String description){
        return serviceCategoryDAO.updateCategory(type, name, description);
    }

    public boolean DeleteServiceCategory(int serviceId) {
        return serviceCategoryDAO.deleteCategory(serviceId);
    }

    public List<ServiceCategory> SearchServiceCategory(String keyword) {
        return serviceCategoryDAO.searchCategoriesByName(keyword);
    }

    public List<ServiceCategory> getAllCategories() {
        return serviceCategoryDAO.getAllCategories();
    }    
}