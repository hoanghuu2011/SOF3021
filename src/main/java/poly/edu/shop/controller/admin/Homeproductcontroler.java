package poly.edu.shop.controller.admin;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Product;
import poly.edu.shop.service.CategoryService;
import poly.edu.shop.service.ProductService;
import poly.edu.shop.service.StorageService;

@Controller
@RequestMapping("products")
public class Homeproductcontroler {
	 private static final int PAGE_SIZE = 8; // Số sản phẩm hiển thị trên mỗi trang
	@Autowired
    private CategoryService categoryService;

	 @Autowired
	    private ProductService productService;

	    @GetMapping("/all")
	    public String showAllProducts(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
	        PageRequest pageable = PageRequest.of(page, PAGE_SIZE);
	        Page<Product> productPage = productService.findAll(pageable);
	        List<Product> productList = productPage.getContent();
	        List<Category> categories = categoryService.findAll();
	        model.addAttribute("products", productList);
	        model.addAttribute("categories", categories);
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", productPage.getTotalPages());
	        return "home/fragments/home";
	    }

	    @GetMapping("category/{categoryId}")
	    public String showProductsByCategory(
	            @PathVariable("categoryId") Long categoryId,
	            @RequestParam(value = "page", defaultValue = "0") int page,
	            Model model
	    ) {
	        PageRequest pageable = PageRequest.of(page, PAGE_SIZE);
	        Page<Product> productPage = productService.findByCategory_CategoryId(categoryId, pageable);
	        List<Product> products = productPage.getContent();
	        if (products.isEmpty()) {
	        	model.addAttribute("message", "No products found for the given category ID.");

	        }
	        List<Category> categories = categoryService.findAll();
	        model.addAttribute("products", products);
	        model.addAttribute("categories", categories);
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", productPage.getTotalPages());
	        return "home/fragments/home";
	    }

}
