package com.app.backend.dto;

import com.app.backend.model.Category;
import com.app.backend.model.Subcategory;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean active;
    private Category category;
    private Subcategory subcategory;

    public ProductResponse(Long id, String name, String description, Double price, Integer stock, Boolean active, Category category, Subcategory subcategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.category = category;
        this.subcategory = subcategory;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }
    public Boolean getActive() { return active; }
    public Category getCategory() { return category; }
    public Subcategory getSubcategory() { return subcategory; }
}
