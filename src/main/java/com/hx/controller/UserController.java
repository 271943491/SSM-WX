package com.hx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import com.hx.domain.User;
import com.hx.domain.orderInfo;
import com.hx.service.IOrderService;
import com.hx.service.IUserService;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Resource
	private IUserService userService = null;

	@Resource
	private IOrderService orderService = null;

	@RequestMapping("/addData.do")
	public ModelAndView addData() {
		return new ModelAndView("/saveUserInfo");
	}

	@RequestMapping("/viewChart.do")
	public ModelAndView viewChart(HttpServletRequest request, Model model) {
		Map<String, String> map = new TreeMap<String, String>();
		ArrayList<orderInfo> orderList = orderService.getOrderInfo();
		String data = "";
		for (orderInfo order : orderList) {
			map.put(order.getDate(), order.getOrderCount());
			data = data + "[" + order.getDate() + "," + order.getOrderCount() + "]" + ",";
		}
		// 计算出当月订单最大的数
		int maxCount = Integer.parseInt(orderList.get(0).getOrderCount());
		for (int m = 0; m < orderList.size() - 1; m++) {
			int a = Integer.parseInt(orderList.get(m + 1).getOrderCount());
			if (a > maxCount) {
				maxCount = a;
			}
		}
		data = data.substring(0, data.length() - 1);
		// data为 [日期，订单数]
		request.setAttribute("map", map);
		request.setAttribute("data", data);
		request.setAttribute("maxCount", maxCount);
		return new ModelAndView("/charts", map);
	}
	
	@RequestMapping("/viewAllStore.do")
	public ModelAndView viewAllStore(HttpServletRequest request, Model model) {
		Map<String, String> map = new TreeMap<String, String>();
		ArrayList<orderInfo> orderList = orderService.getAllStore();
		String data = "";
		for (orderInfo order : orderList) {
			map.put(order.getEntity_name(), order.getOrderCount());
			data = data + "['" + order.getEntity_name() + "'," + order.getOrderCount() + "]" + ",";
		}
		// 计算出当月订单最大的数
		int maxCount = Integer.parseInt(orderList.get(0).getOrderCount());
		for (int m = 0; m < orderList.size() - 1; m++) {
			int a = Integer.parseInt(orderList.get(m + 1).getOrderCount());
			if (a > maxCount) {
				maxCount = a;
			}
		}
		data = data.substring(0, data.length() - 1);
		// data为 ['店名'，订单数]
		request.setAttribute("map", map);
		request.setAttribute("data", data);
		request.setAttribute("maxCount", maxCount);
		return new ModelAndView("/charts2",map);
	}

	@RequestMapping("/getUserName.do")
	public String toIndex(HttpServletRequest request, Model model) {
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUserName";
	}

	@RequestMapping(value = "/addData.json", method = RequestMethod.POST)
	public ModelAndView saveUser(@RequestBody User user) {
		int id = this.userService.saveUserInfo(user);
		Map<String, String> map = new HashMap<String, String>();
		if (id > 0) {
			map.put("msg", "success");
			map.put("state", "1");
		} else {
			map.put("msg", "fail");
			map.put("state", "0");
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

}