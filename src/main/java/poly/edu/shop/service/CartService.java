package poly.edu.shop.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import poly.edu.shop.domain.Order;
import poly.edu.shop.domain.OrderDetail;
import poly.edu.shop.domain.Product;
import poly.edu.shop.repository.ProductRepository;


@Service
@SessionScope
public class CartService {

	@Autowired
	private ProductRepository productRepo;

	private Map<Long, Product> map;

	public CartService() {
		this.map = new HashMap<>();
	}
	
	public Product add(Long productId) {
		Product product = this.map.get(productId);
		// kiểm tra id sản phẩm đã tồn tại hay chưa
		if (product != null) {
			// nếu tồn tại thì số lượng + 1
			this.update(productId, product.getQuantity() + 1);
		} else {
			// ngược lại thêm sp vào giỏ hàng
			Optional<Product> optionalProduct = productRepo.findById(productId);
			if (optionalProduct.isPresent()) {
				product = optionalProduct.get();
				if (product.getQuantity() < 0) {
					return product;
				}
				if (product.getDiscount() > 0) {
					double priceDiscount = product.getUnitPrice() * product.getDiscount();
					product.setUnitPrice(product.getUnitPrice() - priceDiscount);
				}
				product.setQuantity(1);
				this.map.put(productId, product);
			}
		}
		return product;
	}

	public void remove(Long productId) {
		this.map.remove(productId);
	}

	public Product update(Long productId, int quantity) {
		Product product = this.map.get(productId);
		if (product != null) {
			if (quantity <= 0) {
				product.setQuantity(1);
			} else {
				product.setQuantity(quantity);
			}
		}
		return product;
	}

	public void clear() {
		this.map.clear();
	}
	public Collection<Product> getItems() {
		return this.map.values();
	}

	public int getCount() {
		return this.getItems().size();
	}

	public int getQuantity() {
		int count = 0;
		for (Product item : this.map.values()) {
			count += item.getQuantity();
		}
		return count;
	}

	public double getAmount() {
		double amount = 0;
		for (Product item : this.map.values()) {
			amount += (item.getUnitPrice() * item.getQuantity());
		}
		return amount;
	}
	
	public List<OrderDetail> toOrderDetails(Order order) {
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Product product : this.getItems()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setUnitPrice(product.getUnitPrice());
			orderDetail.setQuantity(product.getQuantity());
			orderDetails.add(orderDetail);
		}
		return orderDetails;
	}
}
