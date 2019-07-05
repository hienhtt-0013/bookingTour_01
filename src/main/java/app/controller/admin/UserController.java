package app.controller.admin;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.helper.Constants;
import app.model.Role;
import app.model.User;
import app.service.UserService;

@RequestMapping("admin/users/")
@Controller
public class UserController extends BaseController {

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
	public String add(Map<String, User> model) {
		User user = new User();
		model.put("user", user);
		return "admin/user/add";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User user,BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
		if (result.hasErrors()) {
            return "admin/user/add";
        }
		// Check exist
		if( (userService.findByUser(user.getUsername()) != null) ) {
			modelMap.put("message_exist", getProperties().getProperty("error.existUser"));
			return "admin/user/add";
			// Check Password Confirm
		} else if(!user.getPasswordConfirm().equals(user.getPassword())) {
			modelMap.put("message_matchPass", getProperties().getProperty("error.matchPass"));
			return "admin/user/add";
		}
		// OK lưu
		userService.createUserAdmin(user);
		redirectAttributes.addFlashAttribute("message", getProperties().getProperty("sucess.saveUser"));
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/users/index/page/1";
	}
	
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable Long id, @RequestParam int idPage,
			RedirectAttributes redirectAttributes) {

		if(userService.deleteUser(id)) {
			redirectAttributes.addFlashAttribute("message", getProperties().getProperty("sucess.deleteUser"));
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} else {
			redirectAttributes.addFlashAttribute("message", getProperties().getProperty("error.deleteUser"));
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "redirect:/admin/users/index/page/" + idPage;
	}
}
