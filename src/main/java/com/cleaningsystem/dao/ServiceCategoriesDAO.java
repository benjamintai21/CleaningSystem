package com.cleaningsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.cleaningsystem.model.ServiceCategories;
import static com.cleaningsystem.dao.Queries.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ServiceCategoriesDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ServiceCategories> categoryRowMapper = (ResultSet rs, int rowNum) -> {
        ServiceCategories category = new ServiceCategories();
        category.setCategoryId(rs.getInt("categoryId"));
        category.setType(rs.getString("type"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    };

    public boolean insertServiceCategories(ServiceCategories category){
        int rows_affected = jdbcTemplate.update(CREATE_SERVICE_CATEGORIES, 
            category.getType(), category.getName(), category.getDescription());

        return rows_affected > 0;
    }

    public ServiceCategories getCategoryById(int categoryId){
        List<ServiceCategories> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_ID, categoryRowMapper, categoryId);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public ServiceCategories getCategoryByName(String name){
        List<ServiceCategories> categories = jdbcTemplate.query(GET_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, name);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public boolean updateCategory(ServiceCategories category){
        return jdbcTemplate.update(UPDATE_SERVICE_CATEGORY, category.getType(), category.getName(), category.getDescription(), category.getCategoryId()) > 0;
    }

    public boolean deleteCategory(int categoryId){
        return jdbcTemplate.update(DELETE_SERVICE_CATEGORY, categoryId) > 0;
    }

    public List<ServiceCategories> searchCategoriesByName(String keyword){
        return jdbcTemplate.query(SEARCH_SERVICE_CATEGORY_BY_NAME, categoryRowMapper, "%" + keyword + "%");
    }

    public List<ServiceCategories> getAllCategories(){
        return jdbcTemplate.query(GET_ALL_SERVICE_CATEGORIES, categoryRowMapper);
    } 
}