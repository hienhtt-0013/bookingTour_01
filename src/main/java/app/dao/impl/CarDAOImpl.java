package app.dao.impl;

import java.util.List;


import org.hibernate.criterion.Projections;

import app.dao.CarDAO;
import app.dao.GenericDAO;
import app.helper.Constants;
import app.model.Car;

public class CarDAOImpl extends GenericDAO<Integer, Car> implements CarDAO {

	public CarDAOImpl() {
		super(Car.class);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Car> loadCars(Integer offset, Integer maxResults) {
		return getSession().createCriteria(Car.class).setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : Constants.PAGESIZE).list();
	}
    @SuppressWarnings("deprecation")
	@Override
    public long countCar() {
  
    	return (long)getSession().createCriteria(Car.class).setProjection(Projections.rowCount()).uniqueResult();
    }
    
    
}
