package poly.edu.shop.controller.admin;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.edu.shop.domain.Account;
import poly.edu.shop.model.AccountDto;
import poly.edu.shop.model.AdminLoginDto;
import poly.edu.shop.service.AccountService;



@Controller
public class AdminLoginController {
@Autowired
private AccountService accountService;
@Autowired
private HttpSession session;

@GetMapping("alogin")
public String login(ModelMap model) {
	model.addAttribute("account", new AdminLoginDto());
	return "/admin/accounts/login2";
	
}
@PostMapping("alogin")
public ModelAndView login(ModelMap model, 
		@Valid @ModelAttribute("account") AdminLoginDto dto,
		BindingResult result) {
	if(result.hasErrors()) {
		return new ModelAndView("/admin/accounts/login2", model);
		
	}
	Account account = accountService.login(dto.getUsername(), dto.getPassword());
	if(account == null) {
		model.addAttribute("message", "Vui long kiem tra lai TK va MK");
		return new ModelAndView("/admin/accounts/login2", model);
	}
	session.setAttribute("username", account.getUsername());
	session.setAttribute("fullname", account.getFullname());
	session.setAttribute("role", account.getRole());
//	Object ruri = session.getAttribute("redirect-uri");
//	if(ruri !=null) {
//		session.removeAttribute("redirect-uri");
//		return new ModelAndView("redirect:" +ruri);
//		
//	}
	
	return new ModelAndView("forward:/home/", model);
	
}
@GetMapping("register")
public String register(ModelMap model) {
	model.addAttribute("account", new AdminLoginDto());
	return "/admin/accounts/login2";
	
}
  
   @PostMapping("register")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/login2");
		}
		if (accountService.findByUsername(dto.getUsername()) != null) {
			model.addAttribute("message", "Tên tài khoản đẫ được sử dụng vui lòng sử dụng tên khác ");
	        return new ModelAndView("admin/accounts/login2");
	    }
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		accountService.save(entity);
		model.addAttribute("message", "Đăng kí thành công!!!");
		return new ModelAndView("admin/accounts/login2");
	}
//   @PostMapping("/logout")
//   public String logout(HttpSession session) {
//       // Xóa các thuộc tính liên quan đến đăng nhập khỏi session
//	 
//		sessionService.remove("username");
//		sessionService.remove("fullname");
//		sessionService.remove("role");
//       // Chuyển hướng về trang đăng nhập
//		 return "redirect:/alogin";
//   }
   @PostMapping("/logout")
   public String logout(HttpSession session) {
       // Xóa các thuộc tính liên quan đến đăng nhập khỏi session
       session.removeAttribute("username");
       session.removeAttribute("fullname");
       session.removeAttribute("role");
       
       // Chuyển hướng về trang đăng nhập
       return "redirect:/alogin";
   }
   
}
