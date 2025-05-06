package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class ServiceCategories {
	
	private int categoryId;
    private String type;
    private String name;
    private String description;

    // Constructors
    public ServiceCategories() {}

    public ServiceCategories(String type, String name, String description){
        this.type = type;
        this.name = name;
        this.description = description;
    }

	public ServiceCategories(int categoryId, String type, String name, String description) {
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
} 