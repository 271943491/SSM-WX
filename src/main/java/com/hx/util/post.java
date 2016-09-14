package com.hx.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class post {
	
	public String sendPost(String url, String param) {

		String strResponse = "";

		try {

			// 服务器的域名
			URL realUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

			// 设置为POST情
			conn.setRequestMethod("POST");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);

			conn.setDoInput(true);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			out.write(param.getBytes("utf-8"));

			out.flush();

			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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
