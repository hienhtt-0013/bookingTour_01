package app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.model.Tours;
import app.service.ToursService;

@Controller
public class ToursController {
	@Autowired
	private ToursService toursService;
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public ModelAndView home() {
		List<Tours> tours= toursService.loadTours();
	    ModelAndView mav = new ModelAndView("index");
	    mav.addObject("tours", tours);
	    
	    Tours maxSaleOffTour= toursService.maxSaleOffTours();
	    mav.addObject("mSOTour", maxSaleOffTour);
	    
	    List<Tours> listSaleOffTours= toursService.loadSaleOffTours();
	    mav.addObject("listSaleOffTours", listSaleOffTours);
	    return mav;
	}
}