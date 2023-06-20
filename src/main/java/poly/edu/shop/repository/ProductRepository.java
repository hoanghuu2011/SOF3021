package poly.edu.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {
	 List<Product> findByCategory(Long categoryId);
	 
	 Page<Product> findByCategory_CategoryId(Long categoryIds, Pageable pageable);
	 List<Product> findByCategory_CategoryId(Long categoryIds);

	   List<Product> findByNameContaining(String name);
	   Page<Product> findByNameContaining(String name, Pageable pageable);

}
