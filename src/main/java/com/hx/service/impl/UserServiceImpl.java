package com.hx.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hx.dao.IUserDao;
import com.hx.domain.User;
import com.hx.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;

	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

	@Override
	public int saveUserInfo(User user) {
		// TODO Auto-generated method stub
		return this.userDao.insert(user);
	}

}