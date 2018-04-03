package tr.com.mse.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tr.com.mse.form.PasswordForm;
import tr.com.mse.form.UserForm;
import tr.com.mse.model.User;
import tr.com.mse.service.UserService;

@Controller
@RequestMapping("/admin/userList")
public class UsersController {

	@Autowired
	UserService userService;

	@GetMapping
	public String userList(Model model) {
		List<UserForm> allUsers = userService.getAllUsers();
		model.addAttribute("allUsers", allUsers);
		model.addAttribute("userForm", new UserForm());
		model.addAttribute("passwordForm", new PasswordForm());
		
		return "admin/userList";
	}

	@GetMapping("/{objid}")
	public String getUserInfo(@PathVariable("objid") String objid, Model model) {
		UserForm userForm = userService.getUserFormByObjid(objid);
		model.addAttribute("userForm", userForm);
		model.addAttribute("action", "/admin/userList/" + userForm.getObjid() + "/update");

		return "fragments/userModal :: updateUserContent";
	}

	@GetMapping("/{objid}/switchStatus")
	public String switchUserStatus(@PathVariable("objid") String objid, Model model) {
		userService.switchUserStatus(objid);

		return "redirect:/admin/userList";
	}

	@GetMapping("/{objid}/delete")
	public String deleteUser(@PathVariable("objid") String objid, Model model) {
		userService.switchUserStatus(objid);

		return "redirect:/admin/userList";
	}

	@PostMapping("/{objid}/resetPassword")
	public String resetPassword(@PathVariable("objid") String objid, @ModelAttribute(value = "passwordForm") PasswordForm passwordForm, BindingResult bindingResult, Model model) {
		try {
			User userInfo = userService.findByObjid(objid);
			userService.resetPassword(userInfo, passwordForm.getNewPassword());

			return "redirect:/admin/userList";
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", "Şifre güncellerken hata alınmıştır.");
		}

		return "forward:/admin/userList";
	}

	@PostMapping("/{objid}/update")
	public String updateUser(@PathVariable("objid") String objid, @ModelAttribute(value = "userForm") UserForm userForm, BindingResult bindingResult, Model model) {
		try {
			User userInfo = userService.findByObjid(objid);
			userService.updateUser(userInfo, userForm);
		}
		catch (Exception e) {
			model.addAttribute("errorMsg", "Kullanıcı güncellerken hata alınmıştır.");
		}

		return "redirect:/admin/userList";
	}
}