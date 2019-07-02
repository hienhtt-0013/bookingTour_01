package app.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.helper.Constants;
import app.model.Role;
import app.model.User;
import app.service.UserService;

@RequestMapping("admin/users/")
@Controller
public class UserController extends BaseController {
	public UserService getUserService() {
		return userService;
	}
	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "index" }, method = RequestMethod.GET)
	public String home() {
		return "redirect:/admin/users/index/page/1";
	}

	@RequestMapping(value = { "/index/page/{pageNumber}" }, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNumber, Model model) {

		int pageSize = Constants.PAGESIZE;

		ModelAndView mov = new ModelAndView("admin/user/index");

		PagedListHolder<?> pages = new PagedListHolder<>();

		long count = userService.countUser();
		long end = setEndPagination(count);

		setPaginationModelObject(pageNumber, end, model);

		if (pageNumber == 1) {
			pages = new PagedListHolder<>(userService.loadUsers(null, null));
		} else if (pageNumber <= end && pageNumber > 0) {
			pages = new PagedListHolder<>(userService.loadUsers((pageNumber - 1) * pageSize, pageSize));
		}

		model.addAttribute("page", pages);

		return mov;
	}

	@RequestMapping(value = "/new")
	public String add(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return "admin/user/add";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String add(@ModelAttribute("userForm") User userForm) {
		String roleName = null;
		int roleId = 0;
		for(Role role: userForm.getRoles()){
			roleName = role.getName();
		}
		if(roleName.equals("ROLE_ADMIN")) roleId = 1;
		else if(roleName.equals("ROLE_EDITOR")) roleId = 2;
		getUserService().createUser(userForm, roleId, roleName);
		return "redirect:/admin/users/index/page/1";
	}
}
