package poly.edu.shop.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Product;

public interface ProductService {

	<S extends Product> S save(S entity);

	<S extends Product> Optional<S> findOne(Example<S> example);

	Optional<Product> findById(Long id);

	<S extends Product, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	Page<Product> findAll(Pageable pageable);

	<S extends Product> List<S> findAll(Example<S> example, Sort sort);

	<S extends Product> List<S> findAll(Example<S> example);

	Product getReferenceById(Long id);

	Product getById(Long id);

	Product getOne(Long id);

	<S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Product> S saveAndFlush(S entity);

	<S extends Product> List<S> saveAll(Iterable<S> entities);

	<S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

	boolean existsById(Long id);

	<S extends Product> boolean exists(Example<S> example);

	void deleteById(Long id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteAllInBatch(Iterable<Product> entities);

	void deleteInBatch(Iterable<Product> entities);

	void flush();

	List<Product> findAllById(Iterable<Long> ids);

	List<Product> findAll(Sort sort);

	List<Product> findAll();

	void deleteAllById(Iterable<? extends Long> ids);

	void deleteAll(Iterable<? extends Product> entities);

	void deleteAll();

	void delete(Product entity);

	<S extends Product> long count(Example<S> example);

	long count();
	List<Product> findByCategoryId(Long categoryId);

	 Page<Product> findByCategory_CategoryId(Long categoryIds, Pageable pageable);
	 List<Product> findByCategory_CategoryId(Long categoryIds);

	List<Product> findByNameContaining(String name);

	Page<Product> findByNameContaining(String name, Pageable pageable);
	

}

