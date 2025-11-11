package main.com.app.backend.repository;

import main.com.app.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepositoryRepository<Product,Long> {
    List<Product> findByCategoryId(Long)
}