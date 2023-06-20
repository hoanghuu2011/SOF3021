package poly.edu.shop.controller.admin;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Product;
import poly.edu.shop.model.CategoryDto;
import poly.edu.shop.model.ProductDto;
import poly.edu.shop.service.CategoryService;
import poly.edu.shop.service.ProductService;
import poly.edu.shop.service.StorageService;

@Controller
@RequestMapping("admin/products")
public class ProductController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	@Autowired
	StorageService storageService;
	
	@ModelAttribute("categories")
	public List<CategoryDto> getCategories(){
		return categoryService.findAll().stream().map(item->{
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	@GetMapping("add")
	public String add(Model model) {
		ProductDto dto = new ProductDto();
		dto.setIsEdit(false);
		model.addAttribute("product", dto);
		return "admin/products/addOrEdit";
	}

	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
		
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		
		if(opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setCategoryId(entity.getCategory().getCategoryId());
			dto.setIsEdit(true);
			model.addAttribute("product",dto);
			return new ModelAndView("admin/products/addOrEdit" , model);
			
		}
		
		model.addAttribute("message","Edit success!!!!");
		return new ModelAndView("forward:/admin/products", model);
	}


	
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment; filename=\"" +file.getFilename()+"\"").body(file);
	}
    
    
	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) throws IOException {
		
		Optional<Product> opt = productService.findById(productId);
		if(opt.isPresent()) {
			if(!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());
			}
			productService.delete(opt.get());
			model.addAttribute("message","Xóa thành công ");
		}else {
			model.addAttribute("message","lỗi!!! ");
		}
		
		
		return new ModelAndView("forward:/admin/products/", model);// redirect:
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
	        @Valid @ModelAttribute("product") ProductDto dto, BindingResult result) {
	    if (result.hasErrors()) {
	        return new ModelAndView("admin/products/addOrEdit");
	    }

	    Product entity = new Product();
	    BeanUtils.copyProperties(dto, entity);
	    Category category = new Category();
	    category.setCategoryId(dto.getCategoryId());
	    entity.setCategory(category);

	    if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
	        UUID uuid = UUID.randomUUID();
	        String uuString = uuid.toString();
	        entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));
	        storageService.store(dto.getImageFile(), entity.getImage());
	    } else {
	        Optional<Product> existingProductOpt = productService.findById(dto.getProductId());
	        if (existingProductOpt.isPresent()) {
	            Product existingProduct = existingProductOpt.get();
	            entity.setImage(existingProduct.getImage());
	        }
	    }

	    productService.save(entity);
	    model.addAttribute("message", "Thêm thành công");
	    return new ModelAndView("redirect:/admin/products", model); // redirect:
	}


	@GetMapping("")
	public String list(ModelMap model,
	                   @RequestParam(name = "name", required = false) String name,
	                   @RequestParam("page") Optional<Integer> page,
	                   @RequestParam("size") Optional<String> size) {

	    int currentPage = page.orElse(1);
	    int pageSize;
	    if (size.isPresent() && size.get().equals("all")) {
	        // Nếu giá trị size là "all", set pageSize là một giá trị lớn để hiển thị tất cả sản phẩm
	        pageSize = Integer.MAX_VALUE;
	    } else {
	        pageSize = size.map(Integer::parseInt).orElse(5);
	    }
	    Sort sort = Sort.by("productId").descending();
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

	    Page<Product> resultPage;
	    if (StringUtils.hasText(name)) {
	        resultPage = productService.findByNameContaining(name, pageable);
	        model.addAttribute("name", name);
	    } else {
	        resultPage = productService.findAll(pageable);
	    }
	    int totalPages = resultPage.getTotalPages();
	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);

	        if (totalPages > 5) {
	            if (end == totalPages) start = end - 5;
	            else if (start == 1) end = start + 5;
	        }
	        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
	                .boxed()
	                .collect(Collectors.toList());
	        model.addAttribute("pageNumbers", pageNumbers);
	    }

	    model.addAttribute("productPage", resultPage);

	    return "admin/products/list";
	}


	
	
	
}
