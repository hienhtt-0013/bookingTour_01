package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.controller.admin.BaseController;
import app.helper.Constants;
import app.service.ToursService;

@RequestMapping("tours")
@Controller
public class ToursController extends BaseController {
	@Autowired
	private ToursService toursService;


	@RequestMapping(value = { "/", "index" }, method = RequestMethod.GET)
	public String home() {
		return "redirect:/tours/index/page/1";
	}

	@RequestMapping(value = { "/index/page/{pageNumber}" }, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNumber, Model model) {

		int pageSize = Constants.PAGESIZE;

		ModelAndView mov = new ModelAndView("tours");

		PagedListHolder<?> pages = new PagedListHolder<>();

		long count = toursService.count();
		long end = setEndPagination(count);

		setPaginationModelObject(pageNumber, end, model);

		if (pageNumber == 1) {
			pages = new PagedListHolder<>(toursService.loadTours(null, null));
		} else if (pageNumber <= end && pageNumber > 0) {
			pages = new PagedListHolder<>(toursService.loadTours((pageNumber - 1) * pageSize, pageSize));
		}

		model.addAttribute("page", pages);

		return mov;
	}
}