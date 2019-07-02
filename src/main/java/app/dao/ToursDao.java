package app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.model.Tours;

public interface ToursDao extends BaseDAO<Integer, Tours> {
	List<Tours> listTours();
	List<Tours> listSaleOffTours();
	Tours maxSaleOffTours();
}