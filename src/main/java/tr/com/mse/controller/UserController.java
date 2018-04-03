package tr.com.mse.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tr.com.mse.form.PasswordForm;
import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;
import tr.com.mse.service.SecurityService;
import tr.com.mse.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@Autowired
	@Qualifier("messageSource")
	ResourceBundleMessageSource messageSource;

	@GetMapping
	public String userInfo(Model model) {
		UserForm userForm = userService.getUserForm(securityService.getLoggedInUserCode());
		model.addAttribute("userData", userForm);
		return "user/userInfo";
	}

	@GetMapping("/resetPassword")
	public String resetPasswordForm(Model model) {
		model.addAttribute("passwordForm", new PasswordForm());
		return "user/resetPassword";
	}

	@PostMapping
	public String updateUser(@ModelAttribute(value = "userData") UserForm userData, BindingResult bindingResult, Model model) {
		try {
			User userInfo = userService.findByUserCode(securityService.getLoggedInUserCode());
			UserForm userForm = userService.updateUser(userInfo, userData);

			model.addAttribute("userData", userForm);
			model.addAttribute("result", messageSource.getMessage("user.message.update.success", null, Locale.getDefault()));
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", messageSource.getMessage("user.message.update.fail", null, Locale.getDefault()));
		}

		return "user/userInfo";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@Valid @ModelAttribute(value = "passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors())
			return "user/resetPassword";

		try {
			User userInfo = userService.findByUserCode(securityService.getLoggedInUserCode());

			// Check current password
			securityService.checkCurrentPassword(userInfo.getUserCode(), passwordForm.getCurrentPassword());

			userService.resetPassword(userInfo, passwordForm.getNewPassword());

			model.addAttribute("passwordForm", passwordForm);
			model.addAttribute("result", messageSource.getMessage("user.message.reset.password.success", null, Locale.getDefault()));
		}
		catch (org.springframework.security.authentication.BadCredentialsException iae) {
			bindingResult.rejectValue("currentPassword", "security.exception.currentPassword.invalid");
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", e.getMessage());
		}

		return "user/resetPassword";
	}
}