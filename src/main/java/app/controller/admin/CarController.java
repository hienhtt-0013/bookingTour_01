package app.controller.admin;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.helper.Constants;
import app.model.Car;
import app.service.CarService;
import app.validator.CarValidator;

@RequestMapping("admin/cars/")
@Controller
public class CarController extends BaseController {

	@Autowired
	private CarService carService;

	@Autowired
	private CarValidator carValidator;

	@RequestMapping(value = {"/","index"}, method = RequestMethod.GET)
	public String home() {
		return "redirect:/admin/cars/index/page/1";
	}

	@RequestMapping(value = { "/index/page/{pageNumber}" }, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNumber, Model model) {

		int pageSize = Constants.PAGESIZE;

		ModelAndView mov = new ModelAndView("admin/cars/index");

		PagedListHolder<?> pages = new PagedListHolder<>();

		long count = carService.countCar();
		long end = setEndPagination(count);

		setPaginationModelObject(pageNumber, end, model);

		if (pageNumber == 1) {
			pages = new PagedListHolder<>(carService.loadCars(null, null));
		} else if (pageNumber <= end && pageNumber > 0) {
				pages = new PagedListHolder<>(carService.loadCars((pageNumber - 1) * pageSize, pageSize));
			}

		model.addAttribute("page", pages);

		return mov;
	}

	@RequestMapping(value = "/new")
	public String newCar(Map<String, Object> model) {
		Car car = new Car();
		model.put("car", car);
		return "admin/cars/add";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveCar(@ModelAttribute("car") Car car, BindingResult result, SessionStatus status,
			RedirectAttributes redirectAttributes) {

		if (carValidator.validate(car, result)) {
			return "admin/cars/add";
		}

		status.setComplete();
		carService.saveOrUpdate(car);
		redirectAttributes.addFlashAttribute("message", getProperties().getProperty("sucess.saveCar"));
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/cars/new/";
	}

	@RequestMapping("/edit")
	public ModelAndView editCustomerForm(@RequestParam int id, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView("admin/cars/edit");
		Car car = carService.findById(id);

		if (car == null) {
			redirectAttributes.addFlashAttribute("message", getProperties().getProperty("error.findCar"));
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return mav;
		}

		mav.addObject("car", car);

		return mav;
	}

	@RequestMapping(value = "/delete{id}", method = RequestMethod.GET)
	public String deleteCustomerForm(@RequestParam int id, @RequestParam int idPage,
			RedirectAttributes redirectAttributes) {
		carService.deleteCar(id);

		redirectAttributes.addFlashAttribute("message", getProperties().getProperty("sucess.deleteCar"));
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/cars/index/page/" + idPage;
	}
}