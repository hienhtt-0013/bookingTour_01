package app.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.model.Role;
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
	public boolean deleteUser(Long id) {
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
	public User createUser(User user, Set<Role> setRole) {
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRoles(setRole);

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
	
	@Override
	public void createUserAdmin(User user) {
		// Lưu mảng role nếu 1 user có nhiều role
		Set<Role> setRole = new LinkedHashSet<Role>();
		for(Role role: user.getRoles()){
			String roleName = role.getName();
			int roleId = 0;
			if(roleName.equals("ROLE_ADMIN")) roleId = 1;
			else if(roleName.equals("ROLE_EDITOR")) roleId = 2;
			else {roleId = 2; roleName = "ROLE_EDITOR";}
			setRole.add(new Role(roleId,  roleName));
		}
		createUser(user, setRole);
	}
	
	@Override
	public void createUserPublic(User user) {
		Set<Role> setRole = new HashSet<Role>();
		setRole.add(new Role(3,  "ROLE_USER"));
		createUser(user, setRole);
	}
	
}
