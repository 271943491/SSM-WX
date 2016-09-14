package com.hx.dao;

import java.util.ArrayList;
import com.hx.domain.User;
import com.hx.domain.orderInfo;

public interface IUserDao {

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	ArrayList<orderInfo> selectOneMonth();

	ArrayList<orderInfo> selectAllStore();
}