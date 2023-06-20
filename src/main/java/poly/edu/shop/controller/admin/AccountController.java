package poly.edu.shop.controller.admin;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.ModelAndView;

import poly.edu.shop.domain.Account;
import poly.edu.shop.domain.Category;
import poly.edu.shop.domain.Product;
import poly.edu.shop.model.AccountDto;
import poly.edu.shop.service.AccountService;
import poly.edu.shop.service.CategoryService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/addOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		accountService.save(entity);
		model.addAttribute("message", "Thêm thành công");
		return new ModelAndView("forward:/admin/accounts", model);// redirect:
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list = accountService.findAll();
//	list.sort(Comparator.comparing(Account::getUsername).reversed());
	model.addAttribute("accounts", list);
	return "admin/accounts/list";
	}
	
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {

		Optional<Account> opt = accountService.findById(username);
		AccountDto dto = new AccountDto();

		if (opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
            dto.setPassword("");
			model.addAttribute("account", dto);
			return new ModelAndView("admin/accounts/addOrEdit", model);

		}

		model.addAttribute("message", "Edit faill !!!!");
		return new ModelAndView("forward:/admin/accounts/", model);
	}
	
	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
	accountService.deleteById(username);
		model.addAttribute("message","Xóa thành công ");
		return new ModelAndView("forward:/admin/accounts/", model);// redirect:
	}


}
