package main.com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.annotation.processing.Generated;

@Data
@Entity
@Table(name="products")

public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    @JsonIgnore
    private Category category;
    @ManyToOne
    @JoinColumn(name="subcategory_id", nullable=false)
    private Subcategory subcategory;

    public Long getId() {
        return id;

    }
    public void setId() {
        this.id = id;

    }
    public String getName() {
        return name;

    }
    public void setName() {
        this.name = name;

    }
    public String getDescription() {
        return description;

    }
    public void setDescription() {
        this.description = description;

    }
    private Double getPrice() {
        return price;

    }
    public void setPrice() {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public Boolean getActive() {
        return active;

    }
    public Boolean setActive() {
        this.active = active;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Subcategory getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}