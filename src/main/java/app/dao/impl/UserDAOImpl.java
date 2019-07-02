package app.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import app.dao.GenericDAO;
import app.dao.UserDAO;
import app.helper.Constants;
import app.model.User;

public class UserDAOImpl extends GenericDAO<Integer, User> implements UserDAO {
	
	public UserDAOImpl() {
		super(User.class);
	}
	
	public UserDAOImpl(SessionFactory sessionfactory) {
		setSessionFactory(sessionfactory);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<User> loadUsers(Integer offset,Integer maxResults) {
		return getSession().createCriteria(User.class).setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : Constants.PAGESIZE).list();
	}
	
	@Override
	public User findByUser(String username) {
	    return (User) getSession().createQuery("from User where username=:username")
				.setParameter("username", username).uniqueResult();
	}

	@SuppressWarnings("deprecation")
	@Override
    public long countUser() {
  
    	return (long)getSession().createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
    }
}
