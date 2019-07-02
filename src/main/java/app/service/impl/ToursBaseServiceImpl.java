package app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import app.dao.ToursDao;

public class ToursBaseServiceImpl  {
	@Autowired
	protected ToursDao toursDAO;

	public ToursDao getToursDAO() {
		return toursDAO;
	}

	public void setToursDAO(ToursDao toursDAO) {
		this.toursDAO = toursDAO;
	}
}