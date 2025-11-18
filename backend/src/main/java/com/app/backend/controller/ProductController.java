package com.app.backend.controller;

import com.app.backend.model.Product;
import com.app.backend.service.ProductService;
import com.app.backend.dto.MessageResponse;
import com.app.backend.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @GetMapping("/subcategory/{subcategoryId}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<List<Product>> getProductsBySubcategoryId(@PathVariable Long subcategoryId) {
        return ResponseEntity.ok(productService.findBySubcategoryId(subcategoryId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product created = productService.create(product);
            ProductResponse response = new ProductResponse(
                created.getId(),
                created.getName(),
                created.getDescription(),
                created.getPrice(),
                created.getStock(),
                created.getActive(),
                created.getCategory(),
                created.getSubcategory()
            );
            return ResponseEntity.ok(response);
        } catch (com.app.backend.exception.CustomBadRequestException e) {
            return ResponseEntity.badRequest().body(new com.app.backend.dto.MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINADOR')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = productService.update(id, product);
            ProductResponse response = new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getDescription(),
                updated.getPrice(),
                updated.getStock(),
                updated.getActive(),
                updated.getCategory(),
                updated.getSubcategory()
            );
            return ResponseEntity.ok(response);
        } catch (com.app.backend.exception.CustomBadRequestException e) {
            return ResponseEntity.badRequest().body(new com.app.backend.dto.MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(value ="/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Producto eliminado con éxito"));
    }
}
