package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRouteSampleController {

	@RequestMapping(value = {"/admin","/admin/index"}, method = RequestMethod.GET)
	public String index() {
		return "admin/index";
	}
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String users() {
		return "admin/user/index";
	}

	@RequestMapping(value = "/admin/users/add", method = RequestMethod.GET)
	public String usersAdd() {
		return "admin/user/add";
	}
}
