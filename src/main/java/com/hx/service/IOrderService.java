package com.hx.service;

import java.util.ArrayList;

import com.hx.domain.orderInfo;

public interface IOrderService {

	public ArrayList<orderInfo> getOrderInfo();

	public ArrayList<orderInfo> getAllStore();

}