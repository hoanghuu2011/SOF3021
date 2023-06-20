package poly.edu.shop.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.shop.domain.Account;
import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Order;
import poly.edu.shop.domain.OrderDetail;
import poly.edu.shop.model.OrderForm;
import poly.edu.shop.service.AccountService;
import poly.edu.shop.service.CartService;
import poly.edu.shop.service.OrderDetailService;
import poly.edu.shop.service.OrderService;

@Controller
public class CartController {
	@Autowired
	private HttpSession session;
	@Autowired
	private CartService cartService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;

	@GetMapping("/cart")
	public String cart(Model model) {
		model.addAttribute("cart", cartService);
		return "home/fragments/cart";
	}

	@RequestMapping("/cart/add/{productId}")
	public String addToCart(@PathVariable("productId") Long productId, Model model) {
		cartService.add(productId);
		return "redirect:/cart";
	}


	@RequestMapping("/cart/remove/{productId}")
	public String remove(@PathVariable("productId") Long productId) {
		cartService.remove(productId);
		return "redirect:/cart";
	}

	@RequestMapping("/cart/update/{productId}")
	public String update(@PathVariable("productId") Long productId, @RequestParam("quantity") Integer quantity) {
		cartService.update(productId, quantity);
		return "redirect:/cart";
	}

	@RequestMapping(value = "/cart/clear", method = RequestMethod.POST)
	public String clear() {
		cartService.clear();
		return "redirect:/cart";
	}

	@GetMapping("/cart/checkout")
	public String checkoutForm(Model model) {
		if (cartService.getItems().size() <= 0) {
			return "redirect:/cart";
		}
		OrderForm form = new OrderForm();
		model.addAttribute("data", form);
		model.addAttribute("cart", cartService);

		return "home/fragments/cart-checkout";
	}

	@RequestMapping("/cart/checkout")
	public String checkoutHandler(Model model, @Valid @ModelAttribute("data") OrderForm form, BindingResult result) {
		if (cartService.getItems().size() <= 0) {
			return "redirect:/cart";
		}
		if (!result.hasErrors()) {
			String username = (String) session.getAttribute("username");
			Account account = accountService.findByUsername(username);
			// save order
			Order order = orderService.create(form.getAddress(), account);
			if (order == null) {
				return "redirect:/cart";
			}
			List<OrderDetail> orderDetails = cartService.toOrderDetails(order);
			List<OrderDetail> orderDetailsNew = orderDetailService.saveAll(orderDetails);
			if (orderDetailsNew.size() == orderDetails.size()) {
				cartService.clear();
				List<Order> list = orderService.getOrderHistory(username);
				//lấy danh sách đơn hàng của người dùng từ orderService.getOrderHistory() và thêm vào model
				model.addAttribute("orders", list);
				return "/home/fragments/oder";
			}
			return "redirect:/cart";
		}
		model.addAttribute("cart", cartService);
		return "cart-checkout";
	}

	@RequestMapping("/list")
	public String list(ModelMap model) {
		List<Order> list = orderService.getfinall();
		model.addAttribute("orders", list);
		return "/home/fragments/oder";
	}

	@RequestMapping("/oderme")
	public String oderme(ModelMap model) {
		String username = (String) session.getAttribute("username");
		Account account = accountService.findByUsername(username);
		List<Order> list = orderService.getOrderHistory(username);
		model.addAttribute("orders", list);
		return "/home/fragments/oder";
	}

	@GetMapping("/order/detail/{orderId}")
	public String viewOrderDetail(@PathVariable("orderId") Integer orderId, Model model) {
		Order order = orderService.findById(orderId);
		if (order != null) {
			List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
			model.addAttribute("order", order);
			model.addAttribute("orderDetails", orderDetails);
			return "/home/fragments/oderdetail";
		} else {
			return "redirect:/list";
		}
	}

}
