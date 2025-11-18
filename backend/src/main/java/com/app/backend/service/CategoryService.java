package com.app.backend.service;

import com.app.backend.model.Category;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        // Validar que el nombre de la categoría no está repetido
        if (categoryRepository.existsByName(category.getName())) {
            throw new com.app.backend.exception.CustomBadRequestException("No se puede crear la categoría porque ya existe una categoría con ese nombre.");
        }
        return categoryRepository.save(category);
    }
}package com.app.backend.service;

import com.app.backend.model.Category;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("categoria no encontrada"));
    }

    public Category create(Category category){
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category categoryDetails){
        Category category = findById(id);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        category.setActive(categoryDetails.getActive());
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
