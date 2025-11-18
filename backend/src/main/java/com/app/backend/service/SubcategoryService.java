package com.app.backend.service;

import com.app.backend.model.Subcategory;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubcategoryService {
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }

    public List<Subcategory> findByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }

    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategoria no encontrada"));
    }
    public Subcategory create(Subcategory subcategory){
        // Validar que la categoría existe
        Long categoryId = subcategory.getCategory() != null ? subcategory.getCategory().getId() : null;
        if (categoryId == null || !categoryRepository.existsById(categoryId)) {
            throw new com.app.backend.exception.CustomBadRequestException("No se puede crear la subcategoría porque la categoría especificada no existe.");
        }
        // Validar que el nombre de la subcategoría no está repetido
        if (subcategoryRepository.existsByName(subcategory.getName())) {
            throw new com.app.backend.exception.CustomBadRequestException("No se puede crear la subcategoría porque ya existe una subcategoría con ese nombre.");
        }
        return subcategoryRepository.save(subcategory);
    }
    public Subcategory update(Long id, Subcategory subcategoryDetails){
        Subcategory subcategory = findById(id);
        // Validar que la categoría existe antes de actualizar
        Long categoryId = subcategoryDetails.getCategory() != null ? subcategoryDetails.getCategory().getId() : null;
        if (categoryId == null || !categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("La categoría especificada no existe");
        }
        subcategory.setName(subcategoryDetails.getName());
        subcategory.setDescription(subcategoryDetails.getDescription());
        subcategory.setActive(subcategoryDetails.getActive());
        subcategory.setCategory(subcategoryDetails.getCategory());
        return subcategoryRepository.save(subcategory);
    }
    public void delete(Long id){
        Subcategory subcategory = findById(id);
        subcategoryRepository.delete(subcategory);
    }
} 
