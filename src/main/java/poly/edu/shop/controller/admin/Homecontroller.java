package poly.edu.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.edu.shop.domain.Category;
import poly.edu.shop.service.CategoryService;

@Controller
public class Homecontroller {
	@Autowired
	@RequestMapping("/home")
    public String index() {
        return "home/index";
    }
}
