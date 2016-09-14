package com.hx.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class get {
	
	public String sendGet(String url) {

		String strResponse = "";

		try {

			// 服务器的域名
			URL realUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

			// 设置为GET情
			conn.setRequestMethod("GET");

			// 建立实际的连接
            conn.connect();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

			String strLine = "";

			while ((strLine = reader.readLine()) != null) {
				strResponse += strLine + "\n";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResponse;
	}
}
