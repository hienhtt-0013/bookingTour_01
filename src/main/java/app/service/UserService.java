package app.service;

import java.util.List;

import app.model.User;


public interface UserService extends BaseService<Integer, User> {
	boolean deleteUser(Integer id);

	List<User> loadUsers(Integer offset,Integer maxResult);
	long countUser();
	User findByUser(String username);
	User checkLogin(String username, String password);
	User createUser(User user);
	
}
