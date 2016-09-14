package com.hx.service;

import com.hx.domain.User;

public interface IUserService {
	public User getUserById(int userId);

	public int saveUserInfo(User user);

}