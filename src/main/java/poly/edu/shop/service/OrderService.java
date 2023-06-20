package poly.edu.shop.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import poly.edu.shop.domain.Account;
import poly.edu.shop.domain.Order;
import poly.edu.shop.repository.OrderRepository;

@Service
@SessionScope
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private AccountService accountService;

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> getOrderHistory(String username) {
        Account account = accountService.findByUsername(username);
        return orderRepo.findByAccount(account);
    }

    public List<Order> getfinall() {
        return orderRepo.findAll();
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderRepo.findById(orderId);
    }

    public Order create(String address, Account account) {
        Order order = new Order();
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setAddress(address);
        order.setAccount(account);
        Order savedOrder = orderRepo.save(order);

        return savedOrder;
    }

	public Order findById(Integer orderId) {
		 Optional<Order> optional = orderRepo.findById(orderId);
		    return optional.orElse(null);
	}
//	 public List<Order> getOrdersByProductId(Long productId) {
//	        return orderRepo.findByProductId(productId);
//	    }
//	 
		
}



