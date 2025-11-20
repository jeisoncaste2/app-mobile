package com.app.backend.service;

import com.app.backend.model.Category;
import com.app.backend.model.Product;
import com.app.backend.model.Subcategory;
import com.app.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private com.app.backend.repository.CategoryRepository categoryRepository;

    @Autowired
    private com.app.backend.repository.SubcategoryRepository subcategoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> findBySubcategoryId(Long subcategoryId) {
        return productRepository.findBySubcategoryId(subcategoryId);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product create(Product product){
        // Validar que la categoría existe
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        if (categoryId == null || !categoryRepository.existsById(categoryId)) {
            throw new com.app.backend.exception.CustomBadRequestException("Error: La categoría especificada no existe.");
        }
        // Validar que la subcategoría existe
        Long subcategoryId = product.getSubcategory() != null ? product.getSubcategory().getId() : null;
        if (subcategoryId == null || !subcategoryRepository.existsById(subcategoryId)) {
            throw new com.app.backend.exception.CustomBadRequestException("Error: La subcategoría especificada no existe.");
        }
        return productRepository.save(product);
    }

    public Product update(Long id, Product productDetails){
        Product product = findById(id);
        // LOG: Mostrar los IDs recibidos
        System.out.println("[DEBUG] Actualizando producto: id=" + id);
        Long categoryId = productDetails.getCategory() != null ? productDetails.getCategory().getId() : null;
        System.out.println("[DEBUG] ID de categoría recibido: " + categoryId);
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElse(null);
        }
        if (category == null) {
            System.out.println("[ERROR] La categoría especificada no existe: " + categoryId);
            throw new com.app.backend.exception.CustomBadRequestException("Error: La categoría especificada no existe.");
        }
        Long subcategoryId = productDetails.getSubcategory() != null ? productDetails.getSubcategory().getId() : null;
        System.out.println("[DEBUG] ID de subcategoría recibido: " + subcategoryId);
        Subcategory subcategory = null;
        if (subcategoryId != null) {
            subcategory = subcategoryRepository.findById(subcategoryId).orElse(null);
        }
        if (subcategory == null) {
            System.out.println("[ERROR] La subcategoría especificada no existe: " + subcategoryId);
            throw new com.app.backend.exception.CustomBadRequestException("Error: La subcategoría especificada no existe.");
        }
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setActive(productDetails.getActive());
        product.setCategory(category);
        product.setSubcategory(subcategory);
        return productRepository.save(product);
    }

    public void delete(Long id){
        Product product = findById(id);
        productRepository.delete(product);
    }
}
