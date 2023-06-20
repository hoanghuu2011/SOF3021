package poly.edu.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import poly.edu.shop.domain.Category;

public interface CategoryService {

	<S extends Category> S save(S entity);

	Category getById(Long id);

	Optional<Category> findById(Long id);

	Page<Category> findAll(Pageable pageable);

	boolean existsById(Long id);

	<S extends Category> boolean exists(Example<S> example);

	void deleteById(Long id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteAllInBatch(Iterable<Category> entities);

	<S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Category> S saveAndFlush(S entity);

	void flush();

	<S extends Category> List<S> saveAll(Iterable<S> entities);

	List<Category> findAllById(Iterable<Long> ids);

	List<Category> findAll(Sort sort);

	List<Category> findAll();

	void deleteAllById(Iterable<? extends Long> ids);

	void deleteAll(Iterable<? extends Category> entities);

	void deleteAll();

	void delete(Category entity);

	long count();

	List<Category> findByNameContaining(String name);

	Page<Category> findByNameContaining(String name, Pageable pageable);
	

}
