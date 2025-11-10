package main.com.app.backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.list;
import java.util.jar.Attributes.Name;

import javax.annotation.processing.Generated;

@Data
@Entity
@Table(name="categories")

public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL )
    @JsonIgnore
    private List<Subcategory> subcategories;

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
    public List<Subcategories> getSubcategories() {
        return subcategories;

    }
    public void setSubcategories(List<Subcategories> subcategories) {
        this.subcategories = subcategories;

}
}