package com.cleaningsystem.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cleaningsystem.entity.ServiceCategory;

import static com.cleaningsystem.db.Queries.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceCategory {
	
	private int categoryId;
    private String type;
    private String name;
    private String description;

    // Constructors
    public ServiceCategory() {}

    public ServiceCategory(String type, String name, String description){
        this.type = type;
        this.name = name;
        this.description = description;
    }

	public ServiceCategory(int categoryId, String type, String name, String description) {
		this.categoryId = categoryId;
		this.type = type;
		this.name = name;
		this.description = description;
	}
   
    //Getters
    public int getCategoryId(){return categoryId;}
    public String getType(){return type;}
    public String getName(){return name;}
    public String getDescription(){return description;}

    //Setters
    public void setCategoryId(int categoryId){this.categoryId = categoryId;}
    public void setType(String new_type){this.type = new_type;}
    public void setName(String new_name){this.name = new_name;}
    public void setDescription(String new_description){this.description = new_description;}

    // Miscellanous
    public List<String> searchServiceCategoryNamesByServiceListings(List<ServiceListing> serviceListings) {
        List<String> categoriesName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            ServiceCategory category = viewServiceCategory(listing.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        return categoriesName;
	}

    public List<String> searchAllServiceCategoryNames(List<ServiceListing> serviceListings){
        List<String> categoriesName = new ArrayList<>();
        
        for (ServiceListing listing : serviceListings) {
            ServiceCategory category = viewServiceCategory(listing.getCategoryId());
            String categoryType = category.getType();
            String categoryName = category.getName();
            String categoryTypeandName = categoryType + "-" + categoryName;
            categoriesName.add(categoryTypeandName);
        }

        return categoriesName;
    }

    // Databases Stuff
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceListing serviceListing;

    @Autowired
    private Booking booking;

    @Autowired
    private ServiceShortlist serviceShortlist;

    private final RowMapper<ServiceCategory> categoryRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceCategory category = new ServiceCategory();
        category.setCategoryId(rs.getInt("categoryId"));
        category.setType(rs.getString("type"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    };

    public boolean createServiceCategory(String type,String name, String description){
        int rows_affected = jdbcTemplate.update(CREATE_SERVICE_CATEGORIES, 
                                                type, name, description);

        return rows_affected > 0;
    }

    public ServiceCategory viewServiceCategory(int categoryId){
        List<ServiceCategory> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_ID, categoryRowMapper, categoryId);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public ServiceCategory viewServiceCategory(String name){
        List<ServiceCategory> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, name);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public boolean updateServiceCategory(String type, String name, String description, int categoryId){
        return jdbcTemplate.update(UPDATE_SERVICE_CATEGORY, type, name, description, categoryId) > 0;
    }

    @Transactional
    public boolean deleteServiceCategory(int categoryId){
        try {

            boolean deleteRelatedBookings = booking.deleteBookingByCategory(categoryId);
            if(!deleteRelatedBookings){
                throw new RuntimeException("Failed to delete related bookings with category ID: " + categoryId + ".");
            }

            boolean deleteRelatedShortlists = serviceShortlist.deleteShortlistedServicesByCategory(categoryId);
            if(!deleteRelatedShortlists){
                throw new RuntimeException("Failed to delete related service shortlists with category ID: " + categoryId + ".");
            }
            
            boolean deleteRelatedListings = serviceListing.deleteListingByCategory(categoryId);
            if(!deleteRelatedListings){      
                throw new RuntimeException("Failed to delete related service listings with category ID: " + categoryId + ".");
            }
            

            int rowsAffected = jdbcTemplate.update(DELETE_SERVICE_CATEGORY, categoryId);

            if (rowsAffected <= 0) {
                throw new RuntimeException("Failed to delete service category with ID: " + categoryId + ".");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ServiceCategory> searchServiceCategory(String keyword){
        return jdbcTemplate.query(SEARCH_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, "%" + keyword + "%");
    }

    public List<ServiceCategory> searchServiceCategory(){
        return jdbcTemplate.query(GET_ALL_SERVICE_CATEGORIES, categoryRowMapper);
    } 
}