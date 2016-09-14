package com.hx.util;

import net.sf.json.JSONObject;

// 调用微信接口获取access_token
public class getAccessToken {

	public String getAccessToken() {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx7125ad494a2fdd21&secret=6aa7d777bee984493182c1e95c6cc63f";

		String access_token = new get().sendGet(url);

		JSONObject jo = JSONObject.fromObject(access_token);

		access_token = jo.getString("access_token").toString();

		return access_token;

	}
}
