package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cleaningsystem.model.ServiceCategory;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ServiceCategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ServiceListingDAO serviceListingDAO;

    private final RowMapper<ServiceCategory> categoryRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceCategory category = new ServiceCategory();
        category.setCategoryId(rs.getInt("categoryId"));
        category.setType(rs.getString("type"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    };

    public boolean insertServiceCategory(String type,String name, String description){
        int rows_affected = jdbcTemplate.update(CREATE_SERVICE_CATEGORIES, 
                                                type, name, description);

        return rows_affected > 0;
    }

    public ServiceCategory getCategoryById(int categoryId){
        List<ServiceCategory> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_ID, categoryRowMapper, categoryId);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public ServiceCategory getCategoryByName(String name){
        List<ServiceCategory> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, name);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public boolean updateCategory(String type, String name, String description, int categoryId){
        return jdbcTemplate.update(UPDATE_SERVICE_CATEGORY, type, name, description, categoryId) > 0;
    }

    @Transactional
    public boolean deleteCategory(int categoryId){
        try {
            boolean deleteRelatedListings = serviceListingDAO.deleteListingByCategory(categoryId);
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

    public List<ServiceCategory> searchCategoriesByName(String keyword){
        return jdbcTemplate.query(SEARCH_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, "%" + keyword + "%");
    }

    public List<ServiceCategory> getAllCategories(){
        return jdbcTemplate.query(GET_ALL_SERVICE_CATEGORIES, categoryRowMapper);
    } 
}