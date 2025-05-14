package com.cleaningsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.ServiceCategory;
import com.cleaningsystem.entity.ServiceListing;

@Service
public class ServiceCategoryController {

    @Autowired
    private ServiceCategory serviceCategory;

    public boolean createServiceCategory(String type, String name, String description){
        return serviceCategory.insertServiceCategory(type, name, description);
    }

    public ServiceCategory viewServiceCategory(int categoryId) {
        return serviceCategory.getCategoryById(categoryId);
    }

    public ServiceCategory viewServiceCategory(String name) {
        return serviceCategory.getCategoryByName(name);
    }

    public boolean updateServiceCategory(String type, String name, String description, int categoryId){
        return serviceCategory.updateCategory(type, name, description, categoryId);
    }

    public boolean deleteServiceCategory(int serviceId) {
        return serviceCategory.deleteCategory(serviceId);
    }

    public List<ServiceCategory> searchServiceCategory(String keyword) {
        return serviceCategory.searchCategoriesByName(keyword);
    }

    public List<ServiceCategory> getAllCategories() {
        return serviceCategory.getAllCategories();
    }

    public List<String> getAllCategoryNamesByServiceListings(List<ServiceListing> serviceListings) {
        return serviceCategory.getAllCategoryNamesByServiceListings(serviceListings);
	}

    public List<String> getCategoriesName(List<ServiceListing> serviceListings){
        return serviceCategory.getCategoriesName(serviceListings);
    }

}