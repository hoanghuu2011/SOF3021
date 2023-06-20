package poly.edu.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import poly.edu.shop.domain.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	@Query("select acc from Account acc where acc.username=:username")
	Optional<Account> findByUsername(@Param("username") String username);
	Optional<Account> findByUsernameAndPassword(String username, String password);
}
