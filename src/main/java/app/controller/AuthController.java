package app.controller;

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
import app.model.User;
import app.service.RoleService;
import app.service.UserService;

@Controller
public class AuthController extends BaseController{
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
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
            return "login";
        }
		User user1 = userService.checkLogin(user.getUsername(), user.getPassword());
		if(user1 != null) {
			session.setAttribute("current_user", user1.getUsername());
			return "redirect:/";
		}
		
		modelMap.put("message_login", getProperties().getProperty("error.login"));
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("current_user");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Map<String, User> model) {
		User user = new User();
		model.put("user", user);
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, ModelMap modelMap) {
		if (result.hasErrors()) {
            return "register";
        }
		// Check exist
		if( (userService.findByUser(user.getUsername()) != null) ) {
			modelMap.put("message_exist", getProperties().getProperty("error.existUser"));
			return "register";
			// Check Password Confirm
		} else if(!user.getPasswordConfirm().equals(user.getPassword())) {
			modelMap.put("message_matchPass", getProperties().getProperty("error.matchPass"));
			return "register";
		} 
		// OK save
		userService.createUserPublic(user);
		modelMap.put("message_login", getProperties().getProperty("sucess.register"));
		return "login";
	}
	
}
