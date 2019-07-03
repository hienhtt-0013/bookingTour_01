package app.service;

import java.util.List;
import java.util.Set;

import app.model.Role;
import app.model.User;


public interface UserService extends BaseService<Integer, User> {
	boolean deleteUser(Long id);

	List<User> loadUsers(Integer offset,Integer maxResult);
	long countUser();
	User findByUser(String username);
	User checkLogin(String username, String password);
	User createUser(User user,  Set<Role> setRole);
	void createUserAdmin(User user);
	void createUserPublic(User user);
	
}
