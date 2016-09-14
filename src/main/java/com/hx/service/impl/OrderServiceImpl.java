package com.hx.service.impl;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hx.dao.IUserDao;
import com.hx.domain.orderInfo;
import com.hx.service.IOrderService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
	@Resource
	private IUserDao orderDao;

	@Override
	public ArrayList<orderInfo> getOrderInfo() {
		// TODO Auto-generated method stub
		return this.orderDao.selectOneMonth();
	}
	
	@Override
	public ArrayList<orderInfo> getAllStore() {
		// TODO Auto-generated method stub
		return this.orderDao.selectAllStore();
	}

}