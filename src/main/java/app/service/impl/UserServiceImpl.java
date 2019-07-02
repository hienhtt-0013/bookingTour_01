package app.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.model.User;
import app.service.UserService;
import app.util.DateUtil;
import app.util.RoleUtil;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User saveOrUpdate(User entity) {
		try {
			return getUserDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public boolean deleteUser(Integer id) {
		try {
			User user = getUserDAO().findById(id);
			return delete(user);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public List<User> loadUsers(Integer offset,Integer maxresult) {
		try {
			return getUserDAO().loadUsers(offset,maxresult);
		} catch (Exception e) {
			return null;
		}
	}
   
	@SuppressWarnings("null")
	@Override
	public long countUser() {
		try {
           return getUserDAO().countUser();
		} catch (Exception e) {
			return (Long) null;
		}
	}
	
	@Override
	public User findById(Serializable key) {
		try {
			return getUserDAO().findById(key);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User findByUser(String username) {
		try {
			return getUserDAO().findByUser(username);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User checkLogin(String username, String password) {
		try {
			User user = getUserDAO().findByUser(username);
			if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(User entity) {
		try {
			getUserDAO().delete(entity);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public User createUser(User user, int role_id, String role_name) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			
			RoleUtil roleUtil = new RoleUtil();
			
			user.setRoles(new HashSet<>(roleUtil.roleUtil(role_id, role_name)));

			DateUtil dateUtil = new DateUtil();
			java.sql.Date sqlDate = dateUtil.dateUtil("yyyy-MM-dd");
			
			user.setCreateAt(sqlDate);
			user.setUpdateAt(sqlDate);
			return getUserDAO().saveOrUpdate(user);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

}
