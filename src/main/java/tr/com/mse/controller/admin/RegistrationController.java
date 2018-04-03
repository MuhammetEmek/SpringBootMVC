package tr.com.mse.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tr.com.mse.form.UserForm;
import tr.com.mse.mapper.UserMapper;
import tr.com.mse.service.UserService;

@Controller
@RequestMapping("/admin/register")
public class RegistrationController {

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@GetMapping
	public String register(Model model) {
		model.addAttribute("userData", new UserForm());
		model.addAttribute("isAdmin", false);

		return "admin/registration";
	}

	@PostMapping
	public String registerNew(@Valid @ModelAttribute(value = "userData") UserForm userData, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userData", userData);
			return "admin/registration";
		}

		try {
			userService.saveUser(userData);

			model.addAttribute("userData", new UserForm());
			model.addAttribute("result", "Kullanıcı oluşturma işlemi başarıyla sonuçlanmıştır.");
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", "Kullanıcı oluştururken hata alınmıştır.");
		}

		return "admin/registration";
	}
}