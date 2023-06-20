package poly.edu.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.edu.shop.domain.Order;
import poly.edu.shop.domain.OrderDetail;
import poly.edu.shop.domain.Product;
import poly.edu.shop.model.ReportCategory;
import poly.edu.shop.domain.Customer;
import poly.edu.shop.service.OrderDetailService;
import poly.edu.shop.service.OrderService;
import poly.edu.shop.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminOdercontroler {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
	private OrderDetailService orderDetailService;
	
	
	@GetMapping("/reports")
	public String reportTable(Model model) {
		List<ReportCategory> reports = orderDetailService.reportByCategory();
		model.addAttribute("reports", reports);
		return "/admin/order/report";
	}

    @GetMapping("/orders")
    public String viewOrderStatistics(Model model) {
        List<Order> orders = orderService.getfinall();
        model.addAttribute("orders", orders);
        return "/admin/order/order";
    }
    @GetMapping("/order/detail/{orderId}")
	public String viewOrderDetail(@PathVariable("orderId") Integer orderId, Model model) {
		Order order = orderService.findById(orderId);
		if (order != null) {
			List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
			model.addAttribute("order", order);
			model.addAttribute("orderDetails", orderDetails);
			return "/admin/order/oderdetail";
		} else {
			return "redirect:/admin/orders";
		}
	}

}
