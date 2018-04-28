package kz.astana.uvaissov.insta.service;

import kz.astana.uvaissov.insta.entity.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	void save(User user);
}
