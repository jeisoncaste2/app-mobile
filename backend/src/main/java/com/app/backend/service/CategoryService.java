package com.app.backend.service;

import com.app.backend.model.Category;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.exception.CustomBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        // Validar que el nombre de la categoría no está repetido
        if (categoryRepository.existsByName(category.getName())) {
            throw new CustomBadRequestException("No se puede crear la categoría porque ya existe una categoría con ese nombre.");
        }
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("categoria no encontrada"));
    }

    public void delete(Long id){
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    public Category update(Long id, Category updatedCategory) {
        Category category = findById(id);
        category.setName(updatedCategory.getName());
        // Agrega aquí otros campos si existen
        return categoryRepository.save(category);
    }
}
