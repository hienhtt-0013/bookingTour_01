package app.dao;

import java.util.List;

import app.model.User;

public interface UserDAO extends BaseDAO<Integer, User> {
	List<User> loadUsers(Integer offset,Integer maxResults);
	public long countUser();
	User findByUser(String username);
}
