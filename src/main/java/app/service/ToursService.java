package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import app.dao.ToursDao;
import app.model.Tours;

public interface ToursService  extends  BaseService<Integer, Tours> {
	boolean deleteTours(Integer id);
	List<Tours> loadTours();
	List<Tours> loadSaleOffTours();
	Tours maxSaleOffTours();
}