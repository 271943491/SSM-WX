package com.hx.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class LoginController {

	@RequestMapping(value = "/login.json", method = RequestMethod.GET)
	@ResponseBody
	public Object login(HttpServletRequest request) {

		String name = request.getParameter("name");
		String passwd = request.getParameter("password");

		Map<String, String> map = new HashMap<String, String>();

		if (name.equals("y") && passwd.equals("y")) {

			map.put("success", "登陆成功");
			return map;
		} else {
			map.put("fail", "登陆失败");
			return map;
		}
	}
}