package poly.edu.shop.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import poly.edu.shop.domain.Account;

public interface AccountService {
	boolean isUsernameExists(String username);

	<S extends Account> S saveAndFlush(S entity);

	<S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Account> List<S> saveAll(Iterable<S> entities);

	<S extends Account> S save(S entity);

	Account getReferenceById(String id);

	Account getOne(String id);

	Account getById(String id);

	void flush();

	<S extends Account> Optional<S> findOne(Example<S> example);

	Optional<Account> findById(String id);

	<S extends Account, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	List<Account> findAllById(Iterable<String> ids);

	List<Account> findAll(Sort sort);

	Page<Account> findAll(Pageable pageable);

	<S extends Account> List<S> findAll(Example<S> example);

	<S extends Account> List<S> findAll(Example<S> example, Sort sort);

	<S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

	List<Account> findAll();

	boolean existsById(String id);

	<S extends Account> boolean exists(Example<S> example);

	void deleteInBatch(Iterable<Account> entities);

	void deleteById(String id);

	void deleteAllInBatch(Iterable<Account> entities);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<String> ids);

	void deleteAllById(Iterable<? extends String> ids);

	void deleteAll(Iterable<? extends Account> entities);

	void deleteAll();

	void delete(Account entity);

	<S extends Account> long count(Example<S> example);

	long count();

	Account login(String username, String password);

	Account findByUsername(String username);

	Account findByUsernameAndPassword(String username, String password);

}
