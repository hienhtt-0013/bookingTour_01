package app.dao;

import java.util.List;


import app.model.Tours;

public interface ToursDao extends BaseDAO<Integer, Tours> {
	List<Tours> listTours();
	List<Tours> listSaleOffTours();
	Tours maxSaleOffTours();
}