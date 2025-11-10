package main.com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.annotation.processing.Generated;

@Data
@Entity
@Table(name="subcategories")

public class Subcategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL )
    @JsonIgnore
    private List<Product> products;

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
    public Boolean getActive() {
        return active;

    }
    public String getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}