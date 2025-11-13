package com.app.backend.service;

import com.app.backend.repository.UserRepository;
import org.app.backend.repository.OrderRepository.CategoryRepository;
import org.app.backend.repository.OrderRepository.SubcategoryRepository;
import org.app.backend.repository.OrderRepository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalCategories", categoryRepository.count());
        stats.put("totalSubcategories", subcategoryRepository.count());
        stats.put("totalProducts", productRepository.count());
        
        return stats;
    }
}