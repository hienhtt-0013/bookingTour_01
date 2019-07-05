package app.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.controller.admin.BaseController;
import app.model.Role;
import app.model.User;
import app.service.RoleService;
import app.service.UserService;

@Controller
@RequestMapping("admin/")
public class AuthAdminController extends BaseController{
	public RoleService getRoleService() {
		return roleService;
	}
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private RoleService roleService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Map<String, User> model) {
		User user = new User();
		model.put("user", user);
		return "admin/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
            return "admin/login";
        }
		User user1 = userService.checkLogin(user.getUsername(), user.getPassword());
		//---- Login success
		// Check role
		if(user1 != null) {
			session.setAttribute("current_user", user1.getUsername());
			return "redirect:/admin/index";
		}
		//---- Login fail
		modelMap.put("message_login", getProperties().getProperty("error.login"));
		return "admin/login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("current_user");
		return "redirect:/admin/login";
	}
}
