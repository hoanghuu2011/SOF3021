package poly.edu.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import poly.edu.shop.domain.Account;
import poly.edu.shop.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	 List<Order> findByAccount(Account account);
//	 List<Order> findByProductId(Long productId);
	
}
