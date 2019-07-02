package app.dao.impl;

import java.util.List;

import app.dao.GenericDAO;
import app.dao.ToursDao;
import app.model.Tours;

public class ToursDAOImpl extends GenericDAO<Integer, Tours> implements ToursDao{

	public ToursDAOImpl() {
		super(Tours.class);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tours> listTours() {
		return getSession().createQuery("from Tours as T ORDER BY T.tourRating DESC").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tours> listSaleOffTours() {
		return getSession().createQuery("from Tours as T ORDER BY T.tourSaleOff DESC").getResultList();
	}
	public Tours maxSaleOffTours() {
		return (Tours) getSession().createQuery("from Tours as T ORDER BY T.tourSaleOff DESC").getSingleResult();
	}
}