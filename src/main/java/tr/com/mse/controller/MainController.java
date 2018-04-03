package tr.com.mse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


	@GetMapping("/")
	public String homePage(Model model) {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(name = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request, Model model) {

		if (error != null) {
			Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			if (exception != null)
				error = exception.getMessage();

			model.addAttribute("errorMessage", error);
		}

		if (logout != null)
			model.addAttribute("message", "Logged out succcessfully");

		return "login";
	}
}